package com.benzimmer123.sumo.tasks;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.benzimmer123.sumo.api.objects.SumoArena;
import com.google.common.collect.Lists;

public class SumoTask extends BukkitRunnable {

	private static List<SumoTask> sumoTasks = Lists.newArrayList();
	private SumoArena sumo;
	private boolean cancel;

	public SumoTask(SumoArena sumo) {
		this.sumo = sumo;
		sumoTasks.add(this);
	}

	public SumoTask() {
	}

	@Override
	public void run() {
		if (!cancel) {
			sumo.call();
		} else {
			cancel();
		}
	}

	public List<SumoTask> getTasks() {
		return sumoTasks;
	}

	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public SumoArena getSumo() {
		return sumo;
	}

}
