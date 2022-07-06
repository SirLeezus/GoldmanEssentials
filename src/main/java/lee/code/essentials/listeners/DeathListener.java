package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeathMessage(PlayerDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        CacheManager cacheManager = plugin.getCacheManager();
        Data data = plugin.getData();

        Player player = e.getEntity();
        Location location = player.getLocation();
        UUID uuid = player.getUniqueId();
        Component dm = e.deathMessage();

        //afk check
        if (data.isAFK(uuid)) {
            data.removeAFK(uuid);
            pu.updateDisplayName(player, false);
        }

        if (dm != null && !cacheManager.isVanishPlayer(uuid)) plugin.getServer().sendMessage(Lang.DEATH_PREFIX.getComponent(null).append(dm).append(Component.text(".")).color(NamedTextColor.GRAY));
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.DEATH_CORDS.getComponent(new String[] { player.getWorld().getName(), BukkitUtils.parseDecimalValue(location.getX()), BukkitUtils.parseDecimalValue(location.getY()), BukkitUtils.parseDecimalValue(location.getZ()) })));
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
