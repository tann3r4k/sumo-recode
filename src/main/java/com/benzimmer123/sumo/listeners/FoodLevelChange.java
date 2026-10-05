package com.benzimmer123.sumo.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class FoodLevelChange implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void HungerLoss(FoodLevelChangeEvent e) {
		if (e.getEntityType() != EntityType.PLAYER)
			return;

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer((Player) e.getEntity());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		e.setCancelled(true);

		if (e.getFoodLevel() < 20)
			e.setFoodLevel(20);
	}
}
