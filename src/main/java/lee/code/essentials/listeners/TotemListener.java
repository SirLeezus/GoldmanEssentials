package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class TotemListener implements Listener {

    @EventHandler
    public void onTotemVoidDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                if (player.getInventory().contains(new ItemStack(Material.TOTEM_OF_UNDYING))) {
                    if ((player.getHealth() - e.getFinalDamage()) <= 0) {
                        e.setCancelled(true);
                        player.teleport(GoldmanEssentials.getPlugin().getCache().getSpawn());
                    }
                }
            }
        }
    }
}
