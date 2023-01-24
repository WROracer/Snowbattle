package de.wroracer.snowbattle.commands;

import de.wroracer.snowbattle.PluginMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class JoinGameCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            PluginMain.getMain().gameManager.joinGame(args[0], Bukkit.getPlayer(commandSender.getName()));
        } else {
            commandSender.sendMessage("§4Benutzung /join {Arena Name}\nUsage /join {Arena Name}");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        PluginMain.getMain().arenas.forEach(arena -> list.add(arena.getArenaName()));
        return list;
    }
}
