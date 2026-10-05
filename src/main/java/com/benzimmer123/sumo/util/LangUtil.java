package com.benzimmer123.sumo.util;

import org.bukkit.command.CommandSender;

import com.benzimmer123.sumo.Sumo;

public enum LangUtil {

	PREFIX("&8[&cSumo&8] ", false),
	VERSION("&eYou are currently using the Sumo version %version%. It is advised to always use the latest version.", true),
	HELP_MENU_TITLE("&8=====> &2Sumo Help &7(%page%/%maxpage%) &8<=====", false),
	HELP_ENTRY("&a%cmd% &7%desc%", false),
	SUMO_LIST_TITLE("&8=====> &2Current Sumos &8<=====", false),
	SUMO_LIST_ENTRY("&a- &7%sumo%", false),
	LEFT_DURING_MATCH("&cYou have left Sumo.", true),
	LEAVE_SUMO("&6%player% &ehas left Sumo (&6%players%/%maxplayers%&e).", true),
	MATCH_STARTING("&eYour match is starting against %player%! (Round: &a%round%&e)", true),
	LOST_MATCH("&cYou have been knocked out by %player%.", true),
	WON_MATCH("&aYou have won against %player%!", true),
	FIGHTING_PLAYERS("&eMatch starting! &a%player1% &evs &a%player2% &e(Round: &a%round%&e)", true),
	WON_SUMO("&6%player% &ehas won the Sumo event!", true),
	TIME_LEFT("&eThe Sumo event in %sumo% is starting in &6%time% &eseconds. Do &6/Sumo join &eto join the event.", true),
	JOIN_SUMO("&6%player% &ehas joined Sumo (&6%players%/%maxplayers%&e).", true),
	ENTER_SPECTATE("&eYou are now in spectate mode.", true),
	NEXT_SCHEDULED_NAME("%sumo%", false),
	NEXT_SCHEDULE("&aStarting on %day%/%month% at %hour%:%minute%.", true),
	STARTED_SUMO(
			"&6%player% &ehas just started a Sumo event in %sumo%. This will begin in &6%time% &eseconds. Type &6/Sumo join %sumo% &eto join the event.",
			true),
	IN_COMBAT("&cYou have recently been in combat, please wait &6%time% &cseconds to join Sumo.", true),
	COMBAT_TAG_PLUS("&cYou are currently in combat, please wait.", true),
	INV_NOT_EMPTY("&cYour inventory needs to be empty to join Sumo.", true),
	ILLEGAL_CHAR("&cYou can only use characters for the name.", true),
	AUTOMATIC_HOSTNAME("The Console", false),
	SCHEDULED_COUNTDOWN("&aStarting in %time%.", false),
	DAY_OF_WEEK("&8=====> &2%day% &8<=====", false),
	STARTS_ON("&a%sumo% starts at %hour%:%minute%.", false),
	LIST_SUMO_IDS_TITLE("&8=====> &2%day% &8<=====", false),
	LIST_SUMO_IDS_ENTRY("&aScheduled ID - &e%id% &ais starting at &e%hour%:%minute% &a- &e%type%", false),
	SAVED_KIT("&aYou have saved the sumo kit to your current inventory.", true),
	RENAMED_SUMO("&aYou have renamed %previous% to %new%.", true),
	REMOVED_SCHEDULE("&aRemoved scheduled Sumo with ID: %id%", true),
	END_SUMO("&6%player% &ehas ended the Sumo event.", true),
	STARTING_SOON("&aStarting in %time%...", false),
	SUMO_FULL("&cThe Sumo is currently full!", true),
	SUMO_ACTIVE("&aActive", false),
	SUMO_INACTIVE("&cNot Active", false),
	ACTIVE_NOW("&a%sumo% is currently active!", false),
	BLOCKED_CMD("&cYou are not allowed to execute this command while in Sumo.", true),
	SET_SPAWNPOINT("&aYou have successfully set spawnpoint %pos% location for Sumo in the arena %sumo%.", true),
	SET_LOBBY("&aYou have successfully set the lobby spawn location for Sumo in the arena %sumo%.", true),
	SET_FLOOR("&aYou have successfully set the floor for Sumo in the arena %sumo%.", true),
	SET_SPECTATE("&aYou have successfully set the spectate location for Sumo in the arena %sumo%.", true),
	LEFT_CLICK("&ePlease click a block to set the floor for Sumo.", true),
	CREATED_SUMO("&aSuccessfully created Sumo arena %sumo%.", true),
	DELETED_SUMO("&aSuccessfully deleted Sumo arena %sumo%.", true),
	RELOAD("&aSuccessfully reloaded all Sumo configuration files.", true),
	IN_GAME_ONLY("&cThis command can only be used in-game.", true),
	LOCATIONS_NOT_SET("&cSumo locations have not been set for this arena.", true),
	ALREADY_EXISTS("&cThis Sumo already exists.", true),
	ALREADY_QUEUED("&cYou are already queued for Sumo.", true),
	ALREADY_IN_PROGESS("&cThere is currently a game in progress.", true),
	INVALID_SUMO("&cThis Sumo does not exist.", true),
	SCHEDULED_DAILY("&aYou have successfully scheduled the Sumo %sumo% to run at %time% everyday.", true),
	SCHEDULED_DATE("&aYou have successfully scheduled the Sumo %sumo% at %time% on %day%/%month%.", true),
	SCHEDULED_WEEKLY("&aYou have successfully scheduled the Sumo %sumo% to run at %time% every week.", true),
	NOT_STARTED("&cSumo has not started yet.", true),
	NOT_QUEUED("&cYou are currently not queued for Sumo.", true),
	NOT_IN_PROGRESS("&cThat sumo is currently not in progress.", true),
	NOT_ENOUGH_PLAYERS("&cThere was not enough players to start the Sumo event.", true),
	NO_HELP_PAGE("&cThis number does not exist, the max help page is %maxpage%.", true),
	NO_PERMISSION("&cYou do not have permission for that command", true),
	INVALID_WEEKDAY("&cYou have entered an invalid weekday: &f%validtypes%", true),
	INVALID_TIME("&cYou need to define a time for the Sumo to start for example 17:00.", true),
	INVALID_HOUR("&cYou have entered an incorrect value for the hour you would like the Sumo to be.", true),
	INVALID_MINUTE("&cYou have entered an incorrect value for the minutes you would like the Sumo to be.", true),
	INVALID_MONTH("&cYou have entered an incorrect value for the month you would like the Sumo to be.", true),
	INVALID_DAY("&cYou have entered an incorrect value for the day you would like the Sumo to be.", true),
	INVALID_ID("&cPlease enter a correct ID number. You can use /sumo scheduled <arena> to view IDs.", true),
	NO_SCHEDULE("&aNo scheduled Sumos.", false),
	NO_SUMO("&aNone", false);

	private String defaultValue;
	private boolean usePrefix;

	LangUtil(String defaultValue, boolean usePrefix) {
		this.defaultValue = defaultValue;
		this.usePrefix = usePrefix;
	}

	public String getDefault() {
		return this.defaultValue;
	}

	public String getPath() {
		return this.name();
	}

	public boolean usePrefix() {
		return this.usePrefix;
	}

	@Override
	public String toString() {
		boolean usePrefix = false;
		String prefix = null;

		if (Sumo.getInstance().getSettingsManager().getLang().isSet(LangUtil.PREFIX.getPath())) {
			if (!Sumo.getInstance().getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()).equalsIgnoreCase("") && Sumo.getInstance()
					.getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()) != null && this.usePrefix()) {
				usePrefix = true;
				prefix = ColourUtil.replaceString(Sumo.getInstance().getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()));
			}
		}

		if (Sumo.getInstance().getSettingsManager().getLang().isSet(getPath())) {
			if (Sumo.getInstance().getSettingsManager().getLang().getString(getPath()).equalsIgnoreCase("") || Sumo.getInstance().getSettingsManager()
					.getLang().getString(getPath()) == null) {
				return "";
			}
			return usePrefix ? prefix + ColourUtil.replaceString(Sumo.getInstance().getSettingsManager().getLang().getString(getPath()))
					: ColourUtil.replaceString(Sumo.getInstance().getSettingsManager().getLang().getString(getPath()));
		}

		return usePrefix ? prefix + ColourUtil.replaceString(this.defaultValue) : ColourUtil.replaceString(this.defaultValue);
	}

	public static void sendMessage(CommandSender sender, String replacedString) {
		if (replacedString.equalsIgnoreCase(""))
			return;

		String[] message = replacedString.split("\\\\n");

		for (String msg : message) {
			if (Sumo.getInstance().getPlaceholderManager().isClipPlaceholderAPIHooked())
				msg = Sumo.getInstance().getPlaceholderManager().parsePlaceholders(null, msg);
			sender.sendMessage(msg.replaceAll("\\n", ""));
		}
	}
}
