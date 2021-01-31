package lee.code.essentials.listeners;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.database.SQLite;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TheEssentials plugin = TheEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        SQLite SQL = plugin.getSqLite();

        //register perms
        if (!player.isOp()) {
            plugin.getRegisterPermissions().registerDefault(player);
        }

        //create SQL player profile
        if (!SQL.hasPlayerProfile(uuid)) {
            SQL.createPlayerProfile(uuid, 0);
        }
    }
}
