package de.wroracer.snowbattle.listenders;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuitListender implements Listener {

    @EventHandler
    public void onPlayerLeaveEvnet(PlayerQuitEvent event) {
        Game game = PluginMain.getMain().gameManager.getGameOfPlayer(event.getPlayer());
        if (game != null) {
            game.leaveGame(event.getPlayer());
            event.setQuitMessage("");
        }
    }
}
