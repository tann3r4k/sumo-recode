package com.benzimmer123.sumo.util;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoTopPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.handlers.TopHandler;
import com.benzimmer123.sumo.managers.SumoManager;

public class PlaceholderUtil {

	public static String getNames() {
		StringBuilder message = new StringBuilder("");

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			String toAppend = message.length() == 0 ? sumo.getName() : ", " + sumo.getName();
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getTimeUntilStart() {
		StringBuilder message = new StringBuilder("");

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			String toAppend = message.length() == 0 ? sumo.getTimeUntilStart() + "" : ", " + sumo.getTimeUntilStart();
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getPlayersLeft() {
		StringBuilder message = new StringBuilder("");

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			String toAppend = message.length() == 0 ? sumo.getPlayers().size() + "" : ", " + sumo.getPlayers().size();
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getStatus() {
		StringBuilder message = new StringBuilder("");

		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			String toAppend = message.length() == 0 ? (sumo.getState() == GameState.NOT_STARTED ? LangUtil.SUMO_INACTIVE.toString()
					: LangUtil.SUMO_ACTIVE.toString()) + ""
					: ", " + (sumo.getState() == GameState.NOT_STARTED ? LangUtil.SUMO_INACTIVE.toString() : LangUtil.SUMO_ACTIVE.toString());
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String timeUntilNextSchedule() {
		List<SumoArena> sumos = SumoHandler.getInstance().getSumos();

		if (sumos.isEmpty())
			return LangUtil.NO_SUMO.toString();

		if (DateUtil.getNextStart() == null)
			return LangUtil.NO_SCHEDULE.toString();

		return DateUtil.getScheduleCountdown();
	}
	
	public static int getWinAmount(Player player) {
		Map<String, SumoTopPlayer> sumoMap = TopHandler.getInstance().getPlayers();

		if (sumoMap.containsKey(player.getUniqueId().toString())) {
			return sumoMap.get(player.getUniqueId().toString()).getWins();
		}

		return 0;
	}

	public static String getScheduledNext() {
		List<SumoArena> sumos = SumoHandler.getInstance().getSumos();

		if (sumos.isEmpty())
			return LangUtil.NO_SUMO.toString();

		if (new SumoManager().isActive()) {
			return LangUtil.ACTIVE_NOW.toString().replaceAll("%sumo%", getNames());
		}

		ZonedDateTime zdt = DateUtil.getNextStart();

		if (zdt == null)
			return LangUtil.NO_SCHEDULE.toString();

		return LangUtil.NEXT_SCHEDULE.toString().replaceAll("%month%", zdt.getMonthValue() + "").replaceAll("%day%", zdt.getDayOfMonth() + "").replaceAll(
				"%hour%", zdt.getHour() + "").replaceAll("%minute%", zdt.getMinute() + "");
	}

	public static String getScheduledNextName() {
		List<SumoArena> sumos = SumoHandler.getInstance().getSumos();

		if (sumos.isEmpty())
			return LangUtil.NO_SUMO.toString();

		String sumo = DateUtil.getNextStartName();

		if (sumo == null)
			return LangUtil.NO_SCHEDULE.toString();

		return LangUtil.NEXT_SCHEDULED_NAME.toString().replaceAll("%sumo%", sumo);
	}
}
