package de.wroracer.snowbattle.listenders;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.MainConfig;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Game;
import de.wroracer.snowbattle.util.Present;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PresentDropListender implements Listener {

    private int value = 0;
    private TextConfig tc;
    private MainConfig mc;


    @EventHandler
    public void onPresentDrop(PlayerDropItemEvent event) {
        Game game = PluginMain.getMain().gameManager.getGameOfPlayer(event.getPlayer());
        tc = PluginMain.getMain().getTextConfig();
        mc = PluginMain.getMain().getMainConfig();
        if (game != null) {
            Item item = event.getItemDrop();
            ItemStack itemStack = item.getItemStack();
            List<String> lore = itemStack.getItemMeta().getLore();

            if (lore.contains(tc.getMessage("Present.Lore1"))) {
                double distance = event.getPlayer().getLocation().distance(game.getArena().getPresentGoal());
                if (distance <= 3.0) {
                    int amount = itemStack.getAmount();
                    value = 0;
                    Material material = item.getItemStack().getType();
                    if (material == Material.SKULL_ITEM) {
                        SkullMeta meta = (SkullMeta) item.getItemStack().getItemMeta();
                        String skullOwner = meta.getOwner();
                        for (int i = 1; i != 6; i++) {
                            Present present = mc.getPresent(i);
                            if (present.getPlayerName().equals(skullOwner)) {
                                value = present.getValue();
                                break;
                            }
                        }
                    }
                    game.addPresentToPlayer(event.getPlayer(), (amount * value));
                    item.remove();
                }
            }

        }
    }
}
