package de.wroracer.snowbattle.config;

import de.wroracer.snowbattle.Constands;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaConfig {

    private final File file;
    private final File folder;
    private final YamlConfiguration cfg;

    private final String fileName = "arena.yml";
    private final String filePath = Constands.CONFIG_FOLDER;

    public ArenaConfig() {
        folder = new File(filePath);
        file = new File(filePath, fileName);
        cfg = YamlConfiguration.loadConfiguration(file);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                cfg.set("Arena.All", "");
                cfg.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    public boolean deleteArena(Arena arena) {
        cfg.set(arena.getArenaName(), null);
        if (cfg.get("Arena.All") != null && cfg.getString("Arena.All").contains(arena.getArenaName()))
            cfg.set("Arena.All", cfg.getString("Arena.All").replace(arena.getArenaName() + ",", ""));
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveArena(Arena arena) {
        String name = arena.getArenaName();

        if (cfg.getString("Arena.All") == null) {
            cfg.set("Arena.All", cfg.get("Arena.All") + arena.getArenaName() + ",");
        } else if (!cfg.getString("Arena.All").contains(arena.getArenaName()))
            cfg.set("Arena.All", cfg.get("Arena.All") + arena.getArenaName() + ",");

        setLocation(name + ".Pos1", arena.getPos1());
        setLocation(name + ".Pos2", arena.getPos2());
        setLocation(name + ".Lobby", arena.getLobby());

        cfg.set(name + ".World", arena.getWorld().getName());

        cfg.set(name + ".Name", arena.getEffectiveName());

        cfg.set(name + ".SnowHight", arena.getSnowHigth());

        cfg.set(name + ".MinPlayers", arena.getMinPlayerCount());
        cfg.set(name + ".MaxPlayers", arena.getMaxPlayerCount());

        setLocation(name + ".PresentGoal", arena.getPresentGoal());

        setLocation(name + ".Winner.Place.First", arena.getFirstPlace());
        setLocation(name + ".Winner.Place.Second", arena.getSecondPlace());
        setLocation(name + ".Winner.Place.Third", arena.getThirdPlace());
        setLocation(name + ".Winner.Place.Other", arena.getOtherWinners());

        List<Location> spawns = arena.getSpawns();
        cfg.set(name + ".Spawns.Amount", spawns.size());
        for (int i = 0; i != spawns.size(); i++) {
            int e = i;
            e++;
            setLocation(name + ".Spawns." + e, spawns.get(i));
        }

        List<Location> presents = arena.getPresentSpawn();
        cfg.set(name + ".Presents.Amount", presents.size());
        for (int i = 0; i != presents.size(); i++) {
            int e = i;
            e++;
            setLocation(name + ".Presents." + e, presents.get(i));
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void setLocation(String path, Location location) {
        if (location != null) {
            cfg.set(path + ".World", location.getWorld().getName());
            cfg.set(path + ".X", location.getX());
            cfg.set(path + ".Y", location.getY());
            cfg.set(path + ".Z", location.getZ());
            cfg.set(path + ".Pitch", location.getPitch());
            cfg.set(path + ".Yaw", location.getYaw());
        } else {
            cfg.set(path + ".World", "NO CONFIG");
            cfg.set(path + ".X", "NO CONFIG");
            cfg.set(path + ".Y", "NO CONFIG");
            cfg.set(path + ".Z", "NO CONFIG");
            cfg.set(path + ".Pitch", "NO CONFIG");
            cfg.set(path + ".Yaw", "NO CONFIG");
        }
    }

    public List<Arena> getAllAreanas() {
        if (cfg.getString("Arena.All") == null) {
            return new ArrayList<Arena>();
        }
        String[] arenas = cfg.getString("Arena.All").split(",");
        List<Arena> arenas1 = new ArrayList<>();
        for (int i = 0; i != arenas.length; i++) {
            Arena arena = loadArena(arenas[i]);
            if (arena != null) {
                arenas1.add(arena);
            }
        }
        return arenas1;
    }

    public Arena loadArena(String name) {
        try {
            World world = Bukkit.getWorld(cfg.getString(name + ".World"));
            int minPlayers = cfg.getInt(name + ".MinPlayers");
            Arena arena = new Arena(world, name, minPlayers);
            arena.setEffectiveName(cfg.getString(name + ".Name"));
            arena.setPos1(getLocation(name + ".Pos1"));
            arena.setPos2(getLocation(name + ".Pos2"));
            arena.setLobby(getLocation(name + ".Lobby"));
            arena.setMaxPlayerCount(cfg.getInt(name + ".MaxPlayers"));
            arena.setSnowHigth(cfg.getDouble(name + ".SnowHight"));
            int spawns = cfg.getInt(name + ".Spawns.Amount");
            for (int i = 0; i != spawns; i++) {
                int e = i;
                e++;
                arena.addSpawnLocation(getLocation(name + ".Spawns." + e));
            }

            arena.setThirdPlace(getLocation(name + ".Winner.Place.Third"));
            arena.setSecondPlace(getLocation(name + ".Winner.Place.Second"));
            arena.setOtherWinners(getLocation(name + ".Winner.Place.Other"));
            arena.setFirstPlace(getLocation(name + ".Winner.Place.First"));

            int presents = cfg.getInt(name + ".Presents.Amount");
            for (int i = 0; i != presents; i++) {
                int e = i;
                e++;
                arena.addPresetnLocation(getLocation(name + ".Presents." + e));
            }
            arena.setPresentGoal(getLocation(name + ".PresentGoal"));
            return arena;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Location getLocation(String path) {
        if (!cfg.getString(path + ".World").equalsIgnoreCase("NO CONFIG")) {
            World world = Bukkit.getWorld(cfg.getString(path + ".World"));
            double x = cfg.getDouble(path + ".X");
            double y = cfg.getDouble(path + ".Y");
            double z = cfg.getDouble(path + ".Z");

            double yaw = (double) cfg.get(path + ".Yaw");
            double pitch = (double) cfg.get(path + ".Pitch");
            return new Location(world, x, y, z, (float) yaw, (float) pitch);
        }
        return null;
    }

}
