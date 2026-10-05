package com.benzimmer123.sumo.api.objects;

import org.bukkit.Location;

public interface SumoLocation {

	/**
	 * Method to set the spawn location
	 * 
	 * @param Location loc
	 * @param String number
	 * 
	 */
	void setSpawnLocation(Location loc, String number);

	/**
	 * Method to set the lobby location
	 * 
	 * @param Location loc
	 * 
	 */
	void setLobbyLocation(Location loc);

	/**
	 * Method to set the spectate location
	 * 
	 * @param Location loc
	 * 
	 */
	void setSpectateLocation(Location loc);

	/**
	 * Method to set the exit location
	 * 
	 * @param Location loc
	 * 
	 */
	void setExitLocation(Location loc);

	/**
	 * Method to get the lobby world name
	 * 
	 * @return String
	 * 
	 */
	String getLobbyWorld();

	/**
	 * Method to get the spawn point world name
	 * 
	 * @return String
	 * 
	 */
	String getWorld();

	/**
	 * Method to set the floor location
	 * 
	 * @param int floorLocation
	 * 
	 */
	void setFloorLocation(int floorLocation);

	/**
	 * Method to get the floor location
	 * 
	 * @return int
	 * 
	 */
	int getFloorLocation();

	/**
	 * Method to get the lobby location
	 * 
	 * @return Location
	 * 
	 */
	Location getLobbyLocation();

	/**
	 * Method to get the spectate location
	 * 
	 * @return Location
	 * 
	 */
	Location getSpectateLocation();

	/**
	 * Method to get the exit location for sumo
	 * 
	 * @return Location
	 * 
	 */
	Location getExitLocation();

	/**
	 * Method to see if a spectate location is set
	 * 
	 * @return boolean
	 * 
	 */
	boolean isSpectateSet();

	/**
	 * Method to get the spawn point location
	 * 
	 * @param int locationNumber
	 * 
	 */
	Location getSpawnPoint(int locationNumber);

}