package com.benzimmer123.sumo.util;

import java.io.File;

import com.benzimmer123.sumo.Sumo;

public final class FileUtil {

	public static void createFolders(){
		if (!new File(Sumo.getInstance().getDataFolder() + "/sumos").exists()) {
			new File(Sumo.getInstance().getDataFolder() + "/sumos").mkdirs();
		}
	
		if (!new File(Sumo.getInstance().getDataFolder() + "/top").exists()) {
			new File(Sumo.getInstance().getDataFolder() + "/top").mkdirs();
		}

		if (!new File(Sumo.getInstance().getDataFolder() + "/top/players").exists()) {
			new File(Sumo.getInstance().getDataFolder() + "/top/players").mkdirs();
		}
	}
	
}
