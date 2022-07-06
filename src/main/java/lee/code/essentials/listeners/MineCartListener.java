package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MineCartListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onMineCartArmorStand(PlayerInteractAtEntityEvent e) {
        if (!e.isCancelled()) {
            Player player = e.getPlayer();
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.getType().equals(Material.ARMOR_STAND)) {
                if (e.getRightClicked() instanceof Minecart minecart) {
                    e.setCancelled(true);
                    if (BukkitUtils.hasClickDelay(player)) return;

                    if (minecart.getPassengers().isEmpty() && minecart.getType().equals(EntityType.MINECART)) {
                        Location location = minecart.getLocation();
                        Entity armorStand = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                        minecart.addPassenger(armorStand);
                        int handAmount = handItem.getAmount();
                        if (handAmount > 0) handItem.setAmount(handAmount - 1);
                        else handItem.setType(Material.AIR);
                        player.getInventory().setItemInMainHand(handItem);
                    }
                }
            }
        }
    }
}
