package com.benzimmer123.sumo.api.objects;

import java.time.ZonedDateTime;
import java.util.List;

import org.bukkit.command.CommandSender;

public interface SumoScheduler {

	/**
	 * Method to add a new scheduled start date
	 * 
	 * @param ZonedDateTime date
	 * 
	 */
	void addSchedule(ZonedDateTime date);

	/**
	 * Method to add a new daily start date
	 * 
	 * @param ZonedDateTime date
	 * 
	 */
	void addDaily(ZonedDateTime date);

	/**
	 * Method to add a new weekly start date
	 * 
	 * @param ZonedDateTime date
	 * 
	 */
	void addWeekly(ZonedDateTime date);

	/**
	 * Method to return a list of all expired schedules
	 * 
	 * @return List of Schedules
	 * 
	 */
	List<Schedule> checkAllSchedules();

	/**
	 * Method to list all scheduled ids to a sender
	 * 
	 * @param String sumo
	 * @param CommandSender sender
	 * 
	 */
	void listIds(String sumo, CommandSender sender);

	/**
	 * Method to remove a schedule
	 * 
	 * @param Schedule schedule
	 * 
	 */
	void remove(Schedule schedule);

	/**
	 * Method to return a schedule from an id
	 * 
	 * @param int id
	 * @return Schedule
	 * 
	 */
	Schedule getSchedule(int id);

	/**
	 * Method to get the free next schedule id
	 * 
	 * @return int
	 * 
	 */
	int getNextId();

	/**
	 * Method to add one to the last schedule id
	 */
	void add();

	/**
	 * Method to get all stored schedules
	 * 
	 * @return List of schedules
	 * 
	 */
	List<Schedule> getScheduled();

}