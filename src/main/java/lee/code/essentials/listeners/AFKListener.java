package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class AFKListener implements Listener {

    @EventHandler
    public void onAFKMove(PlayerMoveEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isAFK(uuid)) toggleAFK(player, false);


    }

    @EventHandler
    public void onAFKCommand(PlayerCommandPreprocessEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isAFK(uuid)) toggleAFK(player, false);

    }

    @EventHandler
    public void onAFKChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isAFK(uuid)) toggleAFK(player, false);
    }

    @EventHandler
    public void onAFKQuit(PlayerQuitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isAFK(uuid)) toggleAFK(player, false);
    }

    private void toggleAFK(Player player, boolean afk) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        PU pu = plugin.getPU();
        UUID uuid = player.getUniqueId();

        if (!afk) {
            data.removeAFK(uuid);
            pu.updateDisplayName(player, false);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.AFK_OFF.getComponent(null)));
        }
    }

    private void updateAFKLocation(Player player) {

    }
}
