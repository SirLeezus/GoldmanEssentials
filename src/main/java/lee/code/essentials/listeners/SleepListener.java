package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        World world = player.getWorld();
        Data data = plugin.getData();

        Title.Times weatherTimes = Title.Times.of(Ticks.duration(20), Ticks.duration(100), Ticks.duration(20));

        if (e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            if (data.getSleepTask() == null && !world.isDayTime() && world == Bukkit.getServer().getWorlds().get(0)) {

                world.setStorm(false);
                if (world.isThundering()) {
                    world.setThundering(false);
                    player.showTitle(Title.title(Lang.SLEEPING_TITLE.getComponent(new String[] { plugin.getPU().formatTime(world.getTime()) }), Lang.SLEEPING_WEATHER_CLEARED_SUBTITLE.getComponent(null), weatherTimes));
                    e.setUseBed(Event.Result.DENY);
                    return;
                }

                data.getSleepingPlayers().clear();
                data.addSleepingPlayer(uuid);

                AtomicLong time = new AtomicLong(world.getTime());
                Title.Times times = Title.Times.of(Duration.ZERO, Ticks.duration(3), Duration.ZERO);

                data.setSleepTask(new BukkitRunnable() {

                    @Override
                    public void run() {
                        int percent = plugin.getData().getSleepingPlayers().size() * 100 / plugin.getPU().getOnlinePlayers().size();
                        double base = Math.round(percent * 10.0) / 10.0;
                        long speed = (long) base;
                        long worldTime = world.getTime();

                        if (worldTime > 1000 && worldTime < 1150) {
                            data.setSleepTask(null);
                            this.cancel();
                        } else {
                            world.setTime(time.addAndGet(speed));

                            for (UUID sUUID : data.getSleepingPlayers()) {
                                OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(sUUID);
                                if (oPlayer.isOnline()) {
                                    Player sleepingPlayer = oPlayer.getPlayer();
                                    if (sleepingPlayer != null) {
                                        sleepingPlayer.showTitle(Title.title(Lang.SLEEPING_TITLE.getComponent(new String[] { plugin.getPU().formatTime(world.getTime()) }), Lang.SLEEPING_SUBTITLE.getComponent(new String[] { String.valueOf(plugin.getData().getSleepingPlayers().size()), String.valueOf(plugin.getPU().getOnlinePlayers().size()) }), times));
                                    } else data.removeSleepingPlayer(sUUID);
                                }
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0, 1));
            } else if (!plugin.getData().getSleepingPlayers().contains(uuid)) data.addSleepingPlayer(uuid);
        } else if (data.getSleepTask() == null && !world.isClearWeather() && world.isDayTime()) {
            e.setUseBed(Event.Result.ALLOW);
            world.setStorm(false);
            world.setThundering(false);
            player.showTitle(Title.title(Lang.SLEEPING_TITLE.getComponent(new String[] { plugin.getPU().formatTime(world.getTime()) }), Lang.SLEEPING_WEATHER_CLEARED_SUBTITLE.getComponent(null), weatherTimes));
        }
    }
    @EventHandler
    public void onPlayerSleep(PlayerBedLeaveEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        plugin.getData().removeSleepingPlayer(uuid);
        if (plugin.getData().getSleepingPlayers().isEmpty()) {
            if (plugin.getData().getSleepTask() != null) {
                Bukkit.getScheduler().cancelTask(plugin.getData().getSleepTask().getTaskId());
                plugin.getData().setSleepTask(null);
            }
        }
    }
}
