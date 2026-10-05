package com.benzimmer123.sumo.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.elmakers.mine.bukkit.api.magic.Mage;
import com.elmakers.mine.bukkit.api.magic.MageController;
import com.elmakers.mine.bukkit.api.magic.MagicAPI;
import com.elmakers.mine.bukkit.api.wand.Wand;

public final class Magic {

	private Magic() {
	}

	public static void closeWands(Player player) {
		try {
			Mage mage = mage(player);
			if (mage == null) {
				return;
			}
			Wand offhand = mage.getOffhandWand();
			if (offhand != null && offhand.isInventoryOpen()) {
				offhand.deactivate();
			}
			mage.deactivate();
		} catch (Throwable ignored) {
		}
	}

	public static void resumeWand(Player player) {
		try {
			Mage mage = mage(player);
			if (mage != null) {
				mage.checkWand();
			}
		} catch (Throwable ignored) {
		}
	}

	public static boolean checkInventoryEmpty(Player player) {
		MageController controller = controller();
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null || item.getType() == Material.AIR) {
				continue;
			}
			if (controller != null && (controller.isWand(item) || controller.isSkill(item))) {
				continue;
			}
			return false;
		}
		return true;
	}

	private static Mage mage(Player player) {
		MageController controller = controller();
		return controller == null ? null : controller.getMage(player);
	}

	private static MageController controller() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Magic");
		if (!(plugin instanceof MagicAPI)) {
			return null;
		}
		return ((MagicAPI) plugin).getController();
	}
}
