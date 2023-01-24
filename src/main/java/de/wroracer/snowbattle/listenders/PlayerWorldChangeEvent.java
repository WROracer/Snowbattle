package de.wroracer.snowbattle.listenders;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.game.Game;
import de.wroracer.snowbattle.game.Status;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeEvent implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Game game = PluginMain.getMain().gameManager.getGameforWorld(event.getPlayer().getWorld());
        if (game != null) {
            if (game.getStatus() == Status.LOBBY) {
                game.joinGame(event.getPlayer());
            }
        }
    }
}
