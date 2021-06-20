package lee.code.essentials.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class HatListener implements Listener {

    @EventHandler
    public void onHelmetSlotClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            InventoryType type = e.getInventory().getType();
            if (type == InventoryType.CRAFTING && player.getGameMode() != GameMode.CREATIVE) {
                if (e.getSlot() == 39) {
                    ItemStack item = player.getItemOnCursor();
                    e.setCancelled(true);
                    ItemStack helmet = player.getInventory().getHelmet();
                    player.setItemOnCursor(Objects.requireNonNullElseGet(helmet, () -> new ItemStack(Material.AIR)));
                    player.getInventory().setHelmet(item);
                }
            }
        }
    }
}
