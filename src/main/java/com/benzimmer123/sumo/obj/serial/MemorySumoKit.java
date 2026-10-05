package com.benzimmer123.sumo.obj.serial;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.sumo.api.objects.SumoKit;
import com.benzimmer123.sumo.storage.GsonStorage;
import com.google.common.collect.Maps;

public class MemorySumoKit implements SumoKit, Serializable {

	private static final long serialVersionUID = -4588510274629783422L;
	private Map<Integer, String> itemStacks;
	private String[] armourContents;

	public MemorySumoKit(Player player) { 
		itemStacks = Maps.newHashMap();
		armourContents = new String[4];

		int slot = 0;

		for (ItemStack item : player.getInventory().getContents()) {
			if (item != null)
				itemStacks.put(slot, GsonStorage.itemStackToBase64(item));
			slot++;
		}

		int armourSlot = 0;

		for (ItemStack armour : player.getInventory().getArmorContents()) {
			armourContents[armourSlot] = GsonStorage.itemStackToBase64(armour);
			armourSlot++;
		}
	}

	public void loadKit(Player player) {
		if (itemStacks == null || armourContents == null)
			return;

		for (int item : itemStacks.keySet()) {
			player.getInventory().setItem(item, toItemStack(itemStacks.get(item)));
		}

		player.getInventory().setBoots(toItemStack(armourContents[0]));
		player.getInventory().setLeggings(toItemStack(armourContents[1]));
		player.getInventory().setChestplate(toItemStack(armourContents[2]));
		player.getInventory().setHelmet(toItemStack(armourContents[3]));
	}

	public ItemStack toItemStack(String item) {
		if (item != null) {
			try {
				return GsonStorage.fromBase64(item);
			} catch (IOException e) {
			}
		}
		return new ItemStack(Material.AIR);
	}
}
