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

import java.util.ArrayList;
import java.util.List;

public class HeadDropListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHeadDrop(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Entity entity = e.getEntity();

        if (e.getEntity().getKiller() != null) {
            List<ItemStack> drops = new ArrayList<>(e.getDrops());
            if (!e.getDrops().isEmpty()) {
                for (ItemStack item : drops) {
                    if (item.getType().equals(Material.CREEPER_HEAD)) { drops.remove(item); }
                    else if (item.getType().equals(Material.ZOMBIE_HEAD)) drops.remove(item);
                    else if (item.getType().equals(Material.WITHER_SKELETON_SKULL)) drops.remove(item);
                    else if (item.getType().equals(Material.ZOMBIE_HEAD)) drops.remove(item);
                    else if (item.getType().equals(Material.DRAGON_HEAD)) drops.remove(item);
                }
            }
            e.getDrops().clear();
            e.getDrops().addAll(drops);
            Player killer = e.getEntity().getKiller();
            ItemStack handItem =  killer.getInventory().getItemInMainHand();
            if (handItem.hasItemMeta() && handItem.getItemMeta().hasEnchant(plugin.getEnchantsAPI().getCustomEnchant().HEAD_HUNTER)) return;
            int rng = killer.getGameMode().equals(GameMode.CREATIVE) ? 1000 : pu.headDropRNG();
            ItemStack item = pu.getEntityHead(entity, rng);
            if (item != null) e.getDrops().add(item);
        }
    }
}
