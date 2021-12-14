package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandTabListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onCommandTabShow(PlayerCommandSendEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PermissionManager pm = plugin.getPermissionManager();

        Player player = e.getPlayer();

        if (!player.isOp()) {
            e.getCommands().clear();
            e.getCommands().addAll(pm.getCommands(player));
        }
    }
}
