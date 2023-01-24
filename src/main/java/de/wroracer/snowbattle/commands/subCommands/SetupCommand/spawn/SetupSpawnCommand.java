package de.wroracer.snowbattle.commands.subCommands.SetupCommand.spawn;

import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupSpawnCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        commandSender.sendMessage("Mit /setup " + arenaName + " spawn add eine Player Spawn Hinzufügen\nMit /setup " + arenaName + " spawn delete-all Alle Player Spawns entfernen");
        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        HashMap<String, SubCommandExecuter> subCommands = new HashMap<>();
        subCommands.put("add", new SetupSpawnCommand());
        subCommands.put("delete-all", new SetupSpawnRemoveCommand());
        return subCommands;
    }
}
