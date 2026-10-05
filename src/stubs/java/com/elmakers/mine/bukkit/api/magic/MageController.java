package com.elmakers.mine.bukkit.api.magic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MageController {
	Mage getMage(Player player);

	boolean isWand(ItemStack item);

	boolean isSkill(ItemStack item);
}
