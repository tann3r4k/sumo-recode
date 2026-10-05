package com.benzimmer123.sumo.cmds.rootcommand;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.google.common.collect.Lists;

public abstract class SubCommand {

	protected Sumo plugin;
	private List<String> identifiers;
	private boolean consoleAllowed;

	private static final String godPermission = "SUMO.*";

	public SubCommand(Sumo plugin, boolean consoleAllowed) {
		this.identifiers = Lists.newArrayList();
		this.plugin = plugin;
		this.consoleAllowed = consoleAllowed;
	}

	public abstract boolean performCommand(CommandSender sender, String[] args);

	public abstract boolean validArgumentLength(int length);

	public abstract String getHelp();

	public abstract String getPermission();

	public abstract String getDescription();

	public void performHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Correct Usage: " + ChatColor.YELLOW + getHelp());
	}

	public boolean hasPermission(CommandSender sender) {
		if (sender.isOp() || sender.hasPermission(getPermission()) || sender.hasPermission(godPermission))
			return true;
		return false;
	}

	public boolean isConsoleAllowed() {
		return consoleAllowed;
	}

	public void addAlias(String alias) {
		this.identifiers.add(alias);
	}

	public List<String> getIdentifiers() {
		return identifiers;
	}

}
