package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.nms.CustomChair;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class ChairListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onSit(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR) && action.equals(Action.RIGHT_CLICK_BLOCK) && e.getBlockFace() == BlockFace.UP && !player.isInsideVehicle()) {
            if (e.useInteractedBlock().equals(Event.Result.ALLOW) || e.useItemInHand().equals(Event.Result.ALLOW)) {
                Block block = player.getTargetBlock(2);
                if (block != null) {
                    if (block.getBlockData() instanceof Stairs || block.getType().name().contains("CARPET")) {
                        if (player.getVehicle() == null) {
                            Location location;
                            if (block.getBlockData() instanceof Stairs) location = block.getLocation().add(0.5, 0.3, 0.5);
                            else location = block.getLocation().add(0.5, -0.2, 0.5);
                            CustomChair chair = new CustomChair(location);
                            WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
                            world.addEntity(chair);
                            Entity entityChair = chair.getBukkitEntity();
                            entityChair.addPassenger(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChairDismount(EntityDismountEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (e.getDismounted() instanceof ArmorStand) {
            Entity entity = e.getDismounted();
            entity.remove();

            if (e.getEntity() instanceof Player) {
                Player player = ((Player) e.getEntity()).getPlayer();
                if (player != null) Bukkit.getScheduler().runTaskLater(plugin, () -> player.teleport(player.getLocation().add(0, 1, 0)), 1);
            }
        }
    }

    @EventHandler
    public void onChairBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        for (Entity entity : e.getBlock().getWorld().getNearbyEntities(block.getBoundingBox())) {
            if (entity instanceof ArmorStand) {
                if (entity.getCustomName() != null && entity.getCustomName().equals("chair"))
                entity.remove();
            }
        }
    }
}
