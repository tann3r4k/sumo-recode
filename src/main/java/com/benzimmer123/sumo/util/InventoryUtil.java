package com.benzimmer123.sumo.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.sumo.hooks.Magic;

public class InventoryUtil {

	public static boolean checkInventoryEmpty(Player player) {
		if (Bukkit.getPluginManager().isPluginEnabled("Magic")) {
			return Magic.checkInventoryEmpty(player);
		}

		for (ItemStack it : player.getInventory().getContents()) {
			if (it != null)
				return false;
		}
		return true;
	}
}