package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.util.LangUtil;

public class Version extends SubCommand {

	public Version(Sumo instance) {
		super(instance, true);
		addAlias("v");
		addAlias("version");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.VERSION.toString().replaceAll("%version%", plugin.getDescription().getVersion()));
		return false;
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
		return "/sumo version";
	}

	@Override
	public String getPermission() {
		return "SUMO.VERSION";
	}

	@Override
	public String getDescription() {
		return "View your current Sumo version.";
	}
}
