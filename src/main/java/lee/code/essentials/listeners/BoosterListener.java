package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BoosterListener implements Listener {

    @EventHandler
    public void onBoosterBlockBreak(BlockBreakEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        PU pu = plugin.getPU();

        if (cache.isBoosterActive()) {
            String id = cache.getActiveBoosterID();
            Player player = e.getPlayer();
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                Block block = e.getBlock();
                Location location = block.getLocation();
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                if (pu.getBoosterDropBlocks().contains(block.getType())) {
                    if (!mainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                        if (!block.getDrops().isEmpty()) {
                            int fortuneLevel = mainHand.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) ? mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) : 0;
                            int multiplier = Integer.parseInt(cache.getBoosterMultiplier(id));
                            int booster = multiplier + fortuneLevel;
                            List<ItemStack> drops = new ArrayList<>();
                            for (ItemStack item : new ArrayList<>(block.getDrops())) {
                                int amount = item.getAmount();
                                item.setAmount(amount * booster);
                                drops.add(item);
                            }
                            if (!drops.isEmpty()) {
                                e.setDropItems(false);
                                for (ItemStack item : drops) location.getWorld().dropItemNaturally(location, item);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBoosterEntityDeath(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
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
                EntityEquipment entityEquipment = e.getEntity().getEquipment();
                for (ItemStack item : new ArrayList<>(e.getDrops())) {
                    if (entityEquipment != null) {
                        if (item.equals(entityEquipment.getHelmet())) {
                            drops.add(item);
                            continue;
                        } else if (item.equals(entityEquipment.getChestplate())) {
                            drops.add(item);
                            continue;
                        } else if (item.equals(entityEquipment.getLeggings())) {
                            drops.add(item);
                            continue;
                        } else if (item.equals(entityEquipment.getBoots())) {
                            drops.add(item);
                            continue;
                        } else if (item.equals(entityEquipment.getItemInMainHand())) {
                            drops.add(item);
                            continue;
                        } else if (item.equals(entityEquipment.getItemInOffHand())) {
                            drops.add(item);
                            continue;
                        }
                    }
                    int multiplier = Integer.parseInt(cache.getBoosterMultiplier(id));
                    int booster = multiplier + lootingLevel;
                    int amount = item.getAmount();
                    if (item.getMaxStackSize() > 1) item.setAmount(amount * booster);
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
