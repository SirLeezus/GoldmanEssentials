package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    @EventHandler @SuppressWarnings("deprecation")
    public void onAnvilRename(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        if (e.getInventory().getType() == InventoryType.ANVIL && e.getWhoClicked() instanceof Player) {
            if (e.getCurrentItem() != null) {
                ItemStack item = new ItemStack(e.getCurrentItem());
                ItemMeta itemMeta = item.getItemMeta();
                if (e.getRawSlot() == 2) {
                    if (item.getItemMeta().hasDisplayName()) {
                        String name = itemMeta.getDisplayName();
                        itemMeta.setDisplayName(plugin.getPU().format(name));
                        item.setItemMeta(itemMeta);
                        e.setCurrentItem(item);
                    }
                }
            }
        }
    }
}
