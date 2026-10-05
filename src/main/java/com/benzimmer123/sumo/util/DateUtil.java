package com.benzimmer123.sumo.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.TreeMap;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.ScheduleType;
import com.benzimmer123.sumo.api.objects.Schedule;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.google.common.collect.Maps;

public final class DateUtil {

	public static void sortUpcomingDates() {
		LoggerUtil.info("[Sumo] Sorting through upcoming Sumo schedules...");

		Map<ZonedDateTime, SumoArena> unsortedDates = Maps.newHashMap();
		ZonedDateTime endDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"))).plusDays(7);

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			for (Schedule schedule : sumo.getSumoScheduler().getScheduled()) {
				ZonedDateTime date = schedule.getDate();

				if (schedule.getScheduleType() == ScheduleType.DAILY) {
					date = getNextDailyDate(date);

					for (int i = 0; i < 7; i++) {
						ZonedDateTime daily = date.plusDays(i);

						if (daily.isBefore(endDate))
							unsortedDates.put(daily, sumo);
					}

					continue;
				} else if (schedule.getScheduleType() == ScheduleType.WEEKLY) {
					date = getNextWeeklyDate(date);
				}

				if (date.isAfter(endDate))
					continue;

				unsortedDates.put(date, sumo);
			}
		}
		Map<ZonedDateTime, SumoArena> sortedDates = new TreeMap<ZonedDateTime, SumoArena>(unsortedDates);
		SumoHandler.getInstance().setUpcomingDates(sortedDates);

		LoggerUtil.info("[Sumo] Successfully sorted through all Sumo schedules.");
	}

	public static String getScheduleCountdown() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		long seconds = ChronoUnit.SECONDS.between(currentDate, getNextStart());
		return LangUtil.SCHEDULED_COUNTDOWN.toString().replaceAll("%time%", "" + TimeUtil.getTime((int) seconds));
	}

	public static ZonedDateTime getNextStart() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));

		for (ZonedDateTime date : SumoHandler.getInstance().getUpcomingDates().keySet()) {
			if (date.isAfter(currentDate)) {
				return date;
			}
		}

		return null;
	}

	public static String getNextStartName() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));

		for (ZonedDateTime date : SumoHandler.getInstance().getUpcomingDates().keySet()) {
			if (date.isAfter(currentDate)) {
				return SumoHandler.getInstance().getUpcomingDates().get(date).getName();
			}
		}

		return null;
	}

	private static ZonedDateTime getNextDailyDate(ZonedDateTime date) {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		ZonedDateTime expectedDate = ZonedDateTime.of(date.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), date.getHour(), date
				.getMinute(), 0, 0, ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		expectedDate = expectedDate.withMonth(currentDate.getMonthValue()).withDayOfMonth(currentDate.getDayOfMonth()).withDayOfYear(currentDate
				.getDayOfYear());

		if (expectedDate.getHour() < currentDate.getHour() || expectedDate.getHour() == currentDate.getHour() && expectedDate
				.getMinute() < currentDate.getMinute()) {
			expectedDate = expectedDate.plusDays(1);
		}

		return expectedDate;
	}

	private static ZonedDateTime getNextWeeklyDate(ZonedDateTime date) {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		ZonedDateTime expectedDate = ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), 0,
				0, ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		DayOfWeek dow = expectedDate.getDayOfWeek();

		int threshold = 0;

		while (expectedDate.getDayOfMonth() == currentDate.getDayOfMonth() && expectedDate.getHour() == currentDate.getHour() && expectedDate
				.getMinute() < currentDate.getMinute() || dow != expectedDate.getDayOfWeek()) {
			expectedDate = expectedDate.plusDays(1);
			threshold++;

			if (threshold >= 30)
				break;
		}

		if (threshold >= 30) {
			LoggerUtil.warning("[ERROR] There was a fatal error when loading scheduler. Please report this to Benzimmer immediately.");
			return null;
		}

		return expectedDate;
	}

	public LocalDate nextDayOfWeek(DayOfWeek day, ZoneId zoneId) {
		LocalDate nextWed = LocalDate.now(zoneId).with(TemporalAdjusters.next(day));
		return nextWed;
	}

}
