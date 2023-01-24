package de.wroracer.snowbattle.commands.subCommands.SetupCommand;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.commands.subCommands.SubCommandExecuter;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SetupDeleteCommand implements SubCommandExecuter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args, String command) {
        String arenaName = command.split(" ")[0];
        Arena arena = PluginMain.getMain().getArena(arenaName);
        if (arena != null) {
            PluginMain.getMain().removeArena(arena);
            commandSender.sendMessage("Die Arena " + arenaName + " wurde Gelöscht");
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
