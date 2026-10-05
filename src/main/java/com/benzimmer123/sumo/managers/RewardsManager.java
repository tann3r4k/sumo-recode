package com.benzimmer123.sumo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.hooks.Vault;

public class RewardsManager {

	public void rewardMoney(Player p) {
		if (Sumo.getInstance().getConfig().isSet("REWARDS.VAULT.AMOUNT") && Sumo.getInstance().getConfig().getBoolean("REWARDS.VAULT.ENABLED")) {
			new Vault().depositPlayer(p, Sumo.getInstance().getConfig().getInt("REWARDS.VAULT.AMOUNT"));
		}
	}

	public void runCommands(Player p, String worldname, String sumo) {
		if (Sumo.getInstance().getConfig().isSet("REWARDS.RUN_COMMANDS")) {
			for (String s : Sumo.getInstance().getConfig().getStringList("REWARDS.RUN_COMMANDS")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replaceAll("%player%", p.getName()).replaceAll("%world%",
						worldname).replace("/", "").replaceAll("%sumo%", sumo));
			}
		}
	}
}
