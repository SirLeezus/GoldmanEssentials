package lee.code.essentials.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
        long milliseconds = System.currentTimeMillis();

        if (data.isAFK(uuid)) setNotAFK(player);
        data.setPlayerLastMovedTime(uuid, milliseconds);
    }

    @EventHandler
    public void onAFKCommand(PlayerCommandPreprocessEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        long milliseconds = System.currentTimeMillis();

        if (data.isAFK(uuid)) setNotAFK(player);
        data.setPlayerLastMovedTime(uuid, milliseconds);
    }

    @EventHandler
    public void onAFKChat(AsyncChatEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        long milliseconds = System.currentTimeMillis();

        if (data.isAFK(uuid)) setNotAFK(player);
        data.setPlayerLastMovedTime(uuid, milliseconds);
    }

    @EventHandler
    public void onAFKQuit(PlayerQuitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isAFK(uuid)) setNotAFK(player);
    }

    @EventHandler
    public void onAFKJoin(PlayerJoinEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        long milliseconds = System.currentTimeMillis();

        if (data.isAFK(uuid)) setNotAFK(player);
        data.setPlayerLastMovedTime(uuid, milliseconds);
    }

    @EventHandler
    public void onAFKInventoryClick(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        long milliseconds = System.currentTimeMillis();

        if (data.isAFK(uuid)) setNotAFK(player);
        data.setPlayerLastMovedTime(uuid, milliseconds);
    }

    private void setNotAFK(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();;
        Data data = plugin.getData();
        PU pu = plugin.getPU();
        UUID uuid = player.getUniqueId();

        data.removeAFK(uuid);
        pu.updateDisplayName(player, false);
    }
}
