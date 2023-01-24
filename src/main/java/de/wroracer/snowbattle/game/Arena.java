package de.wroracer.snowbattle.game;


import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arena {
    private final World world;
    private final List<Location> spawns;
    private final List<Location> presentSpawn;
    private final String arenaName;
    private Location pos1;
    private Location pos2;
    private double snowHigth;
    private Location firstPlace;
    private Location secondPlace;
    private Location thirdPlace;
    private Location otherWinners;
    private Location presentGoal;
    private Location lobby;
    private int minPlayerCount;
    private int maxPlayerCount;
    private String effectiveName;

    public Arena(World world, String arenaName, int minPlayerCount) {
        this.world = world;
        this.arenaName = arenaName;
        this.minPlayerCount = minPlayerCount;
        maxPlayerCount = 0;
        this.effectiveName = arenaName + " ";

        spawns = new ArrayList<>();
        presentSpawn = new ArrayList<>();
    }

    public Location getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(Location firstPlace) {
        this.firstPlace = firstPlace;
    }

    public Location getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(Location secondPlace) {
        this.secondPlace = secondPlace;
    }

    public Location getThirdPlace() {
        return thirdPlace;
    }

    public void setThirdPlace(Location thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    public Location getOtherWinners() {
        return otherWinners;
    }

    public void setOtherWinners(Location otherWinners) {
        this.otherWinners = otherWinners;
    }

    public Location getPresentGoal() {
        return presentGoal;
    }

    public void setPresentGoal(Location presentGoal) {
        this.presentGoal = presentGoal;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<Location> getPresentSpawn() {
        return presentSpawn;
    }

    public String getEffectiveName() {
        return effectiveName;
    }

    public void setEffectiveName(String effectiveName) {
        this.effectiveName = effectiveName;
    }

    public void addSpawnLocation(Location... spawn) {
        spawns.addAll(Arrays.asList(spawn));
        maxPlayerCount = spawns.size();
    }

    public void deleteAllSpawnLocation() {
        spawns.clear();
        maxPlayerCount = 0;
    }

    public void addPresetnLocation(Location... spawn) {
        presentSpawn.addAll(Arrays.asList(spawn));
    }

    public void deletePresetnLocation() {
        presentSpawn.clear();
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public double getSnowHigth() {
        return snowHigth;
    }

    public void setSnowHigth(double snowHigth) {
        this.snowHigth = snowHigth;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public String getArenaName() {
        return arenaName;
    }


    public World getWorld() {
        return world;
    }


    public int getMinPlayerCount() {
        return minPlayerCount;
    }

    public void setMinPlayerCount(int minPlayerCount) {
        this.minPlayerCount = minPlayerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }
}
