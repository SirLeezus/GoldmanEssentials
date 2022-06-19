package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvilRename(PrepareAnvilEvent e) {
        ItemStack[] contents = e.getInventory().getContents();
        ItemStack firstSlot = contents[0];
        ItemStack secondSlot = contents[1];

        if (firstSlot != null && secondSlot == null) {
            ItemStack dupe = firstSlot.clone();
            ItemMeta dupeMeta = dupe.getItemMeta();
            ItemStack resultStack = e.getResult();
            if (resultStack != null) {
                dupeMeta.displayName(BukkitUtils.parseColorComponentLiteral(BukkitUtils.serializeComponent(resultStack.getItemMeta().displayName())));
                dupe.setItemMeta(dupeMeta);
                e.setResult(dupe);
            }
        }
    }
}
