package lee.code.essentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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
}
