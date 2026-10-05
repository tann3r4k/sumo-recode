package com.benzimmer123.sumo.util;

import org.bukkit.Bukkit;

public enum ServerVersionUtil {
	PAPER_26;

	public static ServerVersionUtil getServerVersion() {
		return PAPER_26;
	}

	public static boolean isAboveVersion(ServerVersionUtil version) {
		return true;
	}

	@Override
	public String toString() {
		try {
			return Bukkit.getMinecraftVersion();
		} catch (Throwable ignored) {
			return name();
		}
	}
}
