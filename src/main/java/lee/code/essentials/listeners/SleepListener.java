package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SleepListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerSleep(PlayerBedEnterEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        World world = player.getWorld();
        String worldName = world.getName();

        if (e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            if (data.getSleepTask(worldName) == null && !world.isDayTime()) {

                data.clearSleepingPlayers(worldName);
                data.addSleepingPlayer(worldName, uuid);

                AtomicLong time = new AtomicLong(world.getTime());
                Title.Times times = Title.Times.times(Duration.ZERO, Ticks.duration(3), Duration.ZERO);

                data.addSleepTask(worldName, new BukkitRunnable() {

                    @Override
                    public void run() {
                        int percent = data.getSleepingPlayersSize(worldName) * 100 / BukkitUtils.getOnlinePlayers().size();
                        double base = Math.round(percent * 10.0) / 20.0;
                        long speed = (long) base;
                        long worldTime = world.getTime();

                        if (worldTime > 1000 && worldTime < 1150) {
                            data.removeSleepTask(worldName);
                            this.cancel();
                        } else {
                            world.setTime(time.addAndGet(speed));

                            for (UUID sUUID : data.getSleepingPlayers(worldName)) {
                                OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(sUUID);
                                if (oPlayer.isOnline()) {
                                    Player sleepingPlayer = oPlayer.getPlayer();
                                    if (sleepingPlayer != null) {
                                        sleepingPlayer.showTitle(Title.title(Lang.SLEEPING_TITLE.getComponent(new String[] { BukkitUtils.parseMinecraftTime(world.getTime()) }), Lang.SLEEPING_SUBTITLE.getComponent(new String[] { String.valueOf(data.getSleepingPlayersSize(worldName)), String.valueOf(BukkitUtils.getOnlinePlayers().size()) }), times));
                                    } else data.removeSleepingPlayer(worldName, sUUID);
                                }
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0, 1L));
            } else if (!data.isSleepingPlayer(worldName, uuid)) data.addSleepingPlayer(worldName, uuid);
        }
    }
    @EventHandler
    public void onPlayerSleep(PlayerBedLeaveEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        World world = player.getWorld();
        String worldName = world.getName();

        data.removeSleepingPlayer(worldName, uuid);
        if (data.getSleepingPlayers(worldName).isEmpty()) {
            if (data.getSleepTask(worldName) != null) {
                Bukkit.getScheduler().cancelTask(data.getSleepTask(worldName).getTaskId());
                data.removeSleepTask(worldName);
            }
        }
    }
}
