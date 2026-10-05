package be.maximvdw.placeholderapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceholderAPI {
	public static boolean registerPlaceholder(Plugin plugin, String identifier, PlaceholderReplacer replacer) {
		return true;
	}

	public static String replacePlaceholders(Player player, String text) {
		return text;
	}
}
