package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler (priority= EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!e.isCancelled()) {
            e.setCancelled(true);
            if (!cache.isMuted(uuid)) {
                plugin.getServer().sendMessage(player.displayName().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(plugin.getPU().formatC("&8: &f")).append(e.message()));
            } else player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.MUTED.getString(new String[] { cache.getMuteReason(uuid) }));
        }
    }
}
