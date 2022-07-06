package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!e.isCancelled()) {
            e.setCancelled(true);
            Component message = pu.parseVariables(player, e.message());
            if (!data.isStaffChatting(uuid)) {
                plugin.getServer().sendMessage(player.displayName().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(BukkitUtils.parseColorComponent("&8: &f")).append(message));
            } else {
                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                    if (oPlayer.hasPermission("essentials.command.staffchat")) {
                        oPlayer.sendMessage(Lang.STAFF_CHAT_PREFIX.getComponent(null).append(player.displayName()).append(BukkitUtils.parseColorComponent("&3: ")).append(message).color(TextColor.color(86, 40, 255)));
                    }
                }
            }
        }
    }
}
