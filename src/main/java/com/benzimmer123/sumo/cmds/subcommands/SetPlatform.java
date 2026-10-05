package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class SetPlatform extends SubCommand {

	public SetPlatform(Sumo instance) {
		super(instance, false);
		addAlias("setfloor");
		addAlias("setplatform");
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

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(player);
		sumoPlayer.setEditingFloor(true);
		sumoPlayer.setEditingArena(sumo);
		LangUtil.sendMessage(sender, LangUtil.LEFT_CLICK.toString());
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
		return "/sumo setfloor <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.SETFLOOR";
	}

	@Override
	public String getDescription() {
		return "Set the Sumo floor location.";
	}
}
