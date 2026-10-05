package com.benzimmer123.sumo.handlers;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.api.objects.SumoTick;
import com.benzimmer123.sumo.obj.TempSumoPlayer;
import com.benzimmer123.sumo.obj.serial.MemorySumoArena;
import com.benzimmer123.sumo.obj.serial.MemorySumoTick;
import com.benzimmer123.sumo.storage.GsonStorage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SumoHandler {

	private final static SumoHandler INSTANCE;
	private List<SumoArena> sumos;
	private Map<UUID, SumoPlayer> sumoPlayers;
	private Map<ZonedDateTime, SumoArena> upcomingDates;
	private SumoTick sumoTick;

	static {
		INSTANCE = new SumoHandler();
	}

	private SumoHandler() {
		sumos = Lists.newArrayList();
		sumoPlayers = Maps.newHashMap();
		upcomingDates = Maps.newHashMap();

		sumoTick = new MemorySumoTick();

		for (File file : new File(Sumo.getInstance().getDataFolder() + "/sumos").listFiles()) {
			SumoArena sumo = GsonStorage.deserialize(MemorySumoArena.class, file.getPath(), "sumo");
			if (sumo != null) {
				addSumo(sumo);
			}
		}
	}

	public SumoTick getSumoTick() {
		return sumoTick;
	}

	public Map<ZonedDateTime, SumoArena> getUpcomingDates() {
		return upcomingDates;
	}

	public void setUpcomingDates(Map<ZonedDateTime, SumoArena> upcomingDates) {
		this.upcomingDates = upcomingDates;
	}

	public Map<UUID, SumoPlayer> getSumoPlayers() {
		return sumoPlayers;
	}

	public void addPlayer(Player player) {
		sumoPlayers.put(player.getUniqueId(), new TempSumoPlayer(player));
	}

	public void removePlayer(Player player) {
		sumoPlayers.remove(player.getUniqueId());
	}

	public SumoArena getSumo(String name) {
		for (SumoArena sumo : sumos) {
			if (sumo.getName().equalsIgnoreCase(name))
				return sumo;
		}
		return null;
	}

	public SumoPlayer getSumoPlayer(Player player) {
		if (!sumoPlayers.containsKey(player.getUniqueId())) {
			sumoPlayers.put(player.getUniqueId(), new TempSumoPlayer(player));
		}
		return sumoPlayers.get(player.getUniqueId());
	}

	public List<SumoArena> getSumos() {
		return sumos;
	}

	public void addSumo(SumoArena sumo) {
		sumos.add(sumo);
	}

	public void removeSumo(SumoArena sumo) {
		sumos.remove(sumo);
	}

	public static SumoHandler getInstance() {
		return INSTANCE;
	}

}
