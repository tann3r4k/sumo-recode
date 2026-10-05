package com.benzimmer123.sumo.obj.serial;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import com.benzimmer123.sumo.hooks.Magic;
import com.benzimmer123.sumo.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.sumo.Sumo;
import com.benzimmer123.sumo.api.enums.GameState;
import com.benzimmer123.sumo.api.enums.PlayerState;
import com.benzimmer123.sumo.api.objects.SumoArena;
import com.benzimmer123.sumo.api.objects.SumoLocation;
import com.benzimmer123.sumo.api.objects.SumoPlayer;
import com.benzimmer123.sumo.api.objects.SumoScheduler;
import com.benzimmer123.sumo.api.objects.SumoTopPlayer;
import com.benzimmer123.sumo.handlers.SumoHandler;
import com.benzimmer123.sumo.handlers.TopHandler;
import com.benzimmer123.sumo.hooks.CombatTagPlus;
import com.benzimmer123.sumo.obj.TempData;
import com.benzimmer123.sumo.storage.GsonStorage;
import com.benzimmer123.sumo.tasks.SumoTask;
import com.benzimmer123.sumo.util.BroadcastUtil;
import com.benzimmer123.sumo.util.LangUtil;
import com.google.common.collect.Lists;

public class MemorySumoArena implements SumoArena, Serializable {

	private static final long serialVersionUID = -478084949483350105L;
	private MemorySumoLocation sumoLocation;
	private MemorySumoScheduler sumoScheduler;
	private String name;
	private MemorySumoKit sumoKit;
	private transient GameState gameState;
	private transient int roundCounter;
	private transient List<SumoPlayer> teleportExempt;
	private transient List<SumoPlayer> playersInGame;
	private transient List<SumoPlayer> nextRound;
	private transient List<SumoPlayer> spectatingPlayers;
	private transient int timeUntilStart;
	private transient boolean fightingPlayers;

	public MemorySumoArena(String name) {
		this.name = name;
		this.gameState = GameState.NOT_STARTED;
		this.sumoLocation = new MemorySumoLocation();
		this.sumoScheduler = new MemorySumoScheduler();
		SumoHandler.getInstance().addSumo(this);
		save();
	}

	public void call() {
		if (gameState == GameState.IN_LOBBY) {
			checkTimes(timeUntilStart);
			this.timeUntilStart--;

			if (timeUntilStart <= 0) {
				if (getPlayers().size() >= 2) {
					startGame();
				} else {
					BroadcastUtil.call(LangUtil.NOT_ENOUGH_PLAYERS.toString(), null, true);
					end();
				}
			}
		} else if (gameState == GameState.IN_GAME) {
			for (SumoPlayer sumoPlayer : getPlayers()) {
				// return here because players still fighting
				if (sumoPlayer.getState() == PlayerState.FIGHTING) {
					if (sumoPlayer.isFrozen()) {
						LangUtil.sendMessage(sumoPlayer.getPlayer(), LangUtil.STARTING_SOON.toString().replaceAll("%time%", sumoPlayer.getFrozenTime() + ""));
						sumoPlayer.setFrozenTime(sumoPlayer.getFrozenTime() - 1);

						if (sumoPlayer.getFrozenTime() == 0) {
							sumoPlayer.setFrozen(false);
						}
					}
				}
			}

			if (!fightingPlayers)
				startMatch();
		}
	}

	public void startCountdown() {
		this.gameState = GameState.IN_LOBBY;
		this.roundCounter = 1;
		this.timeUntilStart = Sumo.getInstance().getConfig().getInt("QUEUE_TIME");
		SumoTask sumoTask = new SumoTask(this);
		sumoTask.runTaskTimer(Sumo.getInstance(), 20, 20);
	}

	public void startGame() {
		this.gameState = GameState.IN_GAME;
		for (SumoPlayer sumoPlayer : getPlayers()) {
			SumoTopPlayer sumoTopPlayer = TopHandler.getInstance().getPlayer(sumoPlayer.getPlayer());
			sumoTopPlayer.setParticipations(sumoTopPlayer.getParticipations() + 1);
			sumoPlayer.setState(PlayerState.WAITING);
		}
	}

	public void roundEnd() {
		if (getNextRound().size() == 1) {
			wonGame(getNextRound().get(0));
			return;
		}

		getPlayers().clear();

		for (SumoPlayer sumoPlayer : getNextRound()) {
			getPlayers().add(sumoPlayer);
		}

		getNextRound().clear();

		roundCounter++;
	}

	public void startMatch() {
		if (getPlayers().size() == 0 && getNextRound().size() == 1) {
			wonGame(getNextRound().get(0));
			return;
		} else if (getPlayers().size() == 1 && getNextRound().size() == 0) {
			wonGame(getPlayers().get(0));
			return;
		} else if (getPlayers().size() == 1) {
			getNextRound().add(getPlayers().get(0));
			roundEnd();
			return;
		} else if (getPlayers().size() == 0) {
			roundEnd();
			return;
		}

		SumoPlayer[] players = getRandomPlayers();

		fightingPlayers = true;

		players[0].setState(PlayerState.FIGHTING);
		players[1].setState(PlayerState.FIGHTING);

		getExemptPlayers().add(players[0]);
		getExemptPlayers().add(players[1]);
		teleportPlayer(players[0], 1);
		teleportPlayer(players[1], 2);
		getExemptPlayers().remove(players[0]);
		getExemptPlayers().remove(players[1]);

		if (sumoKit != null) {
			sumoKit.loadKit(players[0].getPlayer());
			sumoKit.loadKit(players[1].getPlayer());
		}

		players[0].setFrozen(true);
		players[1].setFrozen(true);

		players[0].setFrozenTime(5);
		players[1].setFrozenTime(5);

		getPlayers().stream().filter(player -> player.getState().equals(PlayerState.IN_LOBBY)).forEach(player -> LangUtil.FIGHTING_PLAYERS.toString()
				.replaceAll("%player1%", players[1].getPlayer().getName()).replaceAll("%player2%", players[0].getPlayer().getName()).replaceAll(
						"%round", getRound() + ""));

		LangUtil.sendMessage(players[0].getPlayer(), LangUtil.MATCH_STARTING.toString().replaceAll("%player%", players[1].getPlayer().getName()).replaceAll(
				"%round%", getRound() + ""));
		LangUtil.sendMessage(players[1].getPlayer(), LangUtil.MATCH_STARTING.toString().replaceAll("%player%", players[0].getPlayer().getName()).replaceAll(
				"%round%", getRound() + ""));
	}

	public void join(Player p) {
		Magic.closeWands(p);
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(p);
		sumoPlayer.setPlayerData(new TempData(p.getInventory().getArmorContents(), p.getInventory().getContents(), p.getLocation().add(0, 1, 0)));
		p.teleport(getSumoLocation().getLobbyLocation());
		Sumo.getInstance().getSumoManager().cleanPlayer(p);
		addPlayer(p);
		BroadcastUtil.call(LangUtil.JOIN_SUMO.toString().replaceAll("%player%", p.getName()).replaceAll("%players%", getPlayers().size() + "").replaceAll(
				"%maxplayers%", "" + Sumo.getInstance().getConfig().getInt("MAXIMUM_PLAYERS")), this, false);
	}

	public boolean isJoinable() {
		if (getPlayers().size() < Sumo.getInstance().getConfig().getInt("MAXIMUM_PLAYERS"))
			return true;
		return false;
	}

	public void checkTimes(int time) {
		final List<Integer> times = (List<Integer>) Sumo.getInstance().getConfig().getIntegerList("BROADCAST_TIMES");

		if (times != null) {
			if (times.contains(time)) {
				BroadcastUtil.call(LangUtil.TIME_LEFT.toString().replaceAll("%time%", time + "").replaceAll("%sumo%", TextUtil.capitalize(getName())),
						null, true);
			}
		}
	}

	public SumoPlayer[] getRandomPlayers() {
		SumoPlayer[] players = new SumoPlayer[2];

		boolean foundPlayers = false;

		SumoPlayer p1 = getPlayers().get(new Random().nextInt(getPlayers().size()));
		SumoPlayer p2;

		do {
			p2 = getPlayers().get(new Random().nextInt(getPlayers().size()));
			if (p1 != p2) {
				foundPlayers = true;
			}
		} while (!foundPlayers);

		players[0] = p1;
		players[1] = p2;

		return players;
	}

	public void wonMatch(SumoPlayer winningPlayer, SumoPlayer losingPlayer) {
		fightingPlayers = false;
		new CombatTagPlus().unTag(winningPlayer.getPlayer());
		new CombatTagPlus().unTag(losingPlayer.getPlayer());
		Player winner = winningPlayer.getPlayer();
		Player loser = losingPlayer.getPlayer();
		LangUtil.sendMessage(winner, LangUtil.WON_MATCH.toString().replaceAll("%player%", loser.getName()));
		winningPlayer.setState(PlayerState.WAITING);
		getExemptPlayers().add(winningPlayer);
		winner.teleport(getSumoLocation().getLobbyLocation());
		Sumo.getInstance().getSumoManager().cleanPlayer(winner);
		getExemptPlayers().remove(winningPlayer);
		getPlayers().remove(winningPlayer);
		getNextRound().add(winningPlayer);
	}

	public void wonGame(SumoPlayer sumoPlayer) {
		Player p = sumoPlayer.getPlayer();

		if (!Sumo.getInstance().getEventManager().callSumoWinEvent(this, p))
			return;

		SumoTopPlayer sumoTopPlayer = TopHandler.getInstance().getPlayer(sumoPlayer.getPlayer());
		sumoTopPlayer.setWins(sumoTopPlayer.getWins() + 1);

		new CombatTagPlus().unTag(sumoPlayer.getPlayer());
		BroadcastUtil.call(LangUtil.WON_SUMO.toString().replaceAll("%sumo%", TextUtil.capitalize(getName())).replaceAll("%player%", p.getName()), null,
				true);
		end();

		Bukkit.getScheduler().runTaskLater(Sumo.getInstance(), () -> {
			Sumo.getInstance().getRewardsManager().runCommands(p, p.getWorld().getName(), getName());
			Sumo.getInstance().getRewardsManager().rewardMoney(p);
		}, 5L);
	}

	public void end() {
		if (!Sumo.getInstance().getEventManager().callSumoEndEvent(this))
			return;

		List<SumoPlayer> toRestore = Lists.newArrayList();

		for (SumoPlayer sumoPlayer : getPlayers()) {
			toRestore.add(sumoPlayer);
		}

		for (SumoPlayer sumoPlayer : getNextRound()) {
			toRestore.add(sumoPlayer);
		}

		for (SumoPlayer sumoPlayer : toRestore) {
			new CombatTagPlus().unTag(sumoPlayer.getPlayer());
			sumoPlayer.setFrozenTime(0);
			sumoPlayer.setFrozen(false);
			sumoPlayer.setState(PlayerState.NOT_PLAYING);
			sumoPlayer.setSumo(null);
			if (sumoPlayer.getPlayerData() != null) {
				sumoPlayer.getPlayerData().restore(this, sumoPlayer.getPlayer());
				sumoPlayer.setPlayerData(null);
			}
		}

		if (Sumo.getInstance().getConfig().getBoolean("SPECTATE_MODE") && sumoLocation.isSpectateSet()) {
			for (SumoPlayer sumoPlayer : Lists.newArrayList(getSpectatingPlayers())) {
				new CombatTagPlus().unTag(sumoPlayer.getPlayer());
				sumoPlayer.setState(PlayerState.NOT_PLAYING);
				sumoPlayer.setSumo(null);
				if (sumoPlayer.getPlayerData() != null) {
					sumoPlayer.getPlayerData().restore(this, sumoPlayer.getPlayer());
					sumoPlayer.setPlayerData(null);
				}
			}
		}

		getPlayers().clear();
		getNextRound().clear();
		getSpectatingPlayers().clear();

		gameState = GameState.NOT_STARTED;
		SumoTask task = null;

		for (SumoTask sumoTask : new SumoTask().getTasks()) {
			if (sumoTask.getSumo().equals(this)) {
				task = sumoTask;
				break;
			}
		}

		if (task != null) {
			new SumoTask().getTasks().remove(task);
			task.setCancelled(true);
		}

		fightingPlayers = false;
	}

	public void teleportPlayer(SumoPlayer sumoPlayer, int locationNumber) {
		new CombatTagPlus().unTag(sumoPlayer.getPlayer());
		sumoPlayer.getPlayer().teleport(getSumoLocation().getSpawnPoint(locationNumber));
	}

	public void setSumoKit(Player player) {
		sumoKit = new MemorySumoKit(player);
		save();
	}

	public void addPlayer(Player player) {
		SumoPlayer sumoPlayer = SumoHandler.getInstance().getSumoPlayer(player);
		playersInGame.add(sumoPlayer);
		sumoPlayer.setState(PlayerState.IN_LOBBY);
		sumoPlayer.setSumo(this);
	}

	public void removePlayer(SumoPlayer p, boolean nextRound) {
		new CombatTagPlus().unTag(p.getPlayer());
		getPlayers().remove(p);
		getNextRound().remove(p);
		p.setFrozen(false);
		p.setFrozenTime(0);

		if (!nextRound) {
			if (Sumo.getInstance().getConfig().getBoolean("SPECTATE_MODE") && sumoLocation.isSpectateSet() && p.getState() != PlayerState.IN_LOBBY
					&& p.getState() != PlayerState.SPECTATING) {
				getSpectatingPlayers().add(p);
				p.getPlayer().setAllowFlight(true);
				p.getPlayer().setFlying(true);
				p.setState(PlayerState.SPECTATING);
				getExemptPlayers().add(p);
				p.getPlayer().teleport(this.getSumoLocation().getSpectateLocation());
				getExemptPlayers().remove(p);
				LangUtil.sendMessage(p.getPlayer(), LangUtil.ENTER_SPECTATE.toString());
				return;
			}

			p.setState(PlayerState.NOT_PLAYING);
			p.setSumo(null);

			if (p.getPlayerData() != null) {
				p.getPlayerData().restore(this, p.getPlayer());
				p.setPlayerData(null);
			}
		}
	}

	public List<SumoPlayer> getSpectatingPlayers() {
		if (spectatingPlayers == null)
			spectatingPlayers = Lists.newArrayList();

		return spectatingPlayers;
	}

	public List<SumoPlayer> getExemptPlayers() {
		if (teleportExempt == null)
			teleportExempt = Lists.newArrayList();

		return teleportExempt;
	}

	public List<SumoPlayer> getPlayers() {
		if (playersInGame == null)
			playersInGame = Lists.newArrayList();

		return playersInGame;
	}

	public List<SumoPlayer> getNextRound() {
		if (nextRound == null)
			nextRound = Lists.newArrayList();

		return nextRound;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getRound() {
		return roundCounter;
	}

	public int getTimeUntilStart() {
		return timeUntilStart;
	}

	public GameState getState() {
		if (gameState == null)
			gameState = GameState.NOT_STARTED;

		return gameState;
	}

	public SumoLocation getSumoLocation() {
		return sumoLocation;
	}

	public SumoScheduler getSumoScheduler() {
		if (sumoScheduler == null) {
			this.sumoScheduler = new MemorySumoScheduler();
		}
		return sumoScheduler;
	}

	public void save() {
		GsonStorage.serialize(this, "sumos/" + getName().toLowerCase() + ".json", "sumo");
	}

}
