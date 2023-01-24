package de.wroracer.snowbattle.commands.subCommands;

import de.wroracer.snowbattle.PluginMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LeaveGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        PluginMain.getMain().gameManager.leaveGame(Bukkit.getPlayer(commandSender.getName()));
        return false;
    }
}
