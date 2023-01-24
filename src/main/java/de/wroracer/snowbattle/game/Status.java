package de.wroracer.snowbattle.game;

public enum Status {
    LOBBY("Lobby"),
    INGAME("InGame"),
    NOT_PLAY_ABLE("Nicht Spielbar");

    private final String translation;

    Status(String status) {
        this.translation = status;
    }

    public String getStatus() {
        return translation;
    }
}
