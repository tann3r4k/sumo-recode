package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class List extends SubCommand {

	public List(Sumo instance) {
		super(instance, true);
		addAlias("list");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.SUMO_LIST_TITLE.toString());

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			LangUtil.sendMessage(sender, LangUtil.SUMO_LIST_ENTRY.toString().replaceAll("%sumo%", sumo.getName()));
		}

		return false;
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
		return "/sumo list";
	}

	@Override
	public String getPermission() {
		return "SUMO.LIST";
	}

	@Override
	public String getDescription() {
		return "List all the currently created Sumos.";
	}
}
