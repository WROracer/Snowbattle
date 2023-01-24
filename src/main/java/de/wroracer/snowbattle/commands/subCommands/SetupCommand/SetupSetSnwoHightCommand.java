package de.wroracer.snowbattle.commands.subCommands.SetupCommand;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupSetSnwoHightCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        Arena arena = PluginMain.getMain().getArena(arenaName);
        if (arena != null) {
            arena.setSnowHigth(Bukkit.getPlayer(commandSender.getName()).getLocation().getBlockY());
            commandSender.sendMessage("Die SnowBall spawn Höhe für " + arenaName + " wurde auf " + arena.getSnowHigth() + " gestetzt");
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
