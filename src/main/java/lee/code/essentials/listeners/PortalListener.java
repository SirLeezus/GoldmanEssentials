package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onCreatePortal(PortalCreateEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        String worldName = e.getWorld().getName();
        if (!data.getWhitelistedWorlds().contains(worldName)) {
            e.setCancelled(true);
        }
    }
}
