package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.massivecraft.factions.event.PowerLossEvent;

public class FactionsListener implements Listener {

	@EventHandler
	public void onFactionsLostPower(PowerLossEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getfPlayer().getPlayer());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		e.setCancelled(true);
	}
}
