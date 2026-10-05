package com.benzimmer123.sumo.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.SubCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.util.LangUtil;

public class SaveKit extends SubCommand {

	public SaveKit(Sumo instance) {
		super(instance, false);
		addAlias("savekit");
		addAlias("kit");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		SumoArena sumo = SumoHandler.getInstance().getSumo(args[0]);

		if (sumo == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_SUMO.toString());
			return false;
		}

		sumo.setSumoKit(player);
		
		LangUtil.sendMessage(sender, LangUtil.SAVED_KIT.toString());		
		return false;
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
		return "/sumo savekit <arena>";
	}

	@Override
	public String getPermission() {
		return "SUMO.SAVEKIT";
	}

	@Override
	public String getDescription() {
		return "Save your inventory as the kit you spawn with in the Sumo arena.";
	}
}
