package com.benzimmer123.sumo.obj.serial;

import java.io.Serializable;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.api.objects.SumoTopPlayer;
import com.benzimmer123.sumo.handlers.TopHandler;
import com.benzimmer123.sumo.storage.GsonStorage;

public class MemorySumoTopPlayer implements SumoTopPlayer, Serializable {

	private static final long serialVersionUID = 2053975119095822870L;
	private int participations;
	private int wins;
	private final String playerName;
	private final String playerUUID;

	public MemorySumoTopPlayer(Player player) {
		playerName = player.getName();
		playerUUID = player.getUniqueId().toString();
		save();
		TopHandler.getInstance().addPlayer(this);
	}

	public void setParticipations(int participations) {
		this.participations = participations;
		save();
	}

	public void setWins(int wins) {
		this.wins = wins;
		save();
	}

	public int getParticipations() {
		return participations;
	}

	public int getWins() {
		return wins;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getUUID() {
		return playerUUID;
	}

	private void save() {
		GsonStorage.serialize(this, "top/players/" + getUUID() + ".json", "player");
	}

}
