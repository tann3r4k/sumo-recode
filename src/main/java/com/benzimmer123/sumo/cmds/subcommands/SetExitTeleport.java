package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class SetExitTeleport extends SubCommand {

	public SetExitTeleport(Sumo instance) {
		super(instance, false);
		addAlias("setexittp");
		addAlias("setexit");
		addAlias("setexitteleport");
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

		sumo.getSumoLocation().setExitLocation(player.getLocation());
		sumo.save();
		return true;
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
		return "/sumo setexit <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.SETEXIT";
	}

	@Override
	public String getDescription() {
		return "Set an exit location.";
	}
}
