package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HeadDropListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHeadDrop(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Entity entity = e.getEntity();

        if (e.getEntity().getKiller() != null) {
            for (ItemStack item : e.getDrops()) {
                if (item.getType().equals(Material.CREEPER_HEAD)) e.getDrops().remove(item);
                else if (item.getType().equals(Material.ZOMBIE_HEAD)) e.getDrops().remove(item);
                else if (item.getType().equals(Material.WITHER_SKELETON_SKULL)) e.getDrops().remove(item);
                else if (item.getType().equals(Material.ZOMBIE_HEAD)) e.getDrops().remove(item);
                else if (item.getType().equals(Material.DRAGON_HEAD)) e.getDrops().remove(item);
            }
            Player killer = e.getEntity().getKiller();
            ItemStack handItem =  killer.getInventory().getItemInMainHand();
            if (handItem.hasItemMeta() && handItem.getItemMeta().hasEnchant(plugin.getEnchantsAPI().getCustomEnchant().HEAD_HUNTER)) return;
            int rng = killer.getGameMode().equals(GameMode.CREATIVE) ? 1000 : pu.headDropRNG();
            ItemStack item = pu.getEntityHead(entity, rng);
            if (item != null) e.getDrops().add(item);
        }
    }
}
