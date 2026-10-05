package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class Rename extends SubCommand {

	public Rename(Sumo instance) {
		super(instance, true);
		addAlias("rename");
		addAlias("recreate");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		SumoArena renamedSumo = SumoHandler.getInstance().getSumo(args[1]);

		if (renamedSumo != null) {
			LangUtil.sendMessage(sender, LangUtil.ALREADY_EXISTS.toString());
			return false;
		}

		sumo.setName(args[1]);
		sumo.save();

		LangUtil.sendMessage(sender, LangUtil.RENAMED_SUMO.toString().replaceAll("%previous%", args[0]).replaceAll("%new%", args[1]));
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
		return "/sumo rename <arena> <newname>";
	}

	@Override
	public String getPermission() {
		return "SUMO.RENAME";
	}

	@Override
	public String getDescription() {
		return "Rename a currently created Sumo arena.";
	}
}
