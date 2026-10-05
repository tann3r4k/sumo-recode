package com.benzimmer123.sumo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.api.events.SumoEndEvent;
import com.benzimmer123.sumo.api.events.SumoStartEvent;
import com.benzimmer123.sumo.api.events.SumoWinEvent;
import com.benzimmer123.sumo.api.objects.SumoArena;

public class EventManager {
	
	public boolean callSumoStartEvent(SumoArena sumo) {
		SumoStartEvent sumoStartEvent = new SumoStartEvent(sumo);
		Bukkit.getServer().getPluginManager().callEvent(sumoStartEvent);
		return true;
	}

	public boolean callSumoEndEvent(SumoArena sumo) {
		SumoEndEvent sumoEndEvent = new SumoEndEvent(sumo);
		Bukkit.getServer().getPluginManager().callEvent(sumoEndEvent);
		return true;
	}

	public boolean callSumoWinEvent(SumoArena sumo, Player player) {
		SumoWinEvent sumoWinEvent = new SumoWinEvent(sumo, player);
		Bukkit.getServer().getPluginManager().callEvent(sumoWinEvent);
		return true;
	}

}
