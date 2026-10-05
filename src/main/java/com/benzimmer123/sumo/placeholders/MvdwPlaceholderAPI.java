package com.benzimmer123.sumo.placeholders;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.util.PlaceholderUtil;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class MvdwPlaceholderAPI {

	public void register() {
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_total_wins", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return "" + PlaceholderUtil.getWinAmount(e.getPlayer()); 
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_status", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getStatus();
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_time_until_start", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getTimeUntilStart();
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_players_left", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getPlayersLeft();
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_name", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getNames(); 
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_scheduled_next", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getScheduledNext();
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_scheduled_countdown", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.timeUntilNextSchedule();
			}
		});
		
		PlaceholderAPI.registerPlaceholder(Sumo.getInstance(), "sumo_scheduled_name", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getScheduledNextName();
			}
		});
	}
}