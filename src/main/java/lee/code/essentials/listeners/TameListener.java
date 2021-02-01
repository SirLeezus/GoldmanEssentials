package lee.code.essentials.listeners;

import lee.code.essentials.TheEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class TameListener implements Listener {

    @EventHandler
    public void onPlayerTame(EntityTameEvent e) {
        Entity tamedEntity = e.getEntity();
        TheEssentials plugin = TheEssentials.getPlugin();

        Player owner = Bukkit.getPlayer(e.getOwner().getUniqueId());
        tamedEntity.setCustomName(plugin.getPluginUtility().format("&e" + owner.getName() + "'s Tamed " + tamedEntity.getName()));
        tamedEntity.setCustomNameVisible(true);
    }

}
