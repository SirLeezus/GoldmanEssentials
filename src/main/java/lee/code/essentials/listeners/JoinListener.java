package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.builders.NameBuilder;
import lee.code.essentials.database.Cache;
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
        if (!player.isOp()) plugin.getPermissionManager().register(player);

        //set player prefix, suffix, color
        ChatColor color = ChatColor.valueOf(cache.getColor(uuid)); String prefix = cache.getPrefix(uuid); String suffix = cache.getSuffix(uuid);
        new NameBuilder(player).setColor(color).setPrefix(prefix).setSuffix(suffix).build();

        //Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new NameTagBuilder(player).setColor(color).setPrefix(prefix).setSuffix(suffix).build());
    }
}
