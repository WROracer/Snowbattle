package de.wroracer.snowbattle.commands.subCommands.SetupCommand;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupMinPlayerCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        Arena arena = PluginMain.getMain().getArena(arenaName);
        if (args.length >= 2) {

        }
        if (arena != null) {
            if (args.length == 2) {
                int playercount;
                try {
                    playercount = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    commandSender.sendMessage("Benutzung: /setup " + arenaName + " minPlayer {Count}");
                    return false;
                }
                arena.setMinPlayerCount(playercount);
                PluginMain.getMain().saveArena(arena);
                commandSender.sendMessage("Der Minimalen Spieler von " + arenaName + " wurde auf " + playercount + " gesetzt");
            } else {
                commandSender.sendMessage("Benutzung: /setup " + arenaName + " minPlayer {Count}");
            }
        } else {
            TextConfig tc = PluginMain.getMain().getTextConfig();
            commandSender.sendMessage(tc.getMessage("Chat.ArenaIsntExisting").replace("{ArenaName}", arenaName));
        }
        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        return new HashMap<>();
    }
}
