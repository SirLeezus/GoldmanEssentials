package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class MoveListener implements Listener {

    @EventHandler
    public void onBotCheckerMove(PlayerMoveEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        UUID uuid = e.getPlayer().getUniqueId();
        if (!cache.hasBeenBotChecked(uuid)) e.setCancelled(true);
    }
}
