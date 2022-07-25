package lee.code.essentials.listeners;

import lee.code.essentials.lists.Lang;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftCat;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftParrot;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftWolf;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class PatchesListener implements Listener {

    //fix for the advancement "nether/ride_strider" & "nether/ride_strider_in_overworld_lava" 1.18.1
    @EventHandler
    public void onRideStrider(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().isRightClick()) {
            Entity riding = player.getVehicle();
            if (riding != null && riding.getType().equals(EntityType.STRIDER)) {
                NamespacedKey keyRide = NamespacedKey.minecraft("nether/ride_strider");
                Advancement advancementRide = Bukkit.getAdvancement(keyRide);
                NamespacedKey keyRideWorld = NamespacedKey.minecraft("nether/ride_strider_in_overworld_lava");
                Advancement advancementRideWorld = Bukkit.getAdvancement(keyRideWorld);

                if (advancementRide != null && advancementRideWorld != null) {
                    ItemStack handItem = player.getInventory().getItemInMainHand();
                    if (handItem.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK)) {
                        AdvancementProgress progressRide = player.getAdvancementProgress(advancementRide);
                        if (!progressRide.isDone()) {
                            for (String criteria : progressRide.getRemainingCriteria()) progressRide.awardCriteria(criteria);
                        }
                        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            AdvancementProgress progressRideWorld = player.getAdvancementProgress(advancementRideWorld);
                            if (!progressRideWorld.isDone()) {
                                for (String criteria : progressRideWorld.getRemainingCriteria()) progressRideWorld.awardCriteria(criteria);
                            }
                        }
                    }
                }
            }
        }
    }

    //patches how rare ocelots are
    @EventHandler
    public void onOcelotSpawn(CreatureSpawnEvent e) {
        CreatureSpawnEvent.SpawnReason spawnReason = e.getSpawnReason();
        if (!spawnReason.equals(CreatureSpawnEvent.SpawnReason.CUSTOM) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.EGG)) {
            if (e.getEntity() instanceof Animals) {
                Location location = e.getLocation();
                Block block = location.getBlock();
                if (!block.isLiquid() && block.getBiome().equals(Biome.JUNGLE)) {
                    if ((int) (Math.random() * 10) == 1) { // 10% chance
                        location.getWorld().spawnEntity(location, EntityType.OCELOT);
                    }
                }
            }
        }
    }

    //patch for how rare drowned drop tridents
    @EventHandler
    public void onDrownedDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Drowned drowned) {
            if (drowned.getEquipment().getItemInMainHand().getType().equals(Material.TRIDENT)) {
                ItemStack trident = new ItemStack(Material.TRIDENT);
                if (!e.getDrops().contains(trident)) {
                    if ((int) (Math.random() * 2) == 1) { // 50% chance
                        entity.getWorld().dropItemNaturally(entity.getLocation(), trident);
                    }
                }
            }
        }
    }

    //patch end portals working with entities
    @EventHandler
    public void onEndPortalUse(EntityPortalEvent e) {
        if (e.getFrom().getWorld().getEnvironment().equals(World.Environment.THE_END)) {
            if (e.getFrom().getBlock().getType().equals(Material.END_PORTAL)) {
                e.setCancelled(true);
            }
        }
    }

    //patch for scoreboards not sending owner info to prevent prefix from showing
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerInteractWithTamed(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!e.isCancelled() && player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            if (e.getRightClicked() instanceof Parrot targetParrot) {
                if (targetParrot.isTamed()) {
                    if (targetParrot.getOwner() != null) {
                        if (targetParrot.getOwner().getUniqueId().equals(player.getUniqueId())) {
                            e.setCancelled(true);
                            net.minecraft.world.entity.animal.Parrot parrotHandle = ((CraftParrot)targetParrot).getHandle();
                            parrotHandle.setOrderedToSit(!parrotHandle.isInSittingPose());
                        } else player.sendActionBar(Lang.ERROR_TAMED_SIT.getComponent(new String[] { targetParrot.getOwner().getName() }));
                    }
                }
            } else if (e.getRightClicked() instanceof Wolf targetWolf) {
                if (targetWolf.isTamed()) {
                    if (targetWolf.getOwner() != null) {
                        if (targetWolf.getOwner().getUniqueId().equals(player.getUniqueId())) {
                            e.setCancelled(true);
                            net.minecraft.world.entity.animal.Wolf wolfHandle = ((CraftWolf)targetWolf).getHandle();
                            wolfHandle.setOrderedToSit(!targetWolf.isSitting());
                        } else player.sendActionBar(Lang.ERROR_TAMED_SIT.getComponent(new String[] { targetWolf.getOwner().getName() }));
                    }
                }
            } else if (e.getRightClicked() instanceof Cat targetCat) {
                if (targetCat.isTamed()) {
                    if (targetCat.getOwner() != null) {
                        if (targetCat.getOwner().getUniqueId().equals(player.getUniqueId())) {
                            e.setCancelled(true);
                            net.minecraft.world.entity.animal.Cat catHandle = ((CraftCat)targetCat).getHandle();
                            catHandle.setOrderedToSit(!targetCat.isSitting());
                        } else player.sendActionBar(Lang.ERROR_TAMED_SIT.getComponent(new String[] { targetCat.getOwner().getName() }));
                    }
                }
            }
        }
    }

    //patch to prevent players from going outside of world border
    @EventHandler
    public void onWorldBorderTeleport(PlayerTeleportEvent e) {
        Location locationTo = e.getTo();
        if (!locationTo.getWorld().getWorldBorder().isInside(locationTo)) {
            e.setCancelled(true);
        }
    }
}
