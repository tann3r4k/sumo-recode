package com.benzimmer123.sumo.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;

public class PlayerDamage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void PlayerDamageMethod(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();

			SumoPlayer player = SumoHandler.getInstance().getSumoPlayer((Player) e.getEntity());

			if (player.getSumo() != null && player.getSumo().getSpectatingPlayers().contains(player)) {
				e.setCancelled(true);
				return;
			}

			if (player.getState() == PlayerState.NOT_PLAYING) {
				if (Sumo.getInstance().getConfig().getBoolean("COMBAT_TAG.ENABLED") && !e.isCancelled()) {
					player.setCombatTime(System.currentTimeMillis() + (Sumo.getInstance().getConfig().getInt("COMBAT_TAG.SECONDS") * 1000));
				}
			}

			if (player.getState() == PlayerState.FIGHTING) {
				if (e.getDamager() == null || e.getDamager().getType() != EntityType.PLAYER) {
					e.setCancelled(true);
					return;
				}
			}

			if (e.getDamager() instanceof Player) {
				Player d = (Player) e.getDamager();

				SumoPlayer damager = SumoHandler.getInstance().getSumoPlayer(d);

				if (player.getState() == PlayerState.FIGHTING && damager.getState() == PlayerState.FIGHTING) {
					e.setCancelled(false);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Sumo.getInstance(), new Runnable() {
						@SuppressWarnings("deprecation")
						public void run() {
							p.setHealth(p.getMaxHealth());
						}
					}, 5);
					return;
				}

				if (damager.getState() == PlayerState.NOT_PLAYING) {
					if (Sumo.getInstance().getConfig().getBoolean("COMBAT_TAG.ENABLED") && !e.isCancelled()) {
						damager.setCombatTime(System.currentTimeMillis() + (Sumo.getInstance().getConfig().getInt("COMBAT_TAG.SECONDS") * 1000));
					}
					return;
				}

				if (damager.getSumo() != null && damager.getSumo().getSpectatingPlayers().contains(damager)) {
					e.setCancelled(true);
					return;
				}

				if (damager.getState() == PlayerState.WAITING || player.getState() == PlayerState.WAITING || damager
						.getState() == PlayerState.IN_LOBBY || player.getState() == PlayerState.IN_LOBBY) {
					e.setCancelled(true);
				}
			}
		}
	}
}
