package com.benzimmer123.sumo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void PlayerInteractMethod(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null)
			return;

		if (e.getClickedBlock().getLocation() == null)
			return;

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.isEditingFloor()) {
			e.setCancelled(true);

			SumoArena sumo = sumoPlayer.getEditingArena();

			sumo.getSumoLocation().setFloorLocation(e.getClickedBlock().getLocation().getBlockY());
			sumo.save();

			LangUtil.sendMessage(e.getPlayer(), LangUtil.SET_FLOOR.toString().replaceAll("%sumo%", sumo.getName()));

			sumoPlayer.setEditingFloor(false);
			sumoPlayer.setEditingArena(null);
		}
	}
	
	public String get() {
		return "%%__USER__%%";
	}
}
