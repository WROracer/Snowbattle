package de.wroracer.snowbattle.config;

import de.wroracer.snowbattle.Constands;
import de.wroracer.snowbattle.util.Present;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MainConfig {

    private final File file;
    private final File folder;
    private final YamlConfiguration cfg;

    private final String fileName = "config.yml";
    private final String filePath = Constands.CONFIG_FOLDER;

    public MainConfig() {
        folder = new File(filePath);
        file = new File(filePath, fileName);
        cfg = YamlConfiguration.loadConfiguration(file);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                cfg.set("Items.Present.1.PlayerName", "INCr3MentJ");
                cfg.set("Items.Present.2.PlayerName", "CruXXx");
                cfg.set("Items.Present.3.PlayerName", "thresh3");
                cfg.set("Items.Present.4.PlayerName", "Hannah4848");
                cfg.set("Items.Present.5.PlayerName", "SeerPotion");
                cfg.set("Items.Present.6.PlayerName", "RiksRealm");

                cfg.set("Items.Present.1.Value", 1);
                cfg.set("Items.Present.2.Value", 3);
                cfg.set("Items.Present.3.Value", 5);
                cfg.set("Items.Present.4.Value", 10);
                cfg.set("Items.Present.5.Value", 15);
                cfg.set("Items.Present.6.Value", 20);

                cfg.set("Items.Present.1.Chances", 5);
                cfg.set("Items.Present.2.Chances", 5);
                cfg.set("Items.Present.3.Chances", 4);
                cfg.set("Items.Present.4.Chances", 3);
                cfg.set("Items.Present.5.Chances", 2);
                cfg.set("Items.Present.6.Chances", 1);

                cfg.set("Game.Time.Start", 30);
                cfg.set("Game.Time.Game", 300);
                cfg.set("Game.Time.End", 20);

                cfg.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    public Present getPresent(int nbr) {
        String playerName = cfg.getString("Items.Present." + nbr + ".PlayerName");
        int value = cfg.getInt("Items.Present." + nbr + ".Value");
        int chances = cfg.getInt("Items.Present." + nbr + ".Chances");
        return new Present(playerName, value, chances);
    }

    public int getStartTime() {
        return cfg.getInt("Game.Time.Start");
    }

    public int getGameTime() {
        return cfg.getInt("Game.Time.Game");
    }

    public int getEndTime() {
        return cfg.getInt("Game.Time.End");
    }

}
