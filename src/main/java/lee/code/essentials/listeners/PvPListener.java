package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PvPListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (!e.isCancelled()) {
            if (e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player attacked) {
                plugin.getPU().addPlayerPvPDelay(attacker.getUniqueId());
                plugin.getPU().addPlayerPvPDelay(attacked.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerPvPCommand(PlayerCommandPreprocessEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isPvPTaskActive(uuid)) {
            long time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            long delay = data.getPVPTimer(uuid);
            if (time < delay) {
                e.setCancelled(true);
                long timeLeft = delay - time;
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.PVP_DELAY.getComponent(new String[] { plugin.getPU().formatSeconds(timeLeft) })));
            }
        }
    }
}
