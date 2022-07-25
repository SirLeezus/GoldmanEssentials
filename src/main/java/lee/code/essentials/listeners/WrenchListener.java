package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import org.bukkit.Axis;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WrenchListener implements Listener {

    @EventHandler (priority= EventPriority.MONITOR)
    public void onWrenchInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasBlock() && e.useInteractedBlock().equals(Event.Result.ALLOW) && e.getAction().isRightClick()) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            ItemMeta handItemMeta = handItem.getItemMeta();
            if (handItemMeta != null && handItemMeta.hasCustomModelData() && handItemMeta.getCustomModelData() == 3000) {
                e.setCancelled(true);
                if (BukkitUtils.hasClickDelay(player)) return;

                Block block = e.getClickedBlock();
                if (block != null) {
                    BlockData blockData = block.getBlockData();
                    if (blockData instanceof Directional directional) {
                        if (block.getState() instanceof Chest chest) {
                            InventoryHolder inventoryHolder = chest.getInventory().getHolder();
                            if (inventoryHolder instanceof DoubleChest) return;
                        } else if (block.getState() instanceof Bed) return;

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
                        block.setBlockData(blockData);
                    } else if (blockData instanceof Orientable orientable) {
                        switch (orientable.getAxis()) {
                            case X -> orientable.setAxis(Axis.Y);
                            case Y -> orientable.setAxis(Axis.Z);
                            case Z -> orientable.setAxis(Axis.X);
                        }
                        block.setBlockData(blockData);
                    }
                }
            }
        }
    }
}
