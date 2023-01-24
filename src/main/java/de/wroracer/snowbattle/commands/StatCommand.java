package de.wroracer.snowbattle.commands;


import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.util.GameStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatCommand implements CommandExecutor {

    TextConfig tc;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            GameStats stats = GameStats.getGameStats((Player) commandSender);
            tc = PluginMain.getMain().getTextConfig();
            commandSender.sendMessage(tc.getMessage("Chat.Stats").replace("{Wins}", "" + stats.getWins()).replace("{Losses}", "" + stats.getLosses()).replace("{HighestPoints}", "" + stats.getHighestPoints()));
        }
        return false;
    }
}
