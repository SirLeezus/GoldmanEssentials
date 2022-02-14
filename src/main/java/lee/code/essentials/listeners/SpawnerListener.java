package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Block block = e.getBlock();
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        World world = player.getWorld();

        if (block.getState() instanceof CreatureSpawner cs) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.containsEnchantment(Enchantment.SILK_TOUCH) || player.getGameMode().equals(GameMode.CREATIVE)) {
                EntityType mob = cs.getSpawnedType();
                ItemStack spawner = plugin.getPU().createSpawner(mob);
                world.dropItemNaturally(location, spawner);
            }
        }
    }

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (block.getState() instanceof CreatureSpawner cs) {
            ItemStack spawner = player.getInventory().getItemInMainHand();
            BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
            if (spawnerMeta != null) {
                CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
                cs.setSpawnedType(spawnerCS.getSpawnedType());
                cs.update();
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onSpawnerExplode(EntityExplodeEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        World world = e.getLocation().getWorld();

        if (!e.isCancelled()) {
            for (Block block : e.blockList()) {
                if (block.getState() instanceof CreatureSpawner cs) {
                    EntityType mob = cs.getSpawnedType();
                    ItemStack spawner = plugin.getPU().createSpawner(mob);
                    world.dropItemNaturally(block.getLocation(), spawner);
                }
            }
        }
    }

    @EventHandler
    public void onAnvilRenameDeny(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            if (e.getInventory().getType() != InventoryType.ANVIL) return;
            if (e.getSlotType() != InventoryType.SlotType.RESULT) return;
            ItemStack clicked = e.getInventory().getItem(0);
            if (clicked != null) {
                if (clicked.getType().equals(Material.SPAWNER)) e.setCancelled(true);
                if (clicked.getType().equals(Material.PLAYER_HEAD)) e.setCancelled(true);
            }
        }
    }
}
