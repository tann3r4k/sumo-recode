package com.benzimmer123.sumo.managers;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.hooks.Magic;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.BroadcastUtil;
import com.benzimmer123.sumo.util.LangUtil;

public class SumoManager {

	@SuppressWarnings("deprecation")
	public void cleanPlayer(Player p) {
		Magic.closeWands(p);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.SURVIVAL);
		p.setFireTicks(0);
		for (PotionEffect pe : p.getActivePotionEffects())
			p.removePotionEffect(pe.getType());
		p.setFlying(false);
		p.setAllowFlight(false);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.updateInventory();
	}

	public boolean isActive() {
		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			if (sumo.getState() != GameState.NOT_STARTED)
				return true;
		}
		return false;
	}

	public List<SumoArena> getActive() {
		return SumoHandler.getInstance().getSumos().stream().filter(sumo -> sumo.getState() != GameState.NOT_STARTED).collect(Collectors.toList());
	}

	public void leave(Player player) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(player);
		
		if (sumoPlayer.getState() == PlayerState.IN_LOBBY) {
			int players = sumoPlayer.getSumo().getPlayers().size() - 1;
			BroadcastUtil.call(LangUtil.LEAVE_SUMO.toString().replaceAll("%player%", player.getName()).replaceAll("%players%", "" + players).replaceAll(
					"%maxplayers%", "" + Sumo.getInstance().getConfig().getInt("MAXIMUM_PLAYERS")), sumoPlayer.getSumo(), false);
			sumoPlayer.getSumo().removePlayer(sumoPlayer, false);
		} else if (sumoPlayer.getState() == PlayerState.WAITING) {
			LangUtil.sendMessage(player, LangUtil.LEFT_DURING_MATCH.toString());
			sumoPlayer.getSumo().removePlayer(sumoPlayer, false);
		} else if (sumoPlayer.getState() == PlayerState.FIGHTING) {
			knockedOut(sumoPlayer);
		} else if (sumoPlayer.getSumo().getSpectatingPlayers().contains(sumoPlayer)) {
			sumoPlayer.getSumo().removePlayer(sumoPlayer, false);
		}
	}

	public void knockedOut(SumoPlayer sumoPlayer) {
		SumoPlayer opponent = null;

		for (SumoPlayer sumoPlayers : sumoPlayer.getSumo().getPlayers()) {
			if (sumoPlayers.getState() == PlayerState.FIGHTING && !sumoPlayers.equals(sumoPlayer)) {
				opponent = sumoPlayers;
			}
		}

		LangUtil.sendMessage(sumoPlayer.getPlayer(), LangUtil.LOST_MATCH.toString().replaceAll("%player%", opponent == null ? "None"
				: opponent.getPlayer().getName()));

		SumoArena sumo = sumoPlayer.getSumo();

		sumoPlayer.getSumo().removePlayer(sumoPlayer, false);
		sumo.wonMatch(opponent, sumoPlayer);
	}
}