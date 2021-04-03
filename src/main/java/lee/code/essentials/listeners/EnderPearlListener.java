package lee.code.essentials.listeners;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EnderPearlListener implements Listener {

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
