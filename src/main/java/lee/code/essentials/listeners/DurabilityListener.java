package lee.code.essentials.listeners;

import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DurabilityListener implements Listener {

    @EventHandler
    public void onLowDurabilityBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.hasItemMeta()) {
            ItemMeta handItemMeta = handItem.getItemMeta();
            if (handItemMeta instanceof Damageable damageable) {
                int maxD = handItem.getType().getMaxDurability() - 1;
                int currentD = maxD - damageable.getDamage();
                if (currentD < 10 && currentD > 0) player.sendActionBar(Lang.DURABILITY.getComponent(new String[] { String.valueOf(currentD), String.valueOf(maxD) }));
            }
        }
    }

    @EventHandler
    public void onLowDurabilityEntityInteract(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.hasItemMeta()) {
            ItemMeta handItemMeta = handItem.getItemMeta();
            if (handItemMeta instanceof Damageable damageable) {
                int maxD = handItem.getType().getMaxDurability() - 1;
                int currentD = maxD - damageable.getDamage();
                if (currentD < 10 && currentD > 0) player.sendActionBar(Lang.DURABILITY.getComponent(new String[] { String.valueOf(currentD), String.valueOf(maxD) }));
            }
        }
    }
}
