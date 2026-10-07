package com.benzimmer123.sumo.storage;

public final class ArenaFiles {

	private ArenaFiles() {
	}

	public static boolean isArenaSave(String fileName) {
		return fileName.endsWith(".json");
	}
}
