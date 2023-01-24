package de.wroracer.snowbattle.commands.subCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public interface SubCommandExecuter {
    boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command);

    HashMap<String, SubCommandExecuter> getSubCommands();
}
