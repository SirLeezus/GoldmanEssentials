package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChatListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!e.isCancelled()) {
            e.setCancelled(true);
            if (!data.isSpamTaskActive(uuid)) {
                pu.addSpamDelay(uuid);
                if (!cache.isMuted(uuid)) {
                    if (!cache.isTempMuted(uuid)) {
                        if (cache.hasBeenBotChecked(uuid)) {

                            Component message = pu.parseVariables(player, e.message());
                            if (data.isSpamLoggerViolation(uuid, message)) {
                                boolean shouldMute = data.addSpamLoggerViolationCount(uuid);
                                if (shouldMute) {
                                    String reason = "Chat Spam";
                                    int timeMuted = Settings.AUTO_MUTE_TIME.getValue();
                                    long milliseconds = System.currentTimeMillis();
                                    long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + timeMuted;
                                    cache.setTempMutedPlayer(uuid, reason, time, true);
                                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPMUTED.getComponent(new String[] { pu.formatSeconds(timeMuted), reason })));
                                    plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_TEMPMUTED.getComponent(new String[] { player.getName(), pu.formatSeconds(timeMuted), reason })));
                                    return;
                                }
                            } else data.resetSpamLogger(uuid, message);

                            if (!data.isStaffChatting(uuid)) {
                                plugin.getServer().sendMessage(player.displayName().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(pu.formatC("&8: &f")).append(message));
                            } else {
                                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                                    if (oPlayer.hasPermission("essentials.command.staffchat")) {
                                        oPlayer.sendMessage(Lang.STAFF_CHAT_PREFIX.getComponent(null).append(player.displayName()).append(pu.formatC("&3: ")).append(message).color(TextColor.color(86, 40, 255)));
                                    }
                                }
                            }
                        }
                    } else {
                        long time = cache.getTempMuteTime(uuid);
                        if (time > 0) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPMUTED.getComponent(new String[]{ pu.formatSeconds(time), cache.getMuteReason(uuid)})));
                        else {
                            cache.setTempMutedPlayer(uuid, "", 0, false);
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPUNMUTED.getComponent(null)));
                        }
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MUTED.getComponent(new String[]{cache.getMuteReason(uuid)})));
            } else {
                pu.addSpamDelay(uuid);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SPAM_CHAT.getComponent(null)));
            }
        }
    }
}
