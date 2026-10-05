package com.benzimmer123.sumo.obj;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;

public class TempSumoPlayer implements SumoPlayer {

	private final Player player;
	private PlayerState state;
	private SumoArena sumo;
	private TempData playerData;
	private boolean isFrozen;
	private boolean isEditingFloor;
	private SumoArena editingArena;
	private int frozenTime;
	private long combatTime;

	public TempSumoPlayer(Player player) {
		this.player = player;
		this.state = PlayerState.NOT_PLAYING;
	}

	public void setCombatTime(long combatTime) {
		this.combatTime = combatTime;
	}

	public boolean inCombat() {
		if (combatTime == 0)
			return false;
		if (combatTime > System.currentTimeMillis())
			return true;
		return false;
	}

	public long getCombatTime() {
		return combatTime;
	}

	public TempData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(TempData playerData) {
		this.playerData = playerData;
	}

	public int getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(int frozenTime) {
		this.frozenTime = frozenTime;
	}

	public SumoArena getEditingArena() {
		return editingArena;
	}

	public void setEditingArena(SumoArena editingArena) {
		this.editingArena = editingArena;
	}

	public void setEditingFloor(boolean isEditingFloor) {
		this.isEditingFloor = isEditingFloor;
	}

	public boolean isEditingFloor() {
		return isEditingFloor;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setSumo(SumoArena sumo) {
		this.sumo = sumo;
	}

	public SumoArena getSumo() {
		return sumo;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerState getState() {
		return state;
	}

	public void setState(PlayerState state) {
		this.state = state;
	}
}
