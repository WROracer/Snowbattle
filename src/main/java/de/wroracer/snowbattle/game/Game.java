package de.wroracer.snowbattle.game;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.MainConfig;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.util.GameStats;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game {
    private final Arena arena;
    private final List<Player> players;
    private final HashMap<Player, Location> joinLocation;
    private final Thread startThread;
    private final int startTime;
    private final MainConfig mc;
    private final TextConfig tc;
    private final List<Item> spawntItems;
    private final HashMap<Player, Integer> colectedPresents;
    private Status status;
    private PresentSpawner presentSpawner;
    private SnowBallSpawner snowBallSpawner;
    private int presentSchedId = -1;
    private int snowBallSchedId = -1;
    private int timerSchedId = -1;
    private int gameTimeMinute;
    private int gameTimeSecunde;
    private Player firstPlace = null;
    private Player secondPlace = null;
    private Player thirdPlace = null;

    public Game(Arena arena, Player... players) {

        mc = PluginMain.getMain().getMainConfig();
        tc = PluginMain.getMain().getTextConfig();

        spawntItems = new ArrayList<>();

        this.arena = arena;
        this.players = new ArrayList<>();
        joinLocation = new HashMap<>();

        colectedPresents = new HashMap<>();

        joinGame(players);
        status = Status.LOBBY;
        if (arena.getLobby() == null) {
            status = Status.NOT_PLAY_ABLE;
        }
        if (arena.getPos1() == null) {
            status = Status.NOT_PLAY_ABLE;
        }
        if (arena.getPos2() == null) {
            status = Status.NOT_PLAY_ABLE;
        }
        for (int i = 0; i != arena.getSpawns().size(); i++) {
            if (arena.getSpawns().get(i) == null) {
                status = Status.NOT_PLAY_ABLE;
                break;
            }
        }
        for (int i = 0; i != arena.getPresentSpawn().size(); i++) {
            if (arena.getPresentSpawn().get(i) == null) {
                status = Status.NOT_PLAY_ABLE;
                break;
            }
        }


        startTime = /*10;*/mc.getStartTime();

        startThread = new Thread(() -> {
            for (int time = startTime; time != -1; time--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (time) {
                    case 60:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 60 Sekunden"));
                        break;
                    case 30:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 30 Sekunden"));
                        break;
                    case 20:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 20 Sekunden"));
                        break;
                    case 15:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 15 Sekunden"));
                        break;
                    case 10:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 10 Sekunden"));
                        break;
                    case 5:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 5 Sekunden"));
                        break;
                    case 4:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 4 Sekunden"));
                        break;
                    case 3:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 3 Sekunden"));
                        break;
                    case 2:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 2 Sekunden"));
                        break;
                    case 1:
                        this.players.forEach(player -> player.sendMessage("Die Runde Startet in 1 Sekunde"));
                        break;
                    case 0:
                        startGame();
                        break;
                }
            }
        });
    }

    public void joinGame(Player... player) {
        for (int i = 0; i != player.length; i++) {
            if (this.players.size() >= arena.getMaxPlayerCount()) {
                player[i].sendMessage(tc.getMessage("Chat.ArenaIsInGame").replace("{ArenaName}", arena.getEffectiveName()));
                return;
            }
            if (!this.players.contains(player[i])) {
                int finalI = i;
                if (players.size() != 0)
                    players.forEach(player1 -> player1.sendMessage(tc.getMessage("Chat.RoundJoin.Other").replace("{PlayerName}", player[finalI].getDisplayName())));
                player[i].sendMessage(tc.getMessage("Chat.RoundJoin.Player").replace("{ArenaName}", arena.getEffectiveName()));
                players.add(player[i]);
                player[i].setHealth(20);
                colectedPresents.put(player[i], 0);
                player[i].setFoodLevel(20);
                player[i].setGameMode(GameMode.ADVENTURE);
                joinLocation.put(player[i], player[i].getLocation());
                player[i].teleport(arena.getLobby());
                player[i].getInventory().clear();
                generateLobbyScoarboard();
                player[i].setLevel(0);
                if (players.size() >= arena.getMinPlayerCount()) {
                    startThread.start();
                }

            } else {
                player[i].sendMessage(tc.getMessage("Chat.ArenaIsInGame"));
            }
        }
    }

    public void startGame() {
        status = Status.INGAME;
        List<Location> spawns = new ArrayList<>();
        spawns.addAll(arena.getSpawns());

        Random random = new Random();
        players.forEach(player -> {
            Location location = spawns.get(random.nextInt(spawns.size()));
            player.teleport(location);
            spawns.remove(location);
        });
        snowBallSpawner = new SnowBallSpawner(arena);
        presentSpawner = new PresentSpawner(arena);

        Game game = this;

        presentSchedId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
                Item item = presentSpawner.spawnPresent();
                if (item != null) {
                    /*Bukkit.broadcastMessage("Neues Geschenk");*/
                    game.addItem(item);
                }
            }
        }, 20, 20);

        snowBallSchedId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
                Item item = snowBallSpawner.spawnSnowBall();
                if (item != null) {
                    /*Bukkit.broadcastMessage("Neuer SChnee ball");*/
                    game.addItem(item);
                }
            }
        }, 30, 20);


        long time = /*121;*/mc.getGameTime();

        gameTimeMinute = (int) time / 60;
        gameTimeSecunde = (int) ((time - (gameTimeMinute * 60)));


        timerSchedId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {

                String time = tc.getMessage("AktionBar.Timer");
                if (gameTimeSecunde <= 9) {
                    players.forEach(player -> sendActionText(player, time.replace("MM", "" + gameTimeMinute).replace("SS", "0" + gameTimeSecunde)));
                } else {
                    players.forEach(player -> sendActionText(player, time.replace("MM", "" + gameTimeMinute).replace("SS", "" + gameTimeSecunde)));
                }
                gameTimeSecunde--;
                if (gameTimeSecunde <= 0) {
                    gameTimeSecunde = 60;
                    gameTimeMinute--;
                }
            }
        }, 20, 20);


        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        }, 20 * time);

    }

    private void generateLobbyScoarboard() {

    }

    public void addItem(Item item) {
        spawntItems.add(item);
        onlineCheck();
    }

    public void sendActionText(Player player, String message) {
        //TODO
        /*PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);*/
    }

    private void endGame() {
        firstPlace = null;
        secondPlace = null;
        thirdPlace = null;
        List<Player> other = new ArrayList<>();
        players.forEach(player -> {
            int presents = colectedPresents.get(player);
            if (firstPlace == null) {
                firstPlace = player;
            } else if (colectedPresents.get(firstPlace) <= presents) {
                thirdPlace = secondPlace;
                secondPlace = firstPlace;
                firstPlace = player;
            } else if (secondPlace == null) {
                secondPlace = player;
            } else if (colectedPresents.get(secondPlace) <= presents) {
                thirdPlace = secondPlace;
                secondPlace = player;
            } else if (thirdPlace == null) {
                thirdPlace = player;
            } else if (colectedPresents.get(thirdPlace) <= presents) {
                thirdPlace = player;
            } else {
                other.add(player);
            }
        });
        if (firstPlace != null) {
            firstPlace.teleport(arena.getFirstPlace());
        }
        if (secondPlace != null) {
            secondPlace.teleport(arena.getSecondPlace());
        }
        if (thirdPlace != null) {
            thirdPlace.teleport(arena.getThirdPlace());
        }
        GameStats gameStatsWin = GameStats.getGameStats(firstPlace);
        gameStatsWin.addWin();
        gameStatsWin.addWin();
        gameStatsWin.addWin();
        gameStatsWin.saveState();
        String message = "Platzierung: \n 1. " + firstPlace.getName() + "(" + colectedPresents.get(firstPlace) + ")";
        message = tc.getMessage("Chat.Winner.FirstPlace").replace("{PlayerName}", firstPlace.getDisplayName()).replace("{Points}", "" + colectedPresents.get(firstPlace));
        if (secondPlace != null) {
            gameStatsWin = GameStats.getGameStats(secondPlace);
            gameStatsWin.addWin();
            gameStatsWin.addWin();
            gameStatsWin.saveState();
            message = message + tc.getMessage("Chat.Winner.SecondPlace").replace("{PlayerName}", secondPlace.getDisplayName()).replace("{Points}", "" + colectedPresents.get(secondPlace));
            if (thirdPlace != null) {
                gameStatsWin = GameStats.getGameStats(thirdPlace);
                gameStatsWin.addWin();
                gameStatsWin.saveState();
                message = message + tc.getMessage("Chat.Winner.ThirdPlace").replace("{PlayerName}", thirdPlace.getDisplayName()).replace("{Points}", "" + colectedPresents.get(thirdPlace));
            }
        }
        String finalMessage = message;
        players.forEach(player -> {
            player.sendMessage(finalMessage);
        });

        other.forEach(player -> {
            player.teleport(arena.getOtherWinners());
        });
        players.forEach(player -> {
            player.setLevel(0);
            player.getInventory().clear();
            GameStats gameStats = GameStats.getGameStats(player);
            gameStats.setPoints(colectedPresents.get(player));
            gameStats.saveState();
        });
        stopGameMechanic();


        long time = /*20;*/mc.getEndTime();

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
                stopGame();
            }
        }, 20 * time);
    }

    private void onlineCheck() {
        List<Player> offlinePlayers = new ArrayList<>();
        players.forEach(player -> {
            if (!player.isOnline()) {
                offlinePlayers.add(player);
            } else {
                player.setHealth(20);
                player.setSaturation(20);
            }
        });
        offlinePlayers.forEach(this::leaveGame);
        offlinePlayers.clear();
    }

    public void stopGameMechanic() {
        if (presentSchedId != -1) {
            Bukkit.getScheduler().cancelTask(presentSchedId);
        }
        if (snowBallSchedId != -1) {
            Bukkit.getScheduler().cancelTask(snowBallSchedId);
        }
        if (timerSchedId != -1) {
            Bukkit.getScheduler().cancelTask(timerSchedId);
        }
        spawntItems.forEach(item -> {
            if (item != null) {
                item.remove();
            }
        });
    }

    public void stopGame() {
        stopGameMechanic();
        leaveAll();

        PluginMain.getMain().gameManager.removeGame(this);
    }

    public void leaveGame(Player... player) {
        for (int i = 0; i != player.length; i++) {
            players.remove(player[i]);
            int finalI = i;
            if (players.size() != 0) {
                int finalI1 = i;
                players.forEach(player1 -> {
                    if (player1 != player[finalI1]) {
                        player1.sendMessage(tc.getMessage("Chat.RoundLeave.Other").replace("{PlayerName}", player[finalI].getDisplayName()));
                    }
                });
            }
            player[i].sendMessage(tc.getMessage("Chat.RoundLeave.Player"));
            player[i].getInventory().clear();
            player[i].setLevel(0);
            player[i].teleport(joinLocation.get(player[i]));
            joinLocation.remove(player[i], player[i].getLocation());
        }
    }

    private void leaveAll() {
        players.forEach(player -> {
            if (players.size() != 0)
                players.forEach(player1 -> {
                    if (player1 != player) {
                        player1.sendMessage(tc.getMessage("Chat.RoundLeave.Other").replace("{PlayerName}", player.getDisplayName()));
                    }
                });
            player.sendMessage(tc.getMessage("Chat.RoundLeave.Player"));
            player.teleport(joinLocation.get(player));
            joinLocation.remove(player, player.getLocation());
        });
        players.clear();
    }

    public void respawnPlayer(Player player) {
        Random random = new Random();
        Location location = arena.getSpawns().get(random.nextInt(arena.getSpawns().size()));
        player.getInventory().clear();
        player.teleport(location);
    }

    public void addPresentToPlayer(Player player, int amount) {
        int i = colectedPresents.get(player);
        player.sendMessage(tc.getMessage("Chat.YourPresents").replace("{Presents}", "" + (i + amount)));
        player.setLevel(i + amount);
        colectedPresents.put(player, i + amount);
    }

    public int getPresentsFromPlayer(Player player) {
        return colectedPresents.get(player);
    }

    public Arena getArena() {
        return arena;
    }

    public Status getStatus() {
        return status;
    }


    public List<Player> getPlayers() {
        return players;
    }
}
