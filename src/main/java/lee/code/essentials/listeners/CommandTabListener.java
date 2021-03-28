package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.UUID;

public class CommandTabListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onCommandTabShow(PlayerCommandSendEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!player.isOp()) {
            e.getCommands().clear();
            e.getCommands().addAll(plugin.getPermissionManager().getCommands(uuid));
        }
    }
}
