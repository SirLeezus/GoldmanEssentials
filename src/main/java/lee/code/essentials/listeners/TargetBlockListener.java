package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class TargetBlockListener implements Listener {

    @EventHandler
    public void onProjectileTargetHit(ProjectileHitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Projectile projectile = e.getEntity();
        Block block = e.getHitBlock();
        if (block != null) {
            if (block.getType().equals(Material.TARGET)) {
                if (projectile.getShooter() instanceof Player player) {
                    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                    scheduler.runTaskLater(plugin, () -> {
                        int power = block.getBlockPower();
                        player.sendActionBar(Lang.TARGET.getComponent(new String[] { String.valueOf(power) }));
                    }, 2L);
                }
            }
        }
    }
}
