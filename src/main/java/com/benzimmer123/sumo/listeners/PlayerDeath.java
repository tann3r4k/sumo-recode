package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerDeath implements Listener {

	@EventHandler
	public void PlayerDeathMethod(PlayerDeathEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getEntity());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		Sumo.getInstance().getSumoManager().leave(e.getEntity());
	}
}
