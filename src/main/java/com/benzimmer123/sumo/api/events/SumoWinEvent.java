package com.benzimmer123.sumo.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.benzimmer123.sumo.api.objects.SumoArena;

public class SumoWinEvent extends Event {

	private SumoArena sumo;
	private Player player;

	public SumoWinEvent(SumoArena sumo, Player player){
		this.sumo = sumo;
		this.player = player;
	}
	
	public Player getWinner(){
		return player;	
	}

	public SumoArena getSumo(){
		return sumo;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
