package com.benzimmer123.sumo.handlers;

import java.io.File;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoTopPlayer;
import com.benzimmer123.sumo.obj.serial.MemorySumoTopPlayer;
import com.benzimmer123.sumo.storage.GsonStorage;
import com.google.common.collect.Maps;

public class TopHandler {

	private final static TopHandler INSTANCE;
	private LinkedHashMap<String, SumoTopPlayer> topPlayers;

	static {
		INSTANCE = new TopHandler();
	}

	private TopHandler() {
		topPlayers = Maps.newLinkedHashMap();

		for (File file : new File(Sumo.getInstance().getDataFolder() + "/top/players").listFiles()) {
			SumoTopPlayer player = GsonStorage.deserialize(MemorySumoTopPlayer.class, file.getPath(), "player");
			if (player != null) {
				addPlayer(player);
			}
		}
	}

	public void setTopPlayers(LinkedHashMap<String, SumoTopPlayer> result) {
		this.topPlayers = result;
	}
	
	public LinkedHashMap<String, SumoTopPlayer> getPlayers() {
		return topPlayers;
	}

	public void addPlayer(SumoTopPlayer player) {
		topPlayers.put(player.getUUID(), player);
	}

	public SumoTopPlayer getPlayer(Player player) {
		if (topPlayers.containsKey(player.getUniqueId().toString())) {
			return topPlayers.get(player.getUniqueId().toString());
		}
		return new MemorySumoTopPlayer(player);
	}

	public static TopHandler getInstance() {
		return INSTANCE;
	}

}
