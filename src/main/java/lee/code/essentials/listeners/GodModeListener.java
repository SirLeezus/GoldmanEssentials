package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class GodModeListener implements Listener {

    @EventHandler
    public void onGodModeEntityDamageByEntity(EntityDamageByEntityEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (e.getEntity() instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (cacheManager.isGodPlayer(uuid)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGodModeEntityDamage(EntityDamageEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (e.getEntity() instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (cacheManager.isGodPlayer(uuid)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGodModeEntityDamageByBlock(EntityDamageByBlockEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (e.getEntity() instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (cacheManager.isGodPlayer(uuid)) e.setCancelled(true);
        }
    }
}
