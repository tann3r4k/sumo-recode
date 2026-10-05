package com.benzimmer123.sumo.cmds.subcommands;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.DateUtil;
import com.benzimmer123.sumo.util.LangUtil;

public class ScheduleWeekly extends SubCommand {

	public ScheduleWeekly(Sumo instance) {
		super(instance, true);
		addAlias("scheduleweekly");
		addAlias("weekly");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		String[] date = args[1].split(":");

		if (date.length <= 1) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_TIME.toString());
			return false;
		}

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

		if (date[1] != null) {
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

		DayOfWeek dayOfWeek = null;
		String validTypes = "";

		for (DayOfWeek dow : DayOfWeek.values()) {
			if (dow.toString().equalsIgnoreCase(args[2])) {
				dayOfWeek = dow;

				if (validTypes.length() == 0) {
					validTypes = dow.toString();
				} else {
					validTypes = validTypes + ", " + dow.toString();
				}
			}
		}

		if (dayOfWeek == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_WEEKDAY.toString().replaceAll("%validtypes%", validTypes));
			return false;
		}

		ZoneId zoneId = ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		LocalDate currentDate = LocalDate.now(zoneId);

		if (currentDate.getDayOfWeek() != dayOfWeek)
			currentDate = new DateUtil().nextDayOfWeek(dayOfWeek, zoneId);

		LocalDateTime scheduledTime = LocalDateTime.of(currentDate, LocalTime.of(hour, minute));
		ZonedDateTime zdt = scheduledTime.atZone(zoneId);

		sumo.getSumoScheduler().addWeekly(zdt);
		sumo.save();

		LangUtil.sendMessage(
				sender,
				LangUtil.SCHEDULED_WEEKLY.toString().replaceAll("%sumo%", args[0]).replaceAll("%time%", args[1])
						.replaceAll("%day%", dayOfWeek.toString()));
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 4)
			return true;
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo weekly <arena> <time_of_day> <day_of_week>";
	}

	@Override
	public String getPermission() {
		return "SUMO.WEEKLYSCHEDULE";
	}

	@Override
	public String getDescription() {
		return "Create an automatic Sumo that will run weekly.";
	}
}
