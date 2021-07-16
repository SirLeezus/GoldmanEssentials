package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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
}
