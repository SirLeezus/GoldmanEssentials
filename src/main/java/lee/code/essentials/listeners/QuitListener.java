package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuitListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        //ban check
        if (cache.isBanned(uuid) || cache.isTempBanned(uuid)) {
            e.quitMessage(null);
            return;
        }

        //afk check
        if (data.isAFK(uuid)) {
            data.removeAFK(uuid);
            pu.updateDisplayName(player, false);
        }

        //playtime update
        cache.setPlayTime(uuid, player.getStatistic(Statistic.PLAY_ONE_MINUTE));

        //set quit message format
        if (data.getVanishedPlayers().contains(uuid)) {
            e.quitMessage(null);
            data.removeVanishedPlayer(uuid);
        } else e.quitMessage(player.displayName().append(Lang.PLAYER_QUIT.getComponent(null)));
    }
}
