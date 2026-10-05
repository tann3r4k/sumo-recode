package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.BroadcastUtil;
import com.benzimmer123.sumo.util.LangUtil;

public class End extends SubCommand {

	public End(Sumo instance) {
		super(instance, true);
		addAlias("end");
		addAlias("finish");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.NOT_IN_PROGRESS.toString());
			return false;
		}

		sumo.end();

		String name = sender instanceof ConsoleCommandSender ? LangUtil.AUTOMATIC_HOSTNAME.toString() : sender.getName();
		BroadcastUtil.call(LangUtil.END_SUMO.toString().replaceAll("%player%", name), null, true);
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
		return "/sumo end <name>";
	}

	@Override
	public String getPermission() {
		return "SUMO.END";
	}

	@Override
	public String getDescription() {
		return "End a Sumo arena in progress.";
	}
}
