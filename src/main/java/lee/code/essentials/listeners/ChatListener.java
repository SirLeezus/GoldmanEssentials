package lee.code.essentials.listeners;

import lee.code.essentials.TheEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        TheEssentials plugin = TheEssentials.getPlugin();
        Player player = e.getPlayer();
        e.setCancelled(true);

        Bukkit.broadcastMessage(plugin.getPluginUtility().format(player.getDisplayName() + "&8: &f" + e.getMessage()));
    }
}
