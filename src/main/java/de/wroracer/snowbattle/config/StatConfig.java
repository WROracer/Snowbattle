package de.wroracer.snowbattle.config;


import de.wroracer.snowbattle.Constands;
import de.wroracer.snowbattle.util.GameStats;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class StatConfig {

    private final File file;
    private final File folder;
    private final YamlConfiguration cfg;

    private final String fileName = "stats.yml";
    private final String filePath = Constands.CONFIG_FOLDER;

    public StatConfig() {
        folder = new File(filePath);
        file = new File(filePath, fileName);
        cfg = YamlConfiguration.loadConfiguration(file);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                cfg.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    public void saveStat(GameStats stats) {
        String uuid = stats.getPlayer().getUniqueId().toString();
        cfg.set(uuid + ".Wins", stats.getWins());
        cfg.set(uuid + ".Losses", stats.getLosses());
        cfg.set(uuid + ".HighestPoints", stats.getHighestPoints());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameStats loadStat(Player player) {
        String uuid = player.getUniqueId().toString();
        if (cfg.contains(uuid + ".Wins"))
            return new GameStats(player, cfg.getInt(uuid + ".Wins"), cfg.getInt(uuid + ".Losses"), cfg.getInt(uuid + ".HighestPoints"));
        return new GameStats(player, 0, 0, 0);
    }

}
