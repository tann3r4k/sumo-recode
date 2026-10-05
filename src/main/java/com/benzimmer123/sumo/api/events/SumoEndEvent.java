package com.benzimmer123.sumo.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.benzimmer123.sumo.api.objects.SumoArena;

public class SumoEndEvent extends Event {

	private SumoArena sumo;

	public SumoEndEvent(SumoArena sumo){
		this.sumo = sumo;
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
