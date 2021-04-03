package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class DamageListener implements Listener {

    @EventHandler
    public void onGodModeEntityDamageByEntity(EntityDamageByEntityEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            UUID uuid = player.getUniqueId();
            if (cache.isGodMode(uuid)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGodModeEntityDamage(EntityDamageEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            UUID uuid = player.getUniqueId();
            if (cache.isGodMode(uuid)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGodModeEntityDamageByBlock(EntityDamageByBlockEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            UUID uuid = player.getUniqueId();
            if (cache.isGodMode(uuid)) e.setCancelled(true);
        }
    }
}
