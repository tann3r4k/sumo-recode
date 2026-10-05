package com.benzimmer123.sumo.api.objects;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.api.enums.GameState;

public interface SumoArena {

	/**
	 * Method to check if there is an active fight and countdown if sumo hasn't
	 * started
	 */
	void call();

	/**
	 * Method to start the queue for players to join
	 */
	void startCountdown();

	/**
	 * Method to start a new sumo game
	 */
	void startGame();

	/**
	 * Method to end the round
	 */
	void roundEnd();

	/**
	 * Method to start a new sumo fight with remaining players
	 */
	void startMatch();

	/**
	 * Method to add a player to the sumo match
	 * 
	 * @param Player player
	 * 
	 */
	void join(Player player);

	/**
	 * Method to check if sumo is joinable
	 * 
	 * @return boolean
	 * 
	 */
	boolean isJoinable();

	/**
	 * Method to check if start time needs broadcasting
	 * 
	 * @param int time
	 * 
	 */
	void checkTimes(int time);

	/**
	 * Method to get 2 random valid players from the match
	 * 
	 * @return boolean
	 * 
	 */
	SumoPlayer[] getRandomPlayers();

	/**
	 * Method to set the winning and losing player of a fight
	 * 
	 * @param SumoPlayer winningPlayer
	 * @param SumoPlayer losingPlayer
	 * 
	 */
	void wonMatch(SumoPlayer winningPlayer, SumoPlayer losingPlayer);

	/**
	 * Method to reward the winner of the sumo
	 * 
	 * @param SumoPlayer sumoPlayer
	 * 
	 */
	void wonGame(SumoPlayer sumoPlayer);

	/**
	 * Method to end the sumo match
	 */
	void end();

	/**
	 * Method to teleport a player to a spawn point
	 * 
	 * @param SumoPlayer sumoPlayer
	 * @param int locationNumber
	 * 
	 */
	void teleportPlayer(SumoPlayer sumoPlayer, int locationNumber);

	/**
	 * Method to set the sumo kit to current players inventory
	 * 
	 * @param Player player
	 * 
	 */
	void setSumoKit(Player player);

	/**
	 * Method to update player details to set as in match
	 * 
	 * @param Player player
	 * 
	 */
	void addPlayer(Player player);

	/**
	 * Method to remove a player and start the next round
	 * 
	 * @param SumoPlayer sumoPlayer
	 * @param boolean nextRound
	 * 
	 */
	void removePlayer(SumoPlayer sumoPlayer, boolean nextRound);

	/**
	 * Method to get all spectating players in this sumo
	 * 
	 * @return List of SumoPlayers
	 * 
	 */
	List<SumoPlayer> getSpectatingPlayers();

	/**
	 * Method to get all exempt players
	 * This is used to temporarily stop players from being removed on teleport
	 * So we can teleport to the sumo spawn location, etc
	 * 
	 * @return List of SumoPlayers
	 * 
	 */
	List<SumoPlayer> getExemptPlayers();

	/**
	 * Method to get all players in this sumo
	 * 
	 * @return List of SumoPlayers
	 * 
	 */
	List<SumoPlayer> getPlayers();

	/**
	 * Method to get all players already in the next round
	 * 
	 * @return List of SumoPlayers
	 * 
	 */
	List<SumoPlayer> getNextRound();

	/**
	 * Method to set the sumo name
	 * 
	 * @param String name
	 * 
	 */
	void setName(String name);

	/**
	 * Method to get the sumo name
	 * 
	 * @return String
	 * 
	 */
	String getName();

	/**
	 * Method to get the round number
	 * 
	 * @return int
	 * 
	 */
	int getRound();

	/**
	 * Method to get the time until start in seconds
	 * 
	 * @return int
	 * 
	 */
	int getTimeUntilStart();

	/**
	 * Method to get the GameState
	 * 
	 * @return GameState
	 * 
	 */
	GameState getState();

	/**
	 * Method to get the SumoLocation data
	 * 
	 * @return SumoLocation
	 * 
	 */
	SumoLocation getSumoLocation();

	/**
	 * Method to get the SumoScheduler data
	 * 
	 * @return SumoScheduler
	 * 
	 */
	SumoScheduler getSumoScheduler();

	/**
	 * Method to serialize the arena
	 */
	void save();
}