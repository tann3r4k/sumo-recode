package com.benzimmer123.sumo.cmds.subcommands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class ScheduleDaily extends SubCommand {

	public ScheduleDaily(Sumo instance) {
		super(instance, true);
		addAlias("daily");
		addAlias("scheduledaily");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		String[] date = args[1].split(":");

		if (date.length <= 1) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_TIME.toString());
			return false;
		}

		int hour = 0;

		if (date[0] != null) {
			try {
				hour = Integer.parseInt(date[0]);
			} catch (NumberFormatException e) {
				hour = -1;
			}
		}

		if (hour == -1 || hour > 23) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_HOUR.toString());
			return false;
		}

		int minute = 0;

		if (date[1] != null) {
			try {
				minute = Integer.parseInt(date[1]);
			} catch (NumberFormatException e) {
				minute = -1;
			}
		}

		if (minute == -1 || minute > 59) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_MINUTE.toString());
			return false;
		}

		ZoneId zoneId = ZoneId.of(Sumo.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime scheduledTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), hour, minute);
		ZonedDateTime zdt = scheduledTime.atZone(zoneId);

		sumo.getSumoScheduler().addDaily(zdt);
		sumo.save();

		LangUtil.sendMessage(sender, LangUtil.SCHEDULED_DAILY.toString().replaceAll("%sumo%", args[0]).replaceAll("%time%", args[1]));
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 3)
			return true;
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo daily <arena> <time_of_day>";
	}

	@Override
	public String getPermission() {
		return "SUMO.DAILYSCHEDULE";
	}

	@Override
	public String getDescription() {
		return "Create an automatic Sumo that will run daily.";
	}
}
