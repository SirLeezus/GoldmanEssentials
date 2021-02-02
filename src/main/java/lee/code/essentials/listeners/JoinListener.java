package lee.code.essentials.listeners;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.nametags.NameTagBuilder;
import org.bukkit.ChatColor;
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

        //create SQL player profile
        if (!SQL.hasPlayerProfile(uuid)) {
            SQL.createPlayerProfile(uuid, 0, "DEFAULT", "n", "n", "n", "YELLOW");
        }

        //register perms
        if (!player.isOp()) {
            plugin.getPermissionManager().register(player);
        }


        ChatColor color = ChatColor.valueOf(SQL.getColor(uuid));
        String prefix = SQL.getPrefix(uuid);
        String suffix = SQL.getSuffix(uuid);

        new NameTagBuilder(player).setColor(color).setPrefix(prefix).setSuffix(suffix).build();
    }
}
