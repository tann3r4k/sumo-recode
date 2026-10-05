package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.hooks.CombatTagPlus;
import com.benzimmer123.sumo.util.InventoryUtil;
import com.benzimmer123.sumo.util.LangUtil;

public class Join extends SubCommand {

	public Join(Sumo instance) {
		super(instance, false);
		addAlias("join");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (plugin.getConfig().getBoolean("FORCE_EMPTY_INV_ON_JOIN") && !InventoryUtil.checkInventoryEmpty(player)) {
			LangUtil.sendMessage(sender, LangUtil.INV_NOT_EMPTY.toString());
			return false;
		}

		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(player);

		if (plugin.getConfig().getBoolean("COMBAT_TAG.ENABLED")) {
			if (sumoPlayer.inCombat()) {
				long remainingTime = sumoPlayer.getCombatTime() - System.currentTimeMillis();
				int seconds = (int) (remainingTime / 1000);
				LangUtil.sendMessage(sender, LangUtil.IN_COMBAT.toString().replaceAll("%time%", seconds + ""));
				return false;
			}
		}

		if (new CombatTagPlus().isHooked() && new CombatTagPlus().inCombat(player)) {
			LangUtil.sendMessage(sender, LangUtil.COMBAT_TAG_PLUS.toString());
			return false;
		}

		if (sumoPlayer.getState() != PlayerState.NOT_PLAYING) {
			LangUtil.sendMessage(sender, LangUtil.ALREADY_QUEUED.toString());
			return false;
		}

		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		if (sumo.getState() != GameState.IN_LOBBY) {
			LangUtil.sendMessage(sender, LangUtil.NOT_STARTED.toString());
			return false;
		}

		if (!sumo.isJoinable()) {
			LangUtil.sendMessage(sender, LangUtil.SUMO_FULL.toString());
			return false;
		}

		sumo.join(player);
		sumoPlayer.setState(PlayerState.IN_LOBBY);
		sumoPlayer.setSumo(sumo);
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo join <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.JOIN";
	}

	@Override
	public String getDescription() {
		return "Join a Sumo arena in progress.";
	}
}
