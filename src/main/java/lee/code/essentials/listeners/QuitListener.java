package lee.code.essentials.listeners;

import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        //set quit message format
        e.quitMessage(player.displayName().append(Lang.PLAYER_QUIT.getComponent(null)));
    }
}
