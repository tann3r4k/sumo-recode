package com.benzimmer123.sumo.cmds.subcommands;

import java.io.File;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class Delete extends SubCommand {

	public Delete(Sumo instance) {
		super(instance, true);
		addAlias("remove");
		addAlias("delete");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		if (sumo.getState() != GameState.NOT_STARTED) {
			LangUtil.sendMessage(sender, LangUtil.ACTIVE_NOW.toString());
			return false;
		}

		File sumoFile = new File(plugin.getDataFolder() + "/sumos/" + sumo.getName().toLowerCase() + ".json");

		if (sumoFile.exists()) {
			sumoFile.delete();
		}

		SumoHandler.getInstance().removeSumo(sumo);
		LangUtil.sendMessage(sender, LangUtil.DELETED_SUMO.toString().replaceAll("%sumo%", args[0]));
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
		return "/sumo delete <name>";
	}

	@Override
	public String getPermission() {
		return "SUMO.DELETE";
	}

	@Override
	public String getDescription() {
		return "Delete a Sumo arena.";
	}
}
