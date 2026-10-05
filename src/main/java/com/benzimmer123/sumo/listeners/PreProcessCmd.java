package com.benzimmer123.sumo.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class PreProcessCmd implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/sumo"))
			return;

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(e.getPlayer());

		if (sumoPlayer.getState() == PlayerState.NOT_PLAYING)
			return;

		if (Sumo.getInstance().getConfig().isSet("WHITE_LISTED_CMDS")) {
			List<String> list = Sumo.getInstance().getConfig().getStringList("WHITE_LISTED_CMDS");

			if (!list.contains(e.getMessage())) {
				e.setCancelled(true);
				LangUtil.sendMessage(e.getPlayer(), LangUtil.BLOCKED_CMD.toString());
			}
		}
	}
}
