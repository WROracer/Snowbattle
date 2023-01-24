package de.wroracer.snowbattle.config;

import de.wroracer.snowbattle.Constands;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TextConfig {

    private final File file;
    private final File folder;
    private final YamlConfiguration cfg;

    private final String fileName = "messages.yml";
    private final String filePath = Constands.CONFIG_FOLDER;

    public TextConfig() {
        folder = new File(filePath);
        file = new File(filePath, fileName);
        cfg = YamlConfiguration.loadConfiguration(file);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                cfg.set("JoinSign.Line1", "&fSnowBattel");
                cfg.set("JoinSign.Line2", "{ArenaName}");
                cfg.set("JoinSign.Line3", "&6Join");
                cfg.set("JoinSign.Line4", "");

                cfg.set("Present.Name", "&aEin Schönes Geschenk");
                cfg.set("Present.Lore1", "&6Geschenk von SnowBattle");
                cfg.set("Present.Lore2", "&aWert: &6{Value} &a[Punkt/Punkte].");

                cfg.set("SnowBall.Name", "&fSchnee Ball");
                cfg.set("SnowBall.Lore", "&fWerfe hiermit diene Gegner ab");

                cfg.set("Chat.YouAreInNoRound", "&4Du bist in keiner Runde");
                cfg.set("Chat.ArenaIsntExisting", "&4Die Arena {ArenaName} Existiert nicht");
                cfg.set("Chat.ArenaIsInGame", "&4Die Arena {ArenaName}, ist Momentan in einer Runde");
                cfg.set("Chat.YouAreInARound", "&4Du befindest dich bereits in einer Runde.\nNutze /leave um die Runde zu verlassen");

                cfg.set("Chat.NoPermissions", "&4Dazu hast du keine Rechte");

                cfg.set("Chat.YourPresents", "&aGeschenk Anzahl: {Presents}");
                cfg.set("Chat.RoundLeave.Player", "&4Du hast die Runde Verlassen");
                cfg.set("Chat.RoundLeave.Other", "&4{PlayerName} hat die Runde Verlassen");

                cfg.set("Chat.RoundJoin.Player", "&aDu bist der Arena {ArenaName} &aBeigetreten");
                cfg.set("Chat.RoundJoin.Other", "&a{PlayerName} ist der Runde Beigetreten");

                cfg.set("Chat.Winner.FirstPlace", "&b1. {PlayerName} (&2{Points}&b)\n");
                cfg.set("Chat.Winner.SecondPlace", "&62. {PlayerName} (&2{Points}&6)\n");
                cfg.set("Chat.Winner.ThirdPlace", "&73. {PlayerName} (&2{Points}&7)\n");

                cfg.set("Chat.Stats", "&6Deine &fSnowBattel &6Stats:\n&aWins: {Wins}\n&4Losses: {Losses}\n&6Höchste Punktzah: {HighestPoints}");

                cfg.set("AktionBar.Timer", "&fZeit: MM:SS");

                cfg.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    public String getMessage(String path) {
        return cfg.getString(path).replace("&", "§");
    }

}
