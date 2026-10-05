package com.benzimmer123.sumo.obj.serial;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.List;

import com.benzimmer123.sumo.util.TextUtil;
import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.api.enums.ScheduleType;
import com.benzimmer123.sumo.api.objects.Schedule;
import com.benzimmer123.sumo.api.objects.SumoScheduler;
import com.benzimmer123.sumo.util.DateUtil;
import com.benzimmer123.sumo.util.LangUtil;
import com.google.common.collect.Lists;

public class MemorySumoScheduler implements SumoScheduler, Serializable {

	private static final long serialVersionUID = -2654650047699381239L;
	private List<MemorySchedule> scheduledSumos;
	private int nextId;

	public MemorySumoScheduler() {
		scheduledSumos = Lists.newArrayList();
	}

	public void addSchedule(ZonedDateTime date) {
		scheduledSumos.add(new MemorySchedule(date, getNextId(), ScheduleType.SCHEDULE));
		DateUtil.sortUpcomingDates();
		add();
	}

	public void addDaily(ZonedDateTime date) {
		scheduledSumos.add(new MemorySchedule(date, getNextId(), ScheduleType.DAILY));
		DateUtil.sortUpcomingDates();
		add();
	}

	public void addWeekly(ZonedDateTime date) {
		scheduledSumos.add(new MemorySchedule(date, getNextId(), ScheduleType.WEEKLY));
		DateUtil.sortUpcomingDates();
		add();
	}

	public List<Schedule> checkAllSchedules() {
		List<Schedule> toRemove = Lists.newArrayList();

		for (Schedule schedule : getScheduled()) {
			if (schedule.ended()) {
				toRemove.add(schedule);
			}
		}

		return toRemove;
	}

	public void listIds(String sumo, CommandSender sender) {
		EnumSet<DayOfWeek> dows = EnumSet.allOf(DayOfWeek.class);

		for (DayOfWeek dow : dows) {
			LangUtil.sendMessage(sender, LangUtil.LIST_SUMO_IDS_TITLE.toString().replaceAll("%day%", TextUtil.capitalize(dow.toString())));
			for (Schedule schedule : getScheduled()) {
				if (schedule.getDate().getDayOfWeek() == dow) {
					LangUtil.sendMessage(
							sender,
							LangUtil.LIST_SUMO_IDS_ENTRY.toString().replaceAll("%id%", "" + schedule.getId())
									.replaceAll("%hour%", schedule.getDate().getHour() + "")
									.replaceAll("%minute%", schedule.getDate().getMinute() + "")
									.replaceAll("%type%", schedule.getScheduleType().toString()));
				}
			}
		}
	}

	public void remove(Schedule schedule) {
		scheduledSumos.remove(schedule);
		DateUtil.sortUpcomingDates();
	}

	public Schedule getSchedule(int id) {
		for (Schedule schedule : getScheduled()) {
			if (schedule.getId() == id) {
				return schedule;
			}
		}
		return null;
	}

	public int getNextId() {
		return nextId;
	}

	public void add() {
		nextId += 1;
	}

	public List<Schedule> getScheduled() {
		List<Schedule> scheduledDuplicate = Lists.newArrayList(scheduledSumos);
		return scheduledDuplicate;
	}

}
