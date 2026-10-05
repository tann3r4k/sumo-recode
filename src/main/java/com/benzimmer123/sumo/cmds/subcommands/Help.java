package com.benzimmer123.sumo.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.util.LangUtil;
import com.google.common.collect.Lists;

public class Help extends SubCommand {

	private final List<SubCommand> commands;

	public Help(Sumo instance, List<SubCommand> commands) {
		super(instance, true);
		this.commands = commands;
		addAlias("help");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int pageNumber;

		if (args.length == 1) {
			try {
				pageNumber = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		} else {
			pageNumber = 1;
		}

		int amountPerPage = 5;
		int maxNumber = pageNumber * amountPerPage;
		int minNumber = maxNumber - amountPerPage;

		while (commands.size() < maxNumber) {
			maxNumber--;
		}

		List<SubCommand> displayedCmds = Lists.newArrayList();

		for (int i = minNumber; i < maxNumber; i++) {
			displayedCmds.add(commands.get(i));
		}

		int maxPages = (int) Math.ceil((double) commands.size() / (double) amountPerPage);
		
		if (pageNumber > maxPages) {
			LangUtil.sendMessage(sender, LangUtil.NO_HELP_PAGE.toString().replaceAll("%maxpage%", maxPages + ""));
			return false;
		}
		
		LangUtil.sendMessage(sender, LangUtil.HELP_MENU_TITLE.toString().replaceAll("%page%", pageNumber + "").replaceAll("%maxpage%", maxPages + ""));

		for (SubCommand command : displayedCmds) {
			LangUtil.sendMessage(sender,
					LangUtil.HELP_ENTRY.toString().replaceAll("%cmd%", command.getHelp()).replaceAll("%desc%", command.getDescription()));
		}

		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		return true;
	}

	@Override
	public String getHelp() {
		return "/sumo help";
	}

	@Override
	public String getPermission() {
		return "SUMO.HELP";
	}

	@Override
	public String getDescription() {
		return "View the Sumo help menu.";
	}
}
