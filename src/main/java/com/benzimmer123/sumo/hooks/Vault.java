package com.benzimmer123.sumo.hooks;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

	private static Economy econ;
	private static boolean hooked;

	public boolean isHooked() {
		return hooked;
	}

	public boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		hooked = econ != null;
		return econ != null;
	}

	public void depositPlayer(Player p, int amount) {
		econ.depositPlayer(p, amount);
	}
}
