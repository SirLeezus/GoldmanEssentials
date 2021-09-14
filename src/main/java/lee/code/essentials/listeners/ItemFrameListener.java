package lee.code.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFrameListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInvisibleFrameInteract(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            Entity entity = e.getEntity();
            if (entity instanceof ItemFrame itemFrame) {
                if (!itemFrame.isVisible()) itemFrame.setVisible(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInvisibleFrameRotate(PlayerInteractEntityEvent e) {
        if (!e.isCancelled()) {
            Player player = e.getPlayer();
            Entity entity = e.getRightClicked();

            if (entity instanceof ItemFrame itemFrame) {
                if (player.isSneaking()) {
                    ItemStack handItem = player.getInventory().getItemInMainHand();
                    if (handItem.getType().equals(Material.SHEARS)) {
                        itemFrame.setVisible(!itemFrame.isVisible());
                        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SNOW_GOLEM_SHEAR, 1, 1);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
