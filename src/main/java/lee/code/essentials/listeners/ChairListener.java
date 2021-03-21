package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.nms.CustomChair;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;

public class ChairListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onSit(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        Action action = e.getAction();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.useInteractedBlock().equals(Event.Result.ALLOW) || e.useItemInHand().equals(Event.Result.ALLOW)) {
                    Block block = player.getTargetBlock(2);
                    if (block != null) {
                        if (block.getBlockData() instanceof Stairs || block.getType().name().contains("CARPET")) {
                            if (player.getVehicle() == null) {
                                Location location;
                                if (block.getBlockData() instanceof Stairs) location = block.getLocation().add(0.5, -1.2, 0.5);
                                else location = block.getLocation().add(0.5, -1.65, 0.5);
                                CustomChair chair = new CustomChair(location);
                                WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
                                world.addEntity(chair);
                                Entity entityArrow = chair.getBukkitEntity();
                                entityArrow.addPassenger(player);
                                plugin.getData().addChairLocation(location.getBlock().getLocation());
                            }
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
            Entity armorStand = e.getDismounted();
            Player player = (Player) e.getEntity();
            armorStand.remove();
            plugin.getData().removeChairLocation(player.getLocation().getBlock().getLocation().add(0, -1, 0));
        }
    }

    @EventHandler
    public void onChairInteract(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (e.hasBlock()) {
            Block block = e.getClickedBlock();
            if (block != null) {
                Location location = block.getLocation().add(0, -2, 0);
                Location location2 = block.getLocation().add(0, -1, 0);
                if (plugin.getData().getChairLocations().contains(location) || plugin.getData().getChairLocations().contains(location2)) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChairPistonMove(BlockPistonExtendEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        for (Block block : new ArrayList<>(e.getBlocks())) {
            Location location = block.getLocation().add(0, -2, 0);
            if (plugin.getData().getChairLocations().contains(location)) e.setCancelled(true);
        }
    }
}
