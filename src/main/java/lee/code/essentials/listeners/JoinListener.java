package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import lee.code.essentials.menusystem.menus.BotCheckerMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(PlayerLoginEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Cache cache = plugin.getCache();

        //player data check
        if (!cache.hasPlayerData(uuid)) cache.createDefaultColumns(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();
        Cache cache = plugin.getCache();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        //first time
        if (!player.hasPlayedBefore()) {
            cache.addPlayerCounter();
            Location spawn = cache.getSpawn();
            if (spawn != null) player.teleportAsync(spawn);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 25));
            Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.FIRST_JOIN_MESSAGE.getComponent(new String[] { player.getName(), String.valueOf(cache.getPlayerCounter()) })));
        }

        //bot checker - removed for now
//        if (!cache.hasBeenBotChecked(uuid)) {
//            new BotCheckerMenu(data.getPlayerMU(uuid)).open();
//            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
//            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
//            scheduler.runTaskLater(plugin, () -> {
//                if (!cache.hasBeenBotChecked(uuid)) player.kick(Lang.BOT_CHECKER_KICK.getComponent(null));
//            }, Settings.BOT_KICK_DELAY.getValue() * 20L);
//        }

        //booster bar check
        if (cache.isBoosterActive()) {
            player.showBossBar(pu.getBoosterBar());
        }

        //flying check
        if (cache.isFlying(uuid) && player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.ON.getString(null) })));
        }

        //ban check
        if (cache.isBanned(uuid)) {
            e.joinMessage(null);
            player.kick(Lang.BANNED.getComponent(new String[] { cache.getBanReason(uuid) }));
            return;
        } else if (cache.isTempBanned(uuid)) {
            e.joinMessage(null);
            long secondsLeft = cache.getTempBanTime(uuid);
            if (secondsLeft > 0) {
                player.kick(Lang.TEMPBANNED.getComponent(new String[] { pu.formatSeconds(secondsLeft), cache.getBanReason(uuid) }));
                return;
            } else cache.setTempBannedPlayer(uuid, null, "0", 0, false);

        }

        //set custom attack speed
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) attribute.setBaseValue(23.4);

        //register perms
        plugin.getPermissionManager().register(player);

        //vanish check
        if (cache.isVanishPlayer(uuid)) {
            if (!data.getVanishedPlayers().contains(uuid)) data.addVanishedPlayer(uuid);
            for (Player oPlayer : Bukkit.getOnlinePlayers()) if (!data.getVanishedPlayers().contains(oPlayer.getUniqueId())) oPlayer.hidePlayer(plugin, player);
        } else if (data.arePlayersVanished()) {
            for (UUID vUUID : data.getVanishedPlayers()) {
                OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(vUUID);
                if (oPlayer.isOnline()) {
                    Player vPlayer = oPlayer.getPlayer();
                    if (vPlayer != null) player.hidePlayer(plugin, vPlayer);
                }
            }
        }

        //give all recipes
        for (NamespacedKey key : data.getRecipeKeys()) e.getPlayer().discoverRecipe(key);

        //update player display name
        pu.updateDisplayName(player, false);

        //set join message format
        if (data.getVanishedPlayers().contains(uuid)) e.joinMessage(null);
        else e.joinMessage(player.displayName().append(Lang.PLAYER_JOIN.getComponent(null)));

        //playtime update
        cache.setPlayTime(uuid, player.getStatistic(Statistic.PLAY_ONE_MINUTE));

        //motd
        for (Component line : data.getServerMOTD()) player.sendMessage(line);
    }
}
