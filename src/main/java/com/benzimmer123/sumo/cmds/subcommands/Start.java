package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.BroadcastUtil;
import com.benzimmer123.sumo.util.LangUtil;

public class Start extends SubCommand {

	public Start(Sumo instance) {
		super(instance, true);
		addAlias("start");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		if (sumo.getSumoLocation().getFloorLocation() == -3000 || sumo.getSumoLocation().getLobbyWorld() == null || sumo.getSumoLocation()
				.getWorld() == null) {
			LangUtil.sendMessage(sender, LangUtil.LOCATIONS_NOT_SET.toString());
			return false;
		}

		if (sumo.getState() != GameState.NOT_STARTED) {
			LangUtil.sendMessage(sender, LangUtil.ALREADY_IN_PROGESS.toString());
			return false;
		}

		if (!Sumo.getInstance().getEventManager().callSumoStartEvent(sumo))
			return false;

		String name = sender instanceof ConsoleCommandSender ? LangUtil.AUTOMATIC_HOSTNAME.toString() : sender.getName();

		sumo.startCountdown();
		BroadcastUtil.call(LangUtil.STARTED_SUMO.toString().replaceAll("%player%", name).replaceAll("%time%", plugin.getConfig().getInt("QUEUE_TIME")
				+ "").replaceAll("%sumo%", args[0]), null, true);
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo start <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.START";
	}

	@Override
	public String getDescription() {
		return "Start a Sumo arena.";
	}
}
