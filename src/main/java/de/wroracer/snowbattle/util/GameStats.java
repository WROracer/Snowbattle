package de.wroracer.snowbattle.util;

import de.wroracer.snowbattle.PluginMain;
import org.bukkit.entity.Player;

public class GameStats {
    private Player player;
    private int wins;
    private int losses;
    private int highestPoints;

    public GameStats(Player player, int wins, int losses, int highestPoints) {
        this.player = player;
        this.wins = wins;
        this.losses = losses;
        this.highestPoints = highestPoints;
    }

    public static GameStats getGameStats(Player player) {
        return PluginMain.getMain().getStatConfig().loadStat(player);
    }

    public void saveState() {
        PluginMain.getMain().getStatConfig().saveStat(this);
    }

    public void setPoints(int points) {
        if (points >= highestPoints) {
            highestPoints = points;
        }
    }

    public void addWin() {
        wins++;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getHighestPoints() {
        return highestPoints;
    }

    public void addLoss() {
        losses++;
    }
}
