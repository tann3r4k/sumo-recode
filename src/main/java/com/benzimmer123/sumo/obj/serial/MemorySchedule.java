package com.benzimmer123.sumo.obj.serial;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.ScheduleType;
import com.benzimmer123.sumo.api.objects.Schedule;

public class MemorySchedule implements Schedule, Serializable { 

	private static final long serialVersionUID = 1685785393694000039L;
	private int id;
	private ZonedDateTime date;
	private ScheduleType scheduleType;

	public MemorySchedule(ZonedDateTime date, int id, ScheduleType scheduleType) { 
		this.id = id;
		this.date = date;
		this.scheduleType = scheduleType;
	}

	public boolean ended() {
		Instant instant = Instant.now();
		ZoneId zoneId = ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		ZonedDateTime date = ZonedDateTime.ofInstant(instant, zoneId);
		if (getScheduleType().equals(ScheduleType.SCHEDULE)) {
			if (date.isAfter(this.date))
				return true;
		} else if (getScheduleType().equals(ScheduleType.WEEKLY)) {
			if (date.getDayOfWeek() == this.date.getDayOfWeek() && date.getHour() == this.date.getHour()
					&& date.getMinute() == this.date.getMinute())
				return true;
		} else if (getScheduleType().equals(ScheduleType.DAILY)) {
			if (date.getHour() == this.date.getHour() && date.getMinute() == this.date.getMinute()) {
				return true;
			}
		}
		return false;
	}

	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public int getId() {
		return id;
	}

	public ZonedDateTime getDate() {
		return date;
	}
}