package com.benzimmer123.sumo.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.util.PlaceholderUtil;

public class ClipPlaceholderAPI extends PlaceholderExpansion {

	// Identifier for this expansion
	@Override
	public String getIdentifier() {
		return "sumo";
	}

	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
	public String getAuthor() {
		return "Benzimmer";
	}

	// Since we are registering this expansion from the dependency, this can be
	// null
	@Override
	public String getPlugin() {
		return null;
	}

	// Return the plugin version since this expansion is bundled with the
	// dependency
	@Override
	public String getVersion() {
		return Sumo.getInstance().getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String placeholder) {
		if (placeholder == null) {
			return "";
		}

		switch (placeholder) {
		case "total_wins":
			return PlaceholderUtil.getWinAmount(player) + "";
		case "status":
			return PlaceholderUtil.getStatus();
		case "time_until_start":
			return PlaceholderUtil.getTimeUntilStart();
		case "players_left":
			return PlaceholderUtil.getPlayersLeft();
		case "name":
			return PlaceholderUtil.getNames();
		case "scheduled_next":
			return PlaceholderUtil.getScheduledNext();
		case "scheduled_countdown":
			return PlaceholderUtil.timeUntilNextSchedule();
		case "scheduled_name":
			return PlaceholderUtil.getScheduledNextName();
		}

		return null;
	}
}