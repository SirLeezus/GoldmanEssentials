package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.event.ClickEvent;
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
            plugin.getServer().sendMessage(player.displayName().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(plugin.getPU().formatC("&8: &f")).append(e.message()));
        }
    }
}
