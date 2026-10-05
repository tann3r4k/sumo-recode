package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerDropItem implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.getState() != PlayerState.NOT_PLAYING) {
			e.setCancelled(true);
		}
	}

}
