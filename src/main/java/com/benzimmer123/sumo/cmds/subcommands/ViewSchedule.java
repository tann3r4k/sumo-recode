package com.benzimmer123.sumo.cmds.subcommands;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.Map;

import com.benzimmer123.sumo.util.TextUtil;
import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class ViewSchedule extends SubCommand {

	public ViewSchedule(Sumo instance) {
		super(instance, true);
		addAlias("times");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Map<ZonedDateTime, SumoArena> sortedDates = SumoHandler.getInstance().getUpcomingDates();

		for (DayOfWeek dow : EnumSet.allOf(DayOfWeek.class)) {
			LangUtil.sendMessage(sender, LangUtil.DAY_OF_WEEK.toString().replaceAll("%day%", TextUtil.capitalize(dow.toString())));
			for (ZonedDateTime zdt : sortedDates.keySet()) {
				if (zdt.getDayOfWeek().equals(dow)) {
					String hour = zdt.getHour() <= 9 ? "0" + zdt.getHour() : zdt.getHour() + "";
					String minute = zdt.getMinute() <= 9 ? "0" + zdt.getMinute() : zdt.getMinute() + "";
					if (sortedDates.get(zdt) != null) {
						LangUtil.sendMessage(sender, LangUtil.STARTS_ON.toString().replaceAll("%sumo%", sortedDates.get(zdt).getName())
								.replaceAll("%hour%", hour).replaceAll("%minute%", minute));
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo times";
	}

	@Override
	public String getPermission() {
		return "SUMO.VIEWTIME";
	}

	@Override
	public String getDescription() {
		return "View the upcoming Sumo schedule.";
	}
}