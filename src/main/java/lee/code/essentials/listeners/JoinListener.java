package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.builders.NameTagBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Cache cache = plugin.getCache();

        //create SQL player profile
        if (!cache.hasPlayerData(uuid)) cache.setPlayerData(uuid, 0, "DEFAULT", "n", "n", "n", "YELLOW", true);

        //register perms
        if (!player.isOp()) {
            plugin.getPermissionManager().register(player);
        }

        ChatColor color = ChatColor.valueOf(cache.getColor(uuid));
        String prefix = cache.getPrefix(uuid);
        String suffix = cache.getSuffix(uuid);

        new NameTagBuilder(player).setColor(color).setPrefix(prefix).setSuffix(suffix).build();
    }
}
