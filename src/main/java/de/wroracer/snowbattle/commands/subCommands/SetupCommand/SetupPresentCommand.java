package de.wroracer.snowbattle.commands.subCommands.SetupCommand;

import de.wroracer.snowbattle.commands.subCommands.SetupCommand.present.SetupPresentAddCommand;
import de.wroracer.snowbattle.commands.subCommands.SetupCommand.present.SetupPresentGoalCommand;
import de.wroracer.snowbattle.commands.subCommands.SetupCommand.present.SetupPresentRemoveCommand;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupPresentCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        commandSender.sendMessage("Mit /setup " + arenaName + " present add Eine Geschenk Spawn Hinzufügen\nMit /setup " + arenaName + " present delete-all Alle Geschenk Spawns entfernen");
        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        HashMap<String, SubCommandExecuter> subCommands = new HashMap<>();
        subCommands.put("add", new SetupPresentAddCommand());
        subCommands.put("delete-all", new SetupPresentRemoveCommand());
        subCommands.put("goal", new SetupPresentGoalCommand());
        return subCommands;
    }
}
