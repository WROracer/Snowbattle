package de.wroracer.snowbattle.game;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.MainConfig;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.util.Present;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class PresentSpawner {

    public final String S_PRESENT_1 = "INCr3MentJ";
    public final String S_PRESENT_2 = "CruXXx";
    public final String S_PRESENT_3 = "thresh3";
    public final String S_PRESENT_4 = "Hannah4848";
    public final String S_PRESENT_5 = "SeerPotion";
    public final String S_PRESENT_6 = "RiksRealm";
    private final Arena arena;
    private final List<String> headNames;
    private final HashMap<String, String> playerHeadNames;
    private final TextConfig tc;
    private ItemStack present;
    private int valuePresent1 = 1;
    private int valuePresent2 = 3;
    private int valuePresent3 = 5;
    private int valuePresent4 = 10;
    private int valuePresent5 = 15;
    private int valuePresent6 = 20;
    private int itemsThere;

    public PresentSpawner(Arena arena) {
        this.arena = arena;
        headNames = new ArrayList<>();

        MainConfig mc = PluginMain.getMain().getMainConfig();
        tc = PluginMain.getMain().getTextConfig();
        Present present1 = mc.getPresent(1);
        Present present2 = mc.getPresent(2);
        Present present3 = mc.getPresent(3);
        Present present4 = mc.getPresent(4);
        Present present5 = mc.getPresent(5);
        Present present6 = mc.getPresent(6);

        playerHeadNames = new HashMap<>();

        playerHeadNames.put(S_PRESENT_1, present1.getPlayerName());
        playerHeadNames.put(S_PRESENT_2, present2.getPlayerName());
        playerHeadNames.put(S_PRESENT_3, present3.getPlayerName());
        playerHeadNames.put(S_PRESENT_4, present4.getPlayerName());
        playerHeadNames.put(S_PRESENT_5, present5.getPlayerName());
        playerHeadNames.put(S_PRESENT_6, present6.getPlayerName());


        valuePresent1 = present1.getValue();
        valuePresent2 = present2.getValue();
        valuePresent3 = present3.getValue();
        valuePresent4 = present4.getValue();
        valuePresent5 = present5.getValue();
        valuePresent6 = present6.getValue();


        int chancesPresent1 = present1.getChances();
        int chancesPresent2 = present2.getChances();
        int chancesPresent3 = present3.getChances();
        int chancesPresent4 = present4.getChances();
        int chancesPresent5 = present5.getChances();
        int chancesPresent6 = present6.getChances();


        for (int i = 0; i != chancesPresent1; i++) {
            headNames.add(S_PRESENT_1);
        }
        for (int i = 0; i != chancesPresent2; i++) {
            headNames.add(S_PRESENT_2);
        }
        for (int i = 0; i != chancesPresent3; i++) {
            headNames.add(S_PRESENT_3);
        }
        for (int i = 0; i != chancesPresent4; i++) {
            headNames.add(S_PRESENT_4);
        }
        for (int i = 0; i != chancesPresent5; i++) {
            headNames.add(S_PRESENT_5);
        }
        for (int i = 0; i != chancesPresent6; i++) {
            headNames.add(S_PRESENT_6);
        }
    }

    public Item spawnPresent() {
        List<Location> spawns = arena.getPresentSpawn();
        Random random = new Random();
        int spawnBound = spawns.size();
        int presetntBound = headNames.size();
        int intSpawn = random.nextInt(spawnBound);
        int intPresent = random.nextInt(presetntBound);
        Location spawn = spawns.get(intSpawn);
        present = getHead(headNames.get(intPresent));
        final Item[] item = new Item[1];
        /*Bukkit.getScheduler().runTask(PluginMain.getPlugin(PluginMain.class), new Runnable() {
            @Override
            public void run() {
                item[0] =  arena.getWorld().dropItem(spawn,present);
            }
        });*/
        itemsThere = 0;
        Collection<org.bukkit.entity.Entity> entities = arena.getWorld().getNearbyEntities(spawn, 2, 2, 2);
        entities.forEach(entity -> {
            if (entity instanceof Item) {
                if (((Item) entity).getItemStack().getItemMeta().getLore().contains(tc.getMessage("Present.Name"))) {
                    itemsThere = itemsThere + ((Item) entity).getItemStack().getAmount();
                }

            }
        });
        if (itemsThere <= 5)
            return arena.getWorld().dropItem(spawn, present);
        return null;
    }

    public ItemStack getHead(String player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(tc.getMessage("Present.Name"));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(tc.getMessage("Present.Lore1"));
        switch (player) {
            case S_PRESENT_1:
                addLore(valuePresent1, lore);
                break;
            case S_PRESENT_2:
                addLore(valuePresent2, lore);
                break;
            case S_PRESENT_3:
                addLore(valuePresent3, lore);
                break;
            case S_PRESENT_4:
                addLore(valuePresent4, lore);
                break;
            case S_PRESENT_5:
                addLore(valuePresent5, lore);
                break;
            case S_PRESENT_6:
                addLore(valuePresent6, lore);
                break;
        }
        skull.setLore(lore);
        skull.setOwner(playerHeadNames.get(player));
        item.setItemMeta(skull);
        return item;
    }

    private void addLore(int value, ArrayList<String> lore) {
        String einZahl = tc.getMessage("Present.Lore2").replace("[", "SPLIT").split("SPLIT")[0] + tc.getMessage("Present.Lore2").replace("[", "SPLIT").replace("]", "SPLIT").split("SPLIT")[1].split("/")[0] + tc.getMessage("Present.Lore2").replace("[", "SPLIT").replace("]", "SPLIT").split("SPLIT")[2];
        String mehrZahl = tc.getMessage("Present.Lore2").replace("[", "SPLIT").split("SPLIT")[0] + tc.getMessage("Present.Lore2").replace("[", "SPLIT").replace("]", "SPLIT").split("SPLIT")[1].split("/")[1] + tc.getMessage("Present.Lore2").replace("[", "SPLIT").replace("]", "SPLIT").split("SPLIT")[2];
        if (value == 1) {
            lore.add(einZahl.replace("{Value}", "" + value));
        } else {
            lore.add(mehrZahl.replace("{Value}", "" + value));
        }
    }


}
