package com.elmakers.mine.bukkit.api.magic;

import org.bukkit.plugin.Plugin;

public interface MagicAPI extends Plugin {
	MageController getController();
}
