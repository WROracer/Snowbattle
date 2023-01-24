package de.wroracer.snowbattle;

import de.wroracer.snowbattle.commands.JoinGameCommand;
import de.wroracer.snowbattle.commands.SetupCommand;
import de.wroracer.snowbattle.commands.StartCommand;
import de.wroracer.snowbattle.commands.StatCommand;
import de.wroracer.snowbattle.commands.subCommands.LeaveGameCommand;
import de.wroracer.snowbattle.config.ArenaConfig;
import de.wroracer.snowbattle.config.MainConfig;
import de.wroracer.snowbattle.config.StatConfig;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import de.wroracer.snowbattle.game.GameManager;
import de.wroracer.snowbattle.listenders.PresentDropListender;
import de.wroracer.snowbattle.listenders.SignEvents;
import de.wroracer.snowbattle.listenders.SnowBallHitListender;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PluginMain extends JavaPlugin {

    private static PluginMain PLUGIN;
    public GameManager gameManager;
    public List<Arena> arenas;
    public Arena retArena;
    private MainConfig mainConfig;
    private ArenaConfig arenaConfig;
    private TextConfig textConfig;
    private StatConfig statConfig;

    public static PluginMain getMain() {
        return PLUGIN;
    }

    public void addArena(Arena arena) {
        arenaConfig.saveArena(arena);
        arenas.add(arena);
    }

    public Arena getArena(String name) {
        arenas.forEach(arena -> {
            if (arena.getArenaName().equalsIgnoreCase(name)) {
                retArena = arena;
            }
        });
        Arena arena = retArena;
        retArena = null;
        return arena;
    }

    public boolean removeArena(Arena arena) {
        if (arenas.contains(arena)) {
            arenaConfig.deleteArena(arena);
            arenas.remove(arena);
            return true;
        }
        return false;
    }

    public void saveArena(Arena arena) {
        arenaConfig.saveArena(arena);
    }

    @Override
    public void onDisable() {
        gameManager.stopAllGames();
    }

    @Override
    public void onEnable() {
        PLUGIN = this;
        System.out.println("Snowbattle plugin by WRORacer");

        loadConfigs();
        System.out.println("Configs Loaded");

        initCommands();
        System.out.println("Comands Loaded");

        initListenders();
        System.out.println("Listenders Loaded");

        arenas = arenaConfig.getAllAreanas();
        gameManager = new GameManager();

    }

    private void loadConfigs() {
        mainConfig = new MainConfig();
        arenaConfig = new ArenaConfig();
        textConfig = new TextConfig();
        statConfig = new StatConfig();
    }

    private void initCommands() {
        getCommand("sb-setup").setExecutor(new SetupCommand());
        getCommand("sb-setup").setTabCompleter(new SetupCommand());

        getCommand("sb-join").setExecutor(new JoinGameCommand());
        getCommand("sb-join").setTabCompleter(new JoinGameCommand());

        getCommand("leave").setExecutor(new LeaveGameCommand());
        getCommand("l").setExecutor(new LeaveGameCommand());

        getCommand("sb-stats").setExecutor(new StatCommand());

        getCommand("sb-start").setExecutor(new StartCommand());
    }

    private void initListenders() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PresentDropListender(), this);
        pluginManager.registerEvents(new SnowBallHitListender(), this);
        pluginManager.registerEvents(new SignEvents(), this);
    }

    public StatConfig getStatConfig() {
        return statConfig;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public TextConfig getTextConfig() {
        return textConfig;
    }

}
