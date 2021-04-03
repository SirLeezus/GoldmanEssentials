package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        //set quit message format
        if (plugin.getData().getVanishedPlayers().contains(uuid)) {
            e.quitMessage(null);
            plugin.getData().removeVanishedPlayer(uuid);
        }
        else e.quitMessage(player.displayName().append(Lang.PLAYER_QUIT.getComponent(null)));
    }
}
