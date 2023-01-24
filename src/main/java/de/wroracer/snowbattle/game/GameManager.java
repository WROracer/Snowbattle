package de.wroracer.snowbattle.game;


import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.TextConfig;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager {
    private final HashMap<String, Game> gameList;
    private final TextConfig tc;
    public Game game;

    public GameManager() {
        tc = PluginMain.getMain().getTextConfig();
        gameList = new HashMap<>();

    }

    public void stopAllGames() {
        gameList.keySet().forEach(key -> {
            System.out.println(key);
            gameList.get(key).stopGame();
        });
    }

    public Game getGameforWorld(World world) {
        game = null;
        gameList.keySet().forEach(key -> {
            if (gameList.get(key).getArena().getWorld() == world) {
                game = gameList.get(key);
            }
        });
        return game;
    }

    public void respawnPlayer(Player player) {
        gameList.keySet().forEach(key -> {
            if (gameList.get(key).getPlayers().contains(player)) {
                gameList.get(key).respawnPlayer(player);
            }
        });
    }

    public void joinGame(String arenaName, Player player) {
        if (isInRound(player)) {
            player.sendMessage(tc.getMessage("Chat.YouAreInARound"));
            return;
        }
        if (gameList.containsKey(arenaName)) {
            if (gameList.get(arenaName).getStatus() == Status.LOBBY) {
                gameList.get(arenaName).joinGame(player);
            } else {
                player.sendMessage(tc.getMessage("Chat.ArenaIsInGame").replace("{ArenaName}", arenaName));
            }
        } else {
            List<Arena> arenas = PluginMain.getMain().arenas;
            Arena arena = null;
            for (int i = 0; i != arenas.size(); i++) {
                if (arenas.get(i).getArenaName().equalsIgnoreCase(arenaName)) {
                    arena = arenas.get(i);
                    break;
                }
            }
            if (arena == null) {
                player.sendMessage(tc.getMessage("Chat.ArenaIsntExisting").replace("{ArenaName}", arenaName));
                return;
            }
            Game game = new Game(arena, player);
            gameList.put(arenaName, game);
        }
    }

    public boolean isInRound(Player player) {
        AtomicBoolean inRound = new AtomicBoolean(false);
        gameList.keySet().forEach(key -> {
            if (gameList.get(key).getPlayers().contains(player)) {
                inRound.set(true);
            }
        });
        return inRound.get();
    }

    public Game getGameOfPlayer(Player player) {
        game = null;
        gameList.keySet().forEach(key -> {
            if (gameList.get(key).getPlayers().contains(player)) {
                game = gameList.get(key);
            }
        });
        return game;
    }

    public void leaveGame(Player player) {
        AtomicBoolean hasLeft = new AtomicBoolean(false);
        List<String> toDelete = new ArrayList<>();
        List<Game> toStop = new ArrayList<>();
        gameList.keySet().forEach(key -> {
            if (!hasLeft.get()) {
                if (gameList.get(key).getPlayers().contains(player)) {
                    gameList.get(key).leaveGame(player);
                    if (gameList.get(key).getPlayers().size() == 0) {
                        toDelete.add(key);
                        toStop.add(gameList.get(key));
                    }
                    hasLeft.set(true);
                }
            }
        });
        toStop.forEach(Game::stopGame);
        toStop.clear();
        toDelete.forEach(game -> gameList.remove(game));
        toDelete.clear();
        if (!hasLeft.get()) {
            player.sendMessage(tc.getMessage("Chat.YouAreInNoRound"));
        }
    }

    public void removeGame(Game game) {
        gameList.remove(game.getArena().getArenaName());
    }
}
