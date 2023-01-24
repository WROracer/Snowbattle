package de.wroracer.snowbattle.commands.subCommands.SetupCommand.winner;

import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupWinnerCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        commandSender.sendMessage("Mit /setup " + arenaName + " winner 1 um die Position für den Ersten platz zu setzen\nMit /setup " + arenaName + " winner 2 denn Zweiten Platz{nMit /setup " + arenaName + " winner 3 den Dritten platz und mit /setup " + arenaName + " winner other die Position für alle anderen");
        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        HashMap<String, SubCommandExecuter> subCommands = new HashMap<>();
        subCommands.put("1", new SetupWinnerFirstCommand());
        subCommands.put("2", new SetupWinnerSecondCommand());
        subCommands.put("3", new SetupWinnerThirdCommand());
        subCommands.put("other", new SetupWinnerOtherCommand());
        return subCommands;
    }
}
