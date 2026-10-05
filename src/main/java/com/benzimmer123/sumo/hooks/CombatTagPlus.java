package com.benzimmer123.sumo.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatTagPlus {

	private static boolean isHooked;

	public void setupCombat() {
		if (Bukkit.getPluginManager().isPluginEnabled("CombatTagPlus")) {
			isHooked = true;
		}
	}

	public boolean isHooked() {
		return isHooked;
	}

	public boolean inCombat(Player player) {
		if (new net.minelink.ctplus.CombatTagPlus().getTagManager().getTag(player.getUniqueId()) != null) {
			return true;
		}
		return false;
	}

	public void unTag(Player player) {
		if (!isHooked)
			return;
		new net.minelink.ctplus.CombatTagPlus().getTagManager().untag(player.getUniqueId());
	}

}
