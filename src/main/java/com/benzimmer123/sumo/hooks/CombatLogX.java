package com.benzimmer123.sumo.hooks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class CombatLogX implements Listener {

	@EventHandler
	public void onTagEvent(com.SirBlobman.combatlogx.api.event.PlayerPreTagEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		e.setCancelled(true);
	}
}
