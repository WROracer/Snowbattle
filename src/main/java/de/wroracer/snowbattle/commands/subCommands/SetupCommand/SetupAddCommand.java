package de.wroracer.snowbattle.commands.subCommands.SetupCommand;


import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetupAddCommand implements SubCommandExecuter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];

        List<String> arenaNames = new ArrayList<>();
        PluginMain.getMain().arenas.forEach(arena -> {
            arenaNames.add(arena.getArenaName());
        });
        if (args.length == 2) {
            if (!arenaNames.contains(arenaName)) {
                String minPlayers = args[1];
                Arena arena = new Arena(Bukkit.getPlayer(commandSender.getName()).getWorld(), arenaName, Integer.parseInt(minPlayers));
                PluginMain.getMain().addArena(arena);
                commandSender.sendMessage("Die Arena " + arenaName + " wurde Erstellt");
            } else {
                commandSender.sendMessage("Die Arena " + arenaName + " existiert bereits. Mit /setup " + arenaName + " delete kannst du sie Wieder Löschen");
            }
        } else {
            commandSender.sendMessage("Benutzung: /setup " + arenaName + " add {minPlayerCount}");
        }

        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        HashMap<String, SubCommandExecuter> subCommands = new HashMap<>();
        return subCommands;
    }


}
