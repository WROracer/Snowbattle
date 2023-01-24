package de.wroracer.snowbattle.commands;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SetupCommand.*;
import de.wroracer.snowbattle.commands.subCommands.SetupCommand.spawn.SetupSpawnCommand;
import de.wroracer.snowbattle.commands.subCommands.SetupCommand.winner.SetupWinnerCommand;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.config.TextConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SetupCommand implements CommandExecutor, TabCompleter {

    private final HashMap<String, SubCommandExecuter> subCommands = new HashMap<>();
    private TextConfig tc;

    public SetupCommand() {
        subCommands.put("add", new SetupAddCommand());
        subCommands.put("lobby", new SetupLobbyCommand());
        subCommands.put("delete", new SetupDeleteCommand());
        subCommands.put("pos1", new SetupPos1Command());
        subCommands.put("pos2", new SetupPos2Command());

        subCommands.put("present", new SetupPresentCommand());
        subCommands.put("spawn", new SetupSpawnCommand());

        subCommands.put("name", new SetupNameCommand());
        subCommands.put("minPlayers", new SetupMinPlayerCommand());

        subCommands.put("snowHight", new SetupSetSnwoHightCommand());

        subCommands.put("winner", new SetupWinnerCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (!commandSender.hasPermission("Snowbattle.Setup")) {
            tc = PluginMain.getMain().getTextConfig();
            commandSender.sendMessage(tc.getMessage("Chat.NoPermissions"));
            return false;
        }
        if (args.length >= 2) {
            SubCommandExecuter subcmd;
            String finalArgs = "";
            String fullCommand = "";
            for (int i = 0; i != args.length; i++) {
                fullCommand = fullCommand + " " + args[i];
            }
            fullCommand = fullCommand.replaceFirst(" ", "");
            if (subCommands.containsKey(args[1])) {
                if (args.length >= 3) {
                    if (subCommands.get(args[1]).getSubCommands().containsKey(args[2])) {
                        subcmd = subCommands.get(args[1]).getSubCommands().get(args[2]);
                        String command = "";
                        if (args.length == 3) {
                            command = args[2];
                            for (int i = 2; i != args.length; i++) {
                                finalArgs = finalArgs + ";" + args[i];
                            }
                        } else {
                            for (int i = 3; i != args.length; i++) {
                                if (subcmd.getSubCommands().containsKey(args[i])) {
                                    subcmd = subcmd.getSubCommands().get(args[i]);
                                } else {
                                    for (int e = i; e != args.length; e++) {
                                        finalArgs = finalArgs + ";" + args[e];
                                    }
                                    command = args[i];
                                    break;
                                }
                            }
                        }
                        subcmd.onCommand(commandSender, cmd, command, finalArgs.replaceFirst(";", "").split(";"), fullCommand);
                    } else {
                        for (int i = 1; i != args.length; i++) {
                            finalArgs = finalArgs + ";" + args[i];
                        }
                        subCommands.get(args[1]).onCommand(commandSender, cmd, args[1], finalArgs.replaceFirst(";", "").split(";"), fullCommand);
                    }
                } else {
                    for (int i = 1; i != args.length; i++) {
                        finalArgs = finalArgs + ";" + args[i];
                    }
                    subCommands.get(args[1]).onCommand(commandSender, cmd, args[1], finalArgs.replaceFirst(";", "").split(";"), fullCommand);
                }
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            PluginMain.getMain().arenas.forEach(arena -> list.add(arena.getArenaName()));
        } else if (args.length == 2) {
            list.addAll(subCommands.keySet());
        } else {
            SubCommandExecuter subcmd;
            if (subCommands.containsKey(args[1])) {
                if (subCommands.get(args[1]).getSubCommands().containsKey(args[2])) {
                    subcmd = subCommands.get(args[1]).getSubCommands().get(args[2]);
                    for (int i = 3; i != args.length; i++) {
                        if (subcmd.getSubCommands().containsKey(args[i])) {
                            subcmd = subcmd.getSubCommands().get(args[i]);
                        } else {
                            break;
                        }
                    }
                    list.addAll(subcmd.getSubCommands().keySet());
                } else {
                    list.addAll(subCommands.get(args[1]).getSubCommands().keySet());
                }
            }
        }
        return list;
    }

}
