package com.benzimmer123.sumo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.cmds.rootcommand.RootCommand;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.hooks.CombatLogX;
import com.benzimmer123.sumo.hooks.CombatTagPlus;
import com.benzimmer123.sumo.hooks.Vault;
import com.benzimmer123.sumo.listeners.FactionsListener;
import com.benzimmer123.sumo.listeners.FoodLevelChange;
import com.benzimmer123.sumo.listeners.PlayerDamage;
import com.benzimmer123.sumo.listeners.PlayerDeath;
import com.benzimmer123.sumo.listeners.PlayerDropItem;
import com.benzimmer123.sumo.listeners.PlayerInteract;
import com.benzimmer123.sumo.listeners.PlayerJoin;
import com.benzimmer123.sumo.listeners.PlayerMove;
import com.benzimmer123.sumo.listeners.PlayerQuit;
import com.benzimmer123.sumo.listeners.PlayerTeleport;
import com.benzimmer123.sumo.listeners.PreProcessCmd;
import com.benzimmer123.sumo.managers.EventManager;
import com.benzimmer123.sumo.managers.PlaceholderManager;
import com.benzimmer123.sumo.managers.RewardsManager;
import com.benzimmer123.sumo.managers.SettingsManager;
import com.benzimmer123.sumo.managers.SumoManager;
import com.benzimmer123.sumo.tasks.ScheduledTask;
import com.benzimmer123.sumo.tasks.SortNextDayTask;
import com.benzimmer123.sumo.util.BroadcastUtil;
import com.benzimmer123.sumo.util.FileUtil;
import com.benzimmer123.sumo.util.LangUtil;
import com.benzimmer123.sumo.util.LoggerUtil;
import com.benzimmer123.sumo.util.MetricsUtil;
import com.benzimmer123.sumo.util.UpdateCheckerUtil;

public class Sumo extends JavaPlugin {

	private static Sumo INSTANCE;
	
	private final int PLUGIN_ID = 53174;
	private final int METRICS_ID = 15287;
	
	private SumoManager sumoManager;
	private EventManager eventManager;
	private PlaceholderManager placeholderManager;
	private SettingsManager settingsManager;
	private RewardsManager rewardsManager;

	public void onEnable() {
		INSTANCE = this;

		long startTime = System.currentTimeMillis();

		sumoManager = new SumoManager();
		placeholderManager = new PlaceholderManager();
		settingsManager = new SettingsManager();
		rewardsManager = new RewardsManager();
		eventManager = new EventManager();

		saveDefaultConfig();

		getSettingsManager().setup(this);

		RootCommand rootCmd = new RootCommand(this);
		getCommand("sumo").setExecutor(rootCmd);

		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
		getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);
		getServer().getPluginManager().registerEvents(new PreProcessCmd(), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);

		if (Bukkit.getPluginManager().isPluginEnabled("CombatLogX")) {
			getServer().getPluginManager().registerEvents(new CombatLogX(), this);
		}

		FileUtil.createFolders();
		getPlaceholderManager().setupPlaceholderAPI();

		new Vault().setupEconomy();
		new CombatTagPlus().setupCombat();

		if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
			getServer().getPluginManager().registerEvents(new FactionsListener(), this);
		}

		ScheduledTask schedulerTask = new ScheduledTask();
		schedulerTask.runTaskTimer(this, 0, 60 * 20);

		SortNextDayTask sortNextDayTask = new SortNextDayTask();
		sortNextDayTask.runTaskTimer(this, 0, 86400 * 20);
		
		String latestVersion = new UpdateCheckerUtil(getDescription().getVersion(), PLUGIN_ID).getLatestVersion();
		new LoggerUtil(latestVersion, rootCmd.getCommandSize(), startTime);
		new MetricsUtil(INSTANCE, METRICS_ID);
	}

	public void onDisable() {
		for (SumoArena sumo : SumoHandler.getInstance().getSumos()) {
			if (sumo.getState() != GameState.NOT_STARTED) {
				BroadcastUtil.call(LangUtil.END_SUMO.toString().replaceAll("%player%", LangUtil.AUTOMATIC_HOSTNAME.toString()), null, true);
				sumo.end();
			}
		}
	}

	public SumoManager getSumoManager() {
		return sumoManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public RewardsManager getRewardsManager() {
		return rewardsManager;
	}

	public static Sumo getInstance() {
		return INSTANCE;
	}
}
