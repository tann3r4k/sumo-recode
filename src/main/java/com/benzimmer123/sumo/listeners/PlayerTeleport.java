package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void PlayerTeleportMethod(PlayerTeleportEvent e) {
		if (e.getFrom().getWorld().equals(e.getTo().getWorld()) && e.getFrom().distanceSquared(e.getTo()) < 5)
			return;

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING || sumoPlayer.getSumo() == null || sumoPlayer.getSumo().getExemptPlayers().contains(
				sumoPlayer))
			return;

		Sumo.getInstance().getSumoManager().leave(e.getPlayer());
	}
	
	public String get() {
		return "%%__USER__%%";
	}
}
