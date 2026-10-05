package me.clip.placeholderapi.expansion;

import org.bukkit.entity.Player;

public abstract class PlaceholderExpansion {
	public abstract String getIdentifier();

	public abstract String getAuthor();

	public abstract String getVersion();

	public boolean persist() {
		return false;
	}

	public String getPlugin() {
		return null;
	}

	public abstract String onPlaceholderRequest(Player player, String params);

	public boolean register() {
		return true;
	}
}
