package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.UUID;

public class BackListener implements Listener {

    @EventHandler
    public void onTeleportBackSave(PlayerTeleportEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            Player player = e.getPlayer();
            UUID uuid = player.getUniqueId();
            String location = BukkitUtils.serializeLocation(player.getLocation());
            plugin.getData().setBackLocation(uuid, location);
        }
    }

    @EventHandler
    public void onDeathBackSave(PlayerDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Player player = e.getEntity();
        UUID uuid = player.getUniqueId();
        String location = BukkitUtils.serializeLocation(player.getLocation());
        plugin.getData().setBackLocation(uuid, location);
    }
}
