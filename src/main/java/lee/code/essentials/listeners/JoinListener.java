package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import lee.code.essentials.menusystem.menus.BotCheckerMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Cache cache = plugin.getCache();

        //player counter
        if (!player.hasPlayedBefore()) {
            cache.addPlayerCounter();
            plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.FIRST_JOIN_MESSAGE.getComponent(new String[] { player.getName(), String.valueOf(cache.getPlayerCounter()) })));
        }

        //bot checker
        if (!cache.hasBeenBotChecked(uuid)) {
            new BotCheckerMenu(plugin.getData().getPlayerMU(uuid)).open();
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

            scheduler.runTaskLater(plugin, () -> {
                if (!cache.hasBeenBotChecked(uuid)) player.kick(Lang.BOT_CHECKER_KICK.getComponent(null));
            }, Settings.BOT_KICK_DELAY.getValue() * 20L);
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
                player.kick(Lang.TEMPBANNED.getComponent(new String[] { plugin.getPU().formatSeconds(secondsLeft), cache.getBanReason(uuid) }));
                return;
            } else cache.setTempBannedPlayer(uuid, null, "0", 0, false);

        }

        //set custom attack speed
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) attribute.setBaseValue(23.4);

        //register perms
        if (!player.isOp()) plugin.getPermissionManager().register(player);

        //vanish check
        if (cache.isVanishPlayer(uuid)) {
            if (!plugin.getData().getVanishedPlayers().contains(uuid)) plugin.getData().addVanishedPlayer(uuid);
            for (Player oPlayer : Bukkit.getOnlinePlayers()) if (!plugin.getData().getVanishedPlayers().contains(oPlayer.getUniqueId())) oPlayer.hidePlayer(plugin, player);
        } else if (plugin.getData().arePlayersVanished()) {
            for (UUID vUUID : plugin.getData().getVanishedPlayers()) {
                OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(vUUID);
                if (oPlayer.isOnline()) {
                    Player vPlayer = oPlayer.getPlayer();
                    if (vPlayer != null) player.hidePlayer(plugin, vPlayer);
                }
            }
        }

        //give all recipes
        for (NamespacedKey key : plugin.getData().getRecipeKeys()) e.getPlayer().discoverRecipe(key);

        //update player display name
        plugin.getPU().updateDisplayName(player);

        //set join message format
        if (plugin.getData().getVanishedPlayers().contains(uuid)) e.joinMessage(null);
        else e.joinMessage(player.displayName().append(Lang.PLAYER_JOIN.getComponent(null)));

        //motd
        for (Component line : plugin.getData().getServerMOTD()) player.sendMessage(line);
    }
}
