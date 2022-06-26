package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class WrenchListener implements Listener {

    @EventHandler (priority= EventPriority.MONITOR)
    public void onWrenchInteract(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (e.hasBlock() && e.useInteractedBlock().equals(Event.Result.ALLOW) && e.useItemInHand().equals(Event.Result.ALLOW)) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            ItemMeta handItemMeta = handItem.getItemMeta();
            if (handItemMeta != null && handItemMeta.hasCustomModelData() && handItemMeta.getCustomModelData() == 3000) {
                e.setCancelled(true);
                if (data.hasPlayerClickDelay(uuid)) return;
                else pu.addPlayerClickDelay(uuid);

                Block block = e.getClickedBlock();
                if (block != null) {
                    BlockData blockData = block.getBlockData();
                    if (blockData instanceof Directional directional) {
                        if (block.getState() instanceof Chest chest) {
                            InventoryHolder inventoryHolder = chest.getInventory().getHolder();
                            if (inventoryHolder instanceof DoubleChest) return;
                        }
                        switch (directional.getFacing()) {
                            case NORTH -> directional.setFacing(BlockFace.EAST);
                            case EAST -> directional.setFacing(BlockFace.SOUTH);
                            case SOUTH -> directional.setFacing(BlockFace.WEST);
                            case WEST -> {
                                if (directional.getFaces().contains(BlockFace.UP)) directional.setFacing(BlockFace.UP);
                                else directional.setFacing(BlockFace.NORTH);
                            }
                            case UP -> directional.setFacing(BlockFace.DOWN);
                            case DOWN -> directional.setFacing(BlockFace.NORTH);
                        }
                        block.setBlockData(directional);
                    }
                }
            }
        }
    }
}
