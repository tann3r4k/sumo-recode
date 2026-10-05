package com.benzimmer123.sumo.cmds.rootcommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.cmds.subcommands.Create;
import com.benzimmer123.sumo.cmds.subcommands.Delete;
import com.benzimmer123.sumo.cmds.subcommands.End;
import com.benzimmer123.sumo.cmds.subcommands.Help;
import com.benzimmer123.sumo.cmds.subcommands.Join;
import com.benzimmer123.sumo.cmds.subcommands.Leave;
import com.benzimmer123.sumo.cmds.subcommands.List;
import com.benzimmer123.sumo.cmds.subcommands.Reload;
import com.benzimmer123.sumo.cmds.subcommands.Rename;
import com.benzimmer123.sumo.cmds.subcommands.SaveKit;
import com.benzimmer123.sumo.cmds.subcommands.ScheduleDaily;
import com.benzimmer123.sumo.cmds.subcommands.ScheduleDate;
import com.benzimmer123.sumo.cmds.subcommands.ScheduleRemove;
import com.benzimmer123.sumo.cmds.subcommands.ScheduleTaskIDs;
import com.benzimmer123.sumo.cmds.subcommands.ScheduleWeekly;
import com.benzimmer123.sumo.cmds.subcommands.SetExitTeleport;
import com.benzimmer123.sumo.cmds.subcommands.SetLobby;
import com.benzimmer123.sumo.cmds.subcommands.SetPlatform;
import com.benzimmer123.sumo.cmds.subcommands.SetPositions;
import com.benzimmer123.sumo.cmds.subcommands.SetSpectate;
import com.benzimmer123.sumo.cmds.subcommands.Start;
import com.benzimmer123.sumo.cmds.subcommands.Version;
import com.benzimmer123.sumo.cmds.subcommands.ViewSchedule;
import com.benzimmer123.sumo.util.LangUtil;
import com.google.common.collect.Lists;

public class RootCommand implements CommandExecutor {

	private ArrayList<SubCommand> subCommands;

	public RootCommand(Sumo instance) {
		this.subCommands = Lists.newArrayList();
		this.subCommands.add(new Create(instance));
		this.subCommands.add(new Delete(instance));
		this.subCommands.add(new End(instance));
		this.subCommands.add(new Join(instance));
		this.subCommands.add(new Leave(instance));
		this.subCommands.add(new List(instance));
		this.subCommands.add(new Reload(instance));
		this.subCommands.add(new Rename(instance));
		this.subCommands.add(new SaveKit(instance));
		this.subCommands.add(new SetLobby(instance));
		this.subCommands.add(new SetPlatform(instance));
		this.subCommands.add(new SetPositions(instance));
		this.subCommands.add(new Start(instance));
		this.subCommands.add(new Version(instance));
		this.subCommands.add(new SetSpectate(instance));
		this.subCommands.add(new ScheduleDaily(instance));
		this.subCommands.add(new ScheduleWeekly(instance));
		this.subCommands.add(new ScheduleDate(instance));
		this.subCommands.add(new ScheduleRemove(instance));
		this.subCommands.add(new ScheduleTaskIDs(instance));
		this.subCommands.add(new ViewSchedule(instance));
		this.subCommands.add(new SetExitTeleport(instance));
		this.subCommands.add(new Help(instance, subCommands));
	}

	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
		if (paramArrayOfString.length >= 1) {
			SubCommand subcommand = null;
			for (SubCommand subcommand1 : this.subCommands) {
				if (subcommand1.getIdentifiers().contains(paramArrayOfString[0].toLowerCase())) {
					subcommand = subcommand1;
					break;
				}
			}
			if (subcommand != null) {
				if (!subcommand.isConsoleAllowed() && paramCommandSender instanceof ConsoleCommandSender) {
					LangUtil.sendMessage(paramCommandSender, LangUtil.IN_GAME_ONLY.toString());
					return true;
				}

				if (!subcommand.validArgumentLength(paramArrayOfString.length)) {
					subcommand.performHelp(paramCommandSender);
					return true;
				}

				if (paramCommandSender instanceof Player) {
					Player player = (Player) paramCommandSender;
					if (passChecks(subcommand, player))
						return subcommand.performCommand((CommandSender) player, Arrays.<String> copyOfRange(paramArrayOfString, 1,
								paramArrayOfString.length));
					return true;
				}
				subcommand.performCommand(paramCommandSender, Arrays.<String> copyOfRange(paramArrayOfString, 1, paramArrayOfString.length));
				return true;
			}
		}
		if (paramCommandSender instanceof Player) {
			SubCommand subcommand = (SubCommand) Objects.requireNonNull(this.subCommands.stream().filter(paramSubcommand -> paramSubcommand
					.getIdentifiers().contains("help")).findAny().orElse(null));
			((SubCommand) subcommand).performCommand(paramCommandSender, paramArrayOfString);
			return true;
		}
		return true;
	}

	private boolean passChecks(SubCommand command, Player player) {
		boolean hasPermission = command.hasPermission(player);
		if (!hasPermission)
			LangUtil.sendMessage(player, LangUtil.NO_PERMISSION.toString());
		return hasPermission;
	}

	public int getCommandSize() {
		return subCommands.size();
	}
}
