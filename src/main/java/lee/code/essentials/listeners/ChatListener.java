package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!e.isCancelled()) {
            e.setCancelled(true);
            if (!data.isSpamTaskActive(uuid)) {
                plugin.getPU().addSpamDelay(uuid);
                if (!cache.isMuted(uuid)) {
                    if (!cache.isTempMuted(uuid)) {
                        if (cache.hasBeenBotChecked(uuid)) {
                            Component message = plugin.getPU().parseChatVariables(player, e.message());
                            if (!data.isStaffChatting(uuid)) {
                                plugin.getServer().sendMessage(player.displayName().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(plugin.getPU().formatC("&8: &f")).append(message));
                            } else {
                                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                                    if (oPlayer.hasPermission("essentials.command.staffchat")) {
                                        oPlayer.sendMessage(Lang.STAFF_CHAT_PREFIX.getComponent(null).append(player.displayName()).append(plugin.getPU().formatC("&3: ")).append(message).color(TextColor.color(86, 40, 255)));
                                    }
                                }
                            }
                        }
                    } else {
                        long time = cache.getTempMuteTime(uuid);
                        if (time > 0) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPMUTED.getComponent(new String[]{ plugin.getPU().formatSeconds(time), cache.getMuteReason(uuid)})));
                        else {
                            cache.setTempMutedPlayer(uuid, "", 0, false);
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPUNMUTED.getComponent(null)));
                        }
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MUTED.getComponent(new String[]{cache.getMuteReason(uuid)})));
            } else {
                plugin.getPU().addSpamDelay(uuid);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SPAM_CHAT.getComponent(null)));
            }
        }
    }
}
