package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class Leave extends SubCommand {

	public Leave(Sumo instance) {
		super(instance, false);
		addAlias("leave");
		addAlias("quit");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(player);

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING) {
			LangUtil.sendMessage(sender, LangUtil.NOT_QUEUED.toString());
			return false;
		}

		Sumo.getInstance().getSumoManager().leave(player);
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo leave";
	}

	@Override
	public String getPermission() {
		return "SUMO.LEAVE";
	}

	@Override
	public String getDescription() {
		return "Leave a Sumo arena in progress.";
	}
}
