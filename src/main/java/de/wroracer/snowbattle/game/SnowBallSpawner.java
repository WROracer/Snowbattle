package de.wroracer.snowbattle.game;


import com.google.common.base.Preconditions;
import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class SnowBallSpawner {
    private final Arena arena;

    private TextConfig tc;

    public SnowBallSpawner(Arena arena) {
        this.arena = arena;
    }

    public Item spawnSnowBall() {
        Location pos1 = arena.getPos1();
        Location pos2 = arena.getPos2();
        pos1.setY(arena.getSnowHigth());
        pos2.setY(arena.getSnowHigth());
        tc = PluginMain.getMain().getTextConfig();
        Location spawnLoc = getRandomLocation(pos1, pos2);

        ItemStack snowBall = new ItemBuilder(Material.SNOW_BALL).setAmount(1).setName(tc.getMessage("SnowBall.Name")).setLore(tc.getMessage("SnowBall.Lore")).buid();
        final Item[] item = new Item[1];
        /*Bukkit.getScheduler().runTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
               item[0] =  arena.getWorld().dropItem(spawnLoc,snowBall);
            }
        });*/
        return arena.getWorld().dropItem(spawnLoc, snowBall);
    }

    public Location getRandomLocation(Location loc1, Location loc2) {
        Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    public double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }


}
