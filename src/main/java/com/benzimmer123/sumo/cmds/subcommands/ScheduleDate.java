package com.benzimmer123.sumo.cmds.subcommands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class ScheduleDate extends SubCommand {

	public ScheduleDate(Sumo instance) {
		super(instance, true);
		addAlias("schedule");
		addAlias("scheduledate");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		int day;

		try {
			day = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			day = 0;
		}

		if (day == 0) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_DAY.toString());
			return false;
		}

		int month;

		try {
			month = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			month = 0;
		}

		if (month == 0 || month > 12) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_MONTH.toString());
			return false;
		}

		String[] date = args[3].split(":");

		int hour = 0;

		if (date[0] != null) {
			try {
				hour = Integer.parseInt(date[0]);
			} catch (NumberFormatException e) {
				hour = -1;
			}
		}

		if (hour == -1 || hour > 23) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_HOUR.toString());
			return false;
		}

		int minute = 0;

		if (date.length == 1) {
			minute = 0;
		} else {
			try {
				minute = Integer.parseInt(date[1]);
			} catch (NumberFormatException e) {
				minute = -1;
			}
		}

		if (minute == -1 || minute > 59) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_MINUTE.toString());
			return false;
		}

		ZoneId zoneId = ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime scheduledTime = LocalDateTime.of(currentTime.getYear(), month, day, hour, minute);
		ZonedDateTime zdt = scheduledTime.atZone(zoneId);

		sumo.getSumoScheduler().addSchedule(zdt);
		sumo.save();

		LangUtil.sendMessage(sender, LangUtil.SCHEDULED_DATE.toString().replaceAll("%sumo%", args[0]).replaceAll("%time%", args[3]).replaceAll("%day%",
				args[1]).replaceAll("%month%", args[2]));
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 5)
			return true;
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo schedule <arena> <day_of_month> <month> <time_of_day>";
	}

	@Override
	public String getPermission() {
		return "SUMO.SCHEDULER";
	}

	@Override
	public String getDescription() {
		return "Schedule a Sumo for a specific time.";
	}
}
