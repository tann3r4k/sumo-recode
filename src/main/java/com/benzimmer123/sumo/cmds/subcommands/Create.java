package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.obj.serial.MemorySumoArena;
import com.benzimmer123.sumo.util.LangUtil;

public class Create extends SubCommand {

	public Create(Sumo instance) {
		super(instance, true);
		addAlias("new");
		addAlias("create");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo != null) {
			LangUtil.sendMessage(sender, LangUtil.ALREADY_EXISTS.toString());
			return false;
		}
		
		boolean allLetters = args[0].chars().allMatch(Character::isLetter);

		if (!allLetters) {
			LangUtil.sendMessage(sender, LangUtil.ILLEGAL_CHAR.toString());
			return false;
		}

		new MemorySumoArena(args[0]);

		LangUtil.sendMessage(sender, LangUtil.CREATED_SUMO.toString().replaceAll("%sumo%", args[0]));
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
		return "/sumo create <name>";
	}

	@Override
	public String getPermission() {
		return "SUMO.CREATE";
	}

	@Override
	public String getDescription() {
		return "Create a new Sumo arena.";
	}
}
