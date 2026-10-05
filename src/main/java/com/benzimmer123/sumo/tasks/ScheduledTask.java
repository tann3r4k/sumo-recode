package com.benzimmer123.sumo.tasks;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.enums.ScheduleType;
import com.benzimmer123.sumo.api.objects.Schedule;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.DateUtil;

public class ScheduledTask extends BukkitRunnable {

	@Override
	public void run() {
		boolean sortDates = false;

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			if (sumo.getSumoLocation().getFloorLocation() == -3000 || sumo.getSumoLocation().getLobbyWorld() == null || sumo.getSumoLocation()
					.getWorld() == null) {
				continue;
			}

			if (sumo.getState() != GameState.NOT_STARTED) {
				continue;
			}

			List<Schedule> scheduledSumos = sumo.getSumoScheduler().checkAllSchedules();

			if (scheduledSumos.isEmpty())
				continue;

			sumo.startCountdown();

			for (Schedule schedule : scheduledSumos) {
				if (schedule.getScheduleType().equals(ScheduleType.SCHEDULE)) {
					sumo.getSumoScheduler().remove(schedule);
					sortDates = true;
				}
			}

			sumo.save();
		}

		if (sortDates) {
			DateUtil.sortUpcomingDates();
		}
	}
}
