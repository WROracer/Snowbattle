package de.wroracer.snowbattle.util;

public class Present {
    private final String playerName;
    private final int value;
    private final int chances;

    public Present(String playerName, int value, int chances) {
        this.playerName = playerName;
        this.value = value;
        this.chances = chances;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getValue() {
        return value;
    }

    public int getChances() {
        return chances;
    }
}
