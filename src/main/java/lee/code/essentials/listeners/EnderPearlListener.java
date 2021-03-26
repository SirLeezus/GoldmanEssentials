package lee.code.essentials.listeners;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EnderPearlListener implements Listener {

    @EventHandler
    public void onEnderPearlLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player && e.getEntityType().equals(EntityType.ENDER_PEARL)) {
            Player player = (Player) e.getEntity().getShooter();
            if (e.getEntityType().equals(EntityType.ENDER_PEARL)) {
                e.getEntity().addPassenger(player);
            }
        }
    }

    @EventHandler
    public void onEnderPearlTeleport(PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            p.setNoDamageTicks(1);
            p.teleport(event.getTo());
        }
    }
}
