package com.benzimmer123.sumo.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;

public final class BroadcastUtil {

	public static void call(String message, SumoArena sumo, boolean ignore) {
		if (ignore || Sumo.getInstance().getConfig().getBoolean("BROADCAST_TO_SERVER")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				LangUtil.sendMessage(player, message);
			}
		} else {
			for (SumoPlayer player : sumo.getPlayers()) {
				LangUtil.sendMessage(player.getPlayer(), message);
			}
		}
	}

}
