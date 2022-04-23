package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.*;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PvPListener implements Listener {

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player attacked && !attacker.getUniqueId().equals(attacked.getUniqueId())) {
            UUID uuid = attacker.getUniqueId();
            if (!data.isDuelCurrent(uuid)) {
                attacker.sendActionBar(Lang.DUEL_INTERACT_WARNING.getComponent(new String[] { attacked.getName() }));
                e.setCancelled(true);
            }
        } else if (e.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player attacker && e.getEntity() instanceof Player attacked && !attacker.getUniqueId().equals(attacked.getUniqueId())) {
                UUID uuid = attacker.getUniqueId();
                if (!data.isDuelCurrent(uuid)) {
                    attacker.sendActionBar(Lang.DUEL_INTERACT_WARNING.getComponent(new String[] { attacked.getName() }));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPvPDeath(PlayerDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Player killer = player.getKiller();
        if (killer != null) {
            UUID kUUID = killer.getUniqueId();
            if (data.isDuelingPlayer(uuid, kUUID)) {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_LOSER.getComponent(new String[] { killer.getName() })));
                killer.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_WINNER.getComponent(new String[] { player.getName() })));
                data.removeDuelingPlayer(uuid, kUUID);
            }
        }
    }

    @EventHandler
    public void onPvPQuit(PlayerQuitEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isDuelCurrent(uuid)) {
            UUID tUUID = data.getDuelCurrentPlayer(uuid);
            Player oTarget = Bukkit.getPlayer(tUUID);
            if (oTarget != null && oTarget.isOnline()) oTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_WINNER.getComponent(new String[] { player.getName() })));
            data.removeDuelingPlayer(uuid, data.getDuelCurrentPlayer(uuid));
        }
    }

    @EventHandler
    public void onPlayerPvPCommand(PlayerCommandPreprocessEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (data.isDuelCurrent(uuid)) {
            e.setCancelled(true);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.DUEL_COMMAND_ERROR.getComponent(null)));
        }
    }
}
