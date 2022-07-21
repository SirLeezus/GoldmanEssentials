package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeathMessage(PlayerDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Player player = e.getEntity();
        Location location = player.getLocation();
        Player killer = player.getKiller();

        String match = player.getName();
        Component dm = e.deathMessage();
        if (dm != null) {
            TextReplacementConfig textReplacementConfig = TextReplacementConfig.builder()
                    .matchLiteral(match)
                    .replacement(player.displayName())
                    .build();
            dm = dm.replaceText(textReplacementConfig);

            if (killer != null) {
                String match2 = killer.getName();
                TextReplacementConfig textReplacementConfig2 = TextReplacementConfig.builder()
                        .matchLiteral(match2)
                        .replacement(killer.displayName())
                        .build();
                dm = dm.replaceText(textReplacementConfig2);
            }

            if (!cacheManager.isVanishPlayer(player.getUniqueId())) plugin.getServer().sendMessage(Lang.DEATH_PREFIX.getComponent(null).append(dm).append(Component.text(".")).color(NamedTextColor.GRAY));
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.DEATH_CORDS.getComponent(new String[] { player.getWorld().getName(), BukkitUtils.parseDecimalValue(location.getX()), BukkitUtils.parseDecimalValue(location.getY()), BukkitUtils.parseDecimalValue(location.getZ()) })));
        }
    }

    @EventHandler
    public void onPlayerRespawnTeleport(PlayerRespawnEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Player player = e.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();

        if (player.getBedSpawnLocation() == null && !(environment.equals(World.Environment.NETHER))) e.setRespawnLocation(cacheManager.getSpawn());
        else if (!e.isAnchorSpawn() && environment.equals(World.Environment.NETHER)) e.setRespawnLocation(cacheManager.getSpawn());
    }
}
