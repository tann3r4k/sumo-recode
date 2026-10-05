package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class SetPositions extends SubCommand {

	public SetPositions(Sumo instance) {
		super(instance, false);
		addAlias("setspawn");
		addAlias("setpos");
		addAlias("setposition");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		if (sumo.getState() != GameState.NOT_STARTED) {
			LangUtil.sendMessage(sender, LangUtil.ALREADY_IN_PROGESS.toString());
			return false;
		}

		sumo.getSumoLocation().setSpawnLocation(player.getLocation(), args[1]);
		sumo.save();

		if (!args[1].equalsIgnoreCase("1")) {
			args[1] = "2";
		}

		LangUtil.sendMessage(sender, LangUtil.SET_SPAWNPOINT.toString().replaceAll("%sumo%", args[0]).replaceAll("%pos%", args[1]));
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo setspawn <arena> <position>";
	}

	@Override
	public String getPermission() {
		return "SUMO.SETSPAWNPOINT";
	}

	@Override
	public String getDescription() {
		return "Set the 2 Sumo arena spawn locations.";
	}
}
