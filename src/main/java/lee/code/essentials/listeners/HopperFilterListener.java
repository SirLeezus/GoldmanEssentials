package lee.code.essentials.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HopperFilterListener implements Listener {

    @EventHandler
    public void onHopperFilterMove(InventoryMoveItemEvent e) {
        if (e.getDestination().getHolder() instanceof Hopper) {
            ItemStack movingItem = e.getItem();
            Location location = e.getDestination().getLocation();
            if (location != null) {
                Block block = location.getBlock();
                List<ItemFrame> frames = findAttachedItemFrames(block);
                if (frames != null && !frames.isEmpty()) {
                    List<Material> whitelist = new ArrayList<>();
                    for (ItemFrame frame : frames) whitelist.add(frame.getItem().getType());
                    if (!whitelist.contains(movingItem.getType())) e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onHopperFilterPickup(InventoryPickupItemEvent e) {
        if (e.getInventory().getHolder() instanceof Hopper) {
            ItemStack pickupItem = e.getItem().getItemStack();
            Location location = e.getInventory().getLocation();
            if (location != null) {
                Block block = location.getBlock();
                List<ItemFrame> frames = findAttachedItemFrames(block);
                if (frames != null && !frames.isEmpty()) {
                    List<Material> whitelist = new ArrayList<>();
                    for (ItemFrame frame : frames) whitelist.add(frame.getItem().getType());
                    if (!whitelist.contains(pickupItem.getType())) e.setCancelled(true);
                }
            }
        }
    }

    private List<ItemFrame> findAttachedItemFrames(Block block) {
        return block.getWorld().getNearbyEntities(block.getLocation(), 2, 2, 2).stream()
                .filter(f -> f instanceof ItemFrame).map(ItemFrame.class::cast)
                .filter(f -> block.equals(getHopperAttachedTo(f).orElse(null))).collect(Collectors.toList());
    }

    private Optional<Block> getHopperAttachedTo(ItemFrame frame) {
        Block attached = frame.getLocation().getBlock().getRelative(frame.getAttachedFace());
        if (attached.getType() != Material.HOPPER) return Optional.empty();
        return Optional.of(attached);
    }
}
