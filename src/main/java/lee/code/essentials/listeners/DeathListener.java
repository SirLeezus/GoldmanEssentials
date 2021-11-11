package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
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
        Cache cache = plugin.getCache();

        Player player = e.getEntity();
        Location location = player.getLocation();
        UUID uuid = player.getUniqueId();
        Component dm = e.deathMessage();
        if (dm != null && !cache.isVanishPlayer(uuid)) plugin.getServer().sendMessage(Lang.DEATH_PREFIX.getComponent(null).append(dm).append(Component.text(".")).color(NamedTextColor.GRAY));
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.DEATH_CORDS.getComponent(new String[] { player.getWorld().getName(), pu.formatDecimal(location.getX()), pu.formatDecimal(location.getY()), pu.formatDecimal(location.getZ()) })));
    }

    @EventHandler
    public void onPlayerRespawnTeleport(PlayerRespawnEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Player player = e.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();

        if (player.getBedSpawnLocation() == null && !(environment.equals(World.Environment.NETHER))) e.setRespawnLocation(cache.getSpawn());
        else if (!e.isAnchorSpawn() && environment.equals(World.Environment.NETHER)) e.setRespawnLocation(cache.getSpawn());
    }
}
