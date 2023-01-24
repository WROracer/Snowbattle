package de.wroracer.snowbattle.commands.subCommands.SetupCommand.present;


import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupPresentAddCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        Arena arena = PluginMain.getMain().getArena(arenaName);
        if (arena != null) {
            arena.addPresetnLocation(Bukkit.getPlayer(commandSender.getName()).getLocation());
            commandSender.sendMessage("Present Position für " + arenaName + " wurde auf deine Position gesetzt");
            PluginMain.getMain().saveArena(arena);
        } else {
            TextConfig tc = PluginMain.getMain().getTextConfig();
            commandSender.sendMessage(tc.getMessage("Chat.ArenaIsntExisting").replace("{ArenaName}", arenaName));
        }
        return false;
    }

    @Override
    public HashMap<String, SubCommandExecuter> getSubCommands() {
        return new HashMap<String, SubCommandExecuter>();
    }
}
