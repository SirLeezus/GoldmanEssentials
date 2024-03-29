package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
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
                Component name = BukkitUtils.parseColorComponentLiteral(BukkitUtils.serializeComponent(resultStack.getItemMeta().displayName()));
                dupeMeta.displayName(GoldmanEssentials.getPlugin().getPU().parseVariables(name));
                dupe.setItemMeta(dupeMeta);
                e.setResult(dupe);
            }
        }
    }
}
