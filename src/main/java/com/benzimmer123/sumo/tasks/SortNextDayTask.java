package com.benzimmer123.sumo.tasks;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bukkit.scheduler.BukkitRunnable;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.util.DateUtil;

public class SortNextDayTask extends BukkitRunnable {

	private int lastDayChecked;

	@Override
	public void run() {
		int currentDay = ZonedDateTime.now(ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"))).getDayOfMonth();

		if (lastDayChecked >= currentDay)
			return;

		lastDayChecked = currentDay;

		DateUtil.sortUpcomingDates();
	}
}