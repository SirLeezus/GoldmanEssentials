package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class HotbarListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (GoldmanEssentials.getPlugin().getCacheManager().isHotbarLocked(player.getUniqueId())) {
            player.sendActionBar(Lang.ERROR_LOCKED_HOTBAR.getComponent(null));
            e.setCancelled(true);
        }
    }
}
