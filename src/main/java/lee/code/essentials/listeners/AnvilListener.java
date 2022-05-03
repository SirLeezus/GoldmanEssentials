package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvilRename(PrepareAnvilEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        ItemStack[] contents = e.getInventory().getContents();
        ItemStack firstSlot = contents[0];
        ItemStack secondSlot = contents[1];

        if (firstSlot != null && secondSlot == null) {
            ItemStack dupe = firstSlot.clone();
            ItemMeta dupeMeta = dupe.getItemMeta();
            ItemStack resultStack = e.getResult();
            if (resultStack != null) {
                dupeMeta.displayName(pu.formatC(pu.unFormatC(resultStack.getItemMeta().displayName())));
                dupe.setItemMeta(dupeMeta);
                e.setResult(dupe);
            }
        }
    }
}
