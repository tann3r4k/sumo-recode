package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		SumoHandler.getInstance().addPlayer(e.getPlayer());
	}
	
}
