package de.wroracer.snowbattle.listenders;

import de.wroracer.snowbattle.PluginMain;
import de.wroracer.snowbattle.config.TextConfig;
import de.wroracer.snowbattle.game.Arena;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SignEvents implements Listener {

    private boolean isJoinSign = false;
    private Arena arena;
    private TextConfig tc;
    private Arena arena2;

    @EventHandler
    public void onSingCreate(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[SnowBattel]") || event.getLine(0).equalsIgnoreCase("[SB]")) {
            if (event.getPlayer().hasPermission("SnowBattel.PlaceSing")) {
                List<Arena> arenas = PluginMain.getMain().arenas;
                tc = PluginMain.getMain().getTextConfig();

                isJoinSign = false;

                arenas.forEach(arena -> {
                    if (event.getLine(1).contains(arena.getArenaName())) {
                        isJoinSign = true;
                        this.arena = arena;
                    }
                });
                if (event.getBlock().getType() == Material.SIGN || event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN) {
                    if (isJoinSign) {
                        /*event.setLine(0,"§fSnowBattel");*/
                        event.setLine(0, tc.getMessage("JoinSign.Line1"));
                        /*event.setLine(1,arena.getEffectiveName());*/
                        event.setLine(1, tc.getMessage("JoinSign.Line2").replace("{ArenaName}", arena.getEffectiveName()));
                        /*event.setLine(2,"§6Join");*/
                        event.setLine(2, tc.getMessage("JoinSign.Line3"));
                        event.setLine(3, tc.getMessage("JoinSing.Line4"));
                        event.getPlayer().sendMessage("§aDu hast ein Schild für die Arena " + arena.getEffectiveName());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSingClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        tc = PluginMain.getMain().getTextConfig();
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                String line1 = tc.getMessage("JoinSign.Line1");
                if (sign.getLine(0).equals(line1)) {
                    List<Arena> arenas = PluginMain.getMain().arenas;
                    arena2 = null;
                    arenas.forEach(arena -> {
                        if (sign.getLine(1).equals(arena.getEffectiveName())) {
                            this.arena2 = arena;
                        }
                    });
                    if (arena2 != null) {
                        player.performCommand("sb-join " + arena2.getArenaName());
                    }
                }
            }
        }
    }

}
