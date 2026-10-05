package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerQuit implements Listener {

	@EventHandler
	public void PlayerQuitMethod(PlayerQuitEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());
		
		if (sumoPlayer.getState() != PlayerState.NOT_PLAYING) {
			Sumo.getInstance().getSumoManager().leave(e.getPlayer());
		}
		
		SumoHandler.getInstance().removePlayer(e.getPlayer());
	}

}
