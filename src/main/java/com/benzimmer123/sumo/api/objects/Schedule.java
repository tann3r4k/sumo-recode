package com.benzimmer123.sumo.api.objects;

import java.time.ZonedDateTime;

import com.benzimmer123.sumo.api.enums.ScheduleType;

public interface Schedule {

	/**
	 * Method to see if this schedule is passed the current time
	 * 
	 * @return boolean
	 * 
	 */
	boolean ended();

	/**
	 * Method to get the ScheduleType
	 * 
	 * @return ScheduleType
	 * 
	 */
	ScheduleType getScheduleType();

	/**
	 * Method to get the schedule id
	 * 
	 * @return int
	 * 
	 */
	int getId();

	/**
	 * Method to get the scheduled date
	 * 
	 * @return ZonedDateTime
	 * 
	 */
	ZonedDateTime getDate();

}