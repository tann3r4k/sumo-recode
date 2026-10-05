package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.util.LangUtil;

public class Reload extends SubCommand{

	public Reload(Sumo instance) {
		super(instance, true);
		addAlias("reload");
		addAlias("rl");
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Sumo.getInstance().getSettingsManager().setup(plugin);
		plugin.reloadConfig();
		LangUtil.sendMessage(sender, LangUtil.RELOAD.toString());
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if(length == 1){
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/sumo reload";
	}

	@Override
	public String getPermission() {
		return "SUMO.RELOAD";
	}

	@Override
	public String getDescription() {
		return "Reload all Sumo configuration files.";
	}
}
