package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class ScheduleTaskIDs extends SubCommand {

	public ScheduleTaskIDs(Sumo instance) {
		super(instance, true);
		addAlias("scheduled");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}
		
		sumo.getSumoScheduler().listIds(args[0], sender);
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
		return "/sumo scheduled <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.VIEWIDS";
	}

	@Override
	public String getDescription() {
		return "View a Sumo's scheduled IDs so they can be changed or removed.";
	}
}
