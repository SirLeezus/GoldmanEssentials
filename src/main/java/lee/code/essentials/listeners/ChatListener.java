package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler (priority= EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();

        if (!e.isCancelled()) {
            e.setCancelled(true);
            plugin.getServer().sendMessage(player.displayName().append(Component.text(plugin.getPU().format("&8: &f"))).append(e.message()));
        }
    }
}
