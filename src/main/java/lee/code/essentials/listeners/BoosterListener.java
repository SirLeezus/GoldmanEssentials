package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BoosterListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBoosterBlockBreak(BlockBreakEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        PU pu = plugin.getPU();
        Data data = plugin.getData();

        if (cache.isBoosterActive()) {
            String id = cache.getActiveBoosterID();
            Player player = e.getPlayer();
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                Block block = e.getBlock();
                Location location = block.getLocation();
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                if (data.getSupportedBoosterBlocks().contains(block.getType())) {
                    if (!mainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                        if (!block.getDrops().isEmpty()) {
                            int fortuneLevel = mainHand.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) ? mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) : 0;
                            int booster = Integer.parseInt(cache.getBoosterMultiplier(id));
                            List<ItemStack> drops = new ArrayList<>(block.getDrops());
                            ItemStack item = new ItemStack(drops.get(0).getType(), drops.size());
                            item.setAmount(Math.min(pu.getDropCount(fortuneLevel) * booster, item.getMaxStackSize()));
                            e.setDropItems(false);
                            location.getWorld().dropItemNaturally(location, item);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBoosterEntityDeath(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        if (cache.isBoosterActive()) {
            if (!(e.getEntity() instanceof Player)) {
                String id = cache.getActiveBoosterID();
                int lootingLevel = 0;
                Player killer = e.getEntity().getKiller();
                if (killer != null) {
                    ItemStack mainHand = killer.getInventory().getItemInMainHand();
                    lootingLevel = mainHand.containsEnchantment(Enchantment.LOOT_BONUS_MOBS) ? mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) : 0;
                }
                List<ItemStack> drops = new ArrayList<>();
                int booster = Integer.parseInt(cache.getBoosterMultiplier(id));
                for (ItemStack item : new ArrayList<>(e.getDrops())) {
                    int dropCount = pu.getDropCount(lootingLevel) * booster;
                    item.setAmount(Math.min(dropCount, item.getMaxStackSize()));
                    drops.add(item);
                }
                if (!drops.isEmpty()) {
                    e.getDrops().clear();
                    e.getDrops().addAll(drops);
                }
            }
        }
    }
}
