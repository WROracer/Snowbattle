package de.wroracer.snowbattle.listenders;


import de.wroracer.snowbattle.PluginMain;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SnowBallHitListender implements Listener {
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball) {
            if (event.getEntity() instanceof Player) {
                if (PluginMain.getMain().gameManager.isInRound((Player) event.getEntity())) {
                    if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
                        if (PluginMain.getMain().gameManager.isInRound((Player) ((Projectile) event.getDamager()).getShooter())) {
                            PluginMain.getMain().gameManager.respawnPlayer((Player) event.getEntity());
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
