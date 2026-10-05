package com.benzimmer123.sumo.obj;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.hooks.Magic;

public class TempData {

	private ItemStack[] armourContents;
	private ItemStack[] invContents;
	private Location previousLoc;

	public TempData(ItemStack[] armourContents, ItemStack[] invContents, Location previousLoc) {
		this.invContents = invContents;
		this.armourContents = armourContents;
		this.previousLoc = previousLoc;
	}

	public Location getPreviousLocation() {
		return previousLoc;
	}

	public ItemStack[] getArmour() {
		return armourContents;
	}

	public ItemStack[] getInventory() {
		return invContents;
	}

	public void restore(SumoArena sumo, Player player) {
		Sumo.getInstance().getSumoManager().cleanPlayer(player);

		if (invContents != null)
			player.getInventory().setContents(getInventory());

		if (armourContents != null)
			player.getInventory().setArmorContents(getArmour());

		Magic.resumeWand(player);

		if (sumo.getSumoLocation().getExitLocation() != null) {
			player.teleport(sumo.getSumoLocation().getExitLocation());
		} else {
			if (previousLoc != null)
				player.teleport(getPreviousLocation());
		}
	}
}
