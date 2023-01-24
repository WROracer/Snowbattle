package de.wroracer.snowbattle.commands;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.game.Game;
import de.wroracer.snowbattle.game.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("SnowBattel.Start")) {
            if (commandSender instanceof Player) {
                Game game = PluginMain.getMain().gameManager.getGameOfPlayer((Player) commandSender);
                if (game != null) {
                    if (game.getStatus() == Status.LOBBY) {
                        game.startGame();
                    }
                }
            }
        }
        return false;
    }
}
