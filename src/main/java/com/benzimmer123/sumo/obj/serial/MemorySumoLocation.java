package com.benzimmer123.sumo.obj.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.benzimmer123.sumo.api.objects.SumoLocation;

public class MemorySumoLocation implements SumoLocation, Serializable {

	private static final long serialVersionUID = 7296119594157098352L;
	
	private String lobbyWorld;
	private double lobbySpawnX, lobbySpawnY, lobbySpawnZ;
	private float lobbySpawnYaw, lobbySpawnPitch;

	private String world;
	private double spawn1X, spawn1Y, spawn1Z;
	private float spawn1Yaw, spawn1Pitch;
	private double spawn2X, spawn2Y, spawn2Z;
	private float spawn2Yaw, spawn2Pitch;

	private int floorLocation;

	private String spectateWorld;
	private double spectateSpawnX, spectateSpawnY, spectateSpawnZ;
	private float spectateSpawnYaw, spectateSpawnPitch;

	private String exitWorld;
	private double exitSpawnX, exitSpawnY, exitSpawnZ;
	private float exitSpawnYaw, exitSpawnPitch;

	public MemorySumoLocation() {
		this.floorLocation = -3000;
	}

	public void setSpawnLocation(Location loc, String number) {
		if (number.equalsIgnoreCase("1")) {
			this.world = loc.getWorld().getName();
			this.spawn1X = loc.getX();
			this.spawn1Y = loc.getY();
			this.spawn1Z = loc.getZ();
			this.spawn1Yaw = loc.getYaw();
			this.spawn1Pitch = loc.getPitch();
		} else {
			this.world = loc.getWorld().getName();
			this.spawn2X = loc.getX();
			this.spawn2Y = loc.getY();
			this.spawn2Z = loc.getZ();
			this.spawn2Yaw = loc.getYaw();
			this.spawn2Pitch = loc.getPitch();
		}
	}

	public void setLobbyLocation(Location loc) {
		this.lobbySpawnX = loc.getX();
		this.lobbySpawnY = loc.getY();
		this.lobbySpawnZ = loc.getZ();
		this.lobbySpawnPitch = loc.getPitch();
		this.lobbySpawnYaw = loc.getYaw();
		this.lobbyWorld = loc.getWorld().getName();
	}

	public void setSpectateLocation(Location loc) {
		this.spectateSpawnX = loc.getX();
		this.spectateSpawnY = loc.getY();
		this.spectateSpawnZ = loc.getZ();
		this.spectateSpawnPitch = loc.getPitch();
		this.spectateSpawnYaw = loc.getYaw();
		this.spectateWorld = loc.getWorld().getName();
	}

	public void setExitLocation(Location loc) {
		this.exitSpawnX = loc.getX();
		this.exitSpawnY = loc.getY();
		this.exitSpawnZ = loc.getZ();
		this.exitSpawnPitch = loc.getPitch();
		this.exitSpawnYaw = loc.getYaw();
		this.exitWorld = loc.getWorld().getName();
	}

	public String getLobbyWorld() {
		return lobbyWorld;
	}

	public String getWorld() {
		return world;
	}

	public void setFloorLocation(int floorLocation) {
		this.floorLocation = floorLocation;
	}

	public int getFloorLocation() {
		return floorLocation;
	}

	public Location getLobbyLocation() {
		return new Location(Bukkit.getWorld(lobbyWorld), lobbySpawnX, lobbySpawnY, lobbySpawnZ, lobbySpawnYaw, lobbySpawnPitch);
	}

	public Location getSpectateLocation() {
		return new Location(Bukkit.getWorld(spectateWorld), spectateSpawnX, spectateSpawnY, spectateSpawnZ, spectateSpawnYaw, spectateSpawnPitch);
	}

	public Location getExitLocation() {
		if (exitWorld == null || Bukkit.getWorld(exitWorld) == null)
			return null;
		return new Location(Bukkit.getWorld(exitWorld), exitSpawnX, exitSpawnY, exitSpawnZ, exitSpawnYaw, exitSpawnPitch);
	}

	public boolean isSpectateSet() {
		return spectateWorld != null;
	}

	public Location getSpawnPoint(int locationNumber) {
		if (locationNumber == 1) {
			return new Location(Bukkit.getWorld(world), spawn1X, spawn1Y, spawn1Z, spawn1Yaw, spawn1Pitch);
		} else {
			return new Location(Bukkit.getWorld(world), spawn2X, spawn2Y, spawn2Z, spawn2Yaw, spawn2Pitch);
		}
	}

}
