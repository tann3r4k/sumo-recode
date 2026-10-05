package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.Schedule;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class ScheduleRemove extends SubCommand {

	public ScheduleRemove(Sumo instance) {
		super(instance, true);
		addAlias("removeschedule");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		int id;

		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			id = -1;
		}

		if (id == -1) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_ID.toString());
			return false;
		}
		
		Schedule schedule = sumo.getSumoScheduler().getSchedule(id);

		if (schedule == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_ID.toString());
			return false;
		}

		sumo.getSumoScheduler().remove(schedule);
		LangUtil.sendMessage(sender, LangUtil.REMOVED_SCHEDULE.toString().replaceAll("%id%", id + ""));
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
		return "/sumo removeschedule <arena> <id>";
	}

	@Override
	public String getPermission() {
		return "SUMO.REMOVESCHEDULE";
	}

	@Override
	public String getDescription() {
		return "Remove a currently scheduled Sumo.";
	}
}
