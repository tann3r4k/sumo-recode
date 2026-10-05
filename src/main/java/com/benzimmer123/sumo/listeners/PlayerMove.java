package com.benzimmer123.sumo.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerMove implements Listener {

	@EventHandler
	public void PlayerMoveMethod(PlayerMoveEvent e) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		if (sumoPlayer.getState() == PlayerState.FIGHTING) {
			if (sumoPlayer.getSumo() != null) {
				if (sumoPlayer.getPlayer().getLocation().getBlockY() < sumoPlayer.getSumo().getSumoLocation().getFloorLocation()) {
					Sumo.getInstance().getSumoManager().knockedOut(sumoPlayer);
					return;
				}
			}
		}

		Location from = e.getFrom();
		double fromX = from.getBlockX();
		double fromZ = from.getBlockZ();

		Location to = e.getTo();
		double toX = to.getBlockX();
		double toZ = to.getBlockZ();

		if (fromX != toX || fromZ != toZ) {
			if (sumoPlayer.isFrozen())
				e.setTo(e.getFrom());
		}
	}
}
