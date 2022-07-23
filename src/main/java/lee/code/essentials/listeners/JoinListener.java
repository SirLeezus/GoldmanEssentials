package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Setting;
import lee.code.essentials.managers.BoardManager;
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

import java.util.Map;
import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(PlayerLoginEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        CacheManager cacheManager = plugin.getCacheManager();

        //player data check
        if (!cacheManager.hasPlayerData(uuid)) cacheManager.createDefaultColumns(uuid);

        //ban check
        if (cacheManager.isBanned(uuid)) {
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            e.kickMessage(Lang.BANNED.getComponent(new String[] { cacheManager.getBanReason(uuid) }));
        } else if (cacheManager.isTempBanned(uuid)) {
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            long secondsLeft = cacheManager.getTempBanTime(uuid);
            if (secondsLeft > 0) {
                e.kickMessage(Lang.TEMPBANNED.getComponent(new String[] { BukkitUtils.parseSeconds(secondsLeft), cacheManager.getBanReason(uuid) }));
            } else cacheManager.setTempBannedPlayer(uuid, null, "0", 0);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();
        CacheManager cacheManager = plugin.getCacheManager();

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        //first time
        if (!player.hasPlayedBefore()) {
            cacheManager.addPlayerCounter();
            Location spawn = cacheManager.getSpawn();
            if (spawn != null) player.teleportAsync(spawn);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 25));
            Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.FIRST_JOIN_MESSAGE.getComponent(new String[] { player.getName(), String.valueOf(cacheManager.getPlayerCounter()) })));
        }

        //bot check
        if (cacheManager.isBotCheckEnabled() && cacheManager.isBot(uuid)) {
            new BotCheckerMenu(data.getPlayerMU(uuid)).open();
            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskLater(plugin, () -> {
                if (cacheManager.isBot(uuid)) player.kick(Lang.BOT_CHECKER_KICK.getComponent(null));
            }, Setting.BOT_KICK_DELAY.getValue() * 20L);
        }

        //booster bar check
        if (cacheManager.isBoosterActive()) {
            player.showBossBar(pu.getBoosterBar());
        }

        //flying check
        if (cacheManager.isFlying(uuid) && player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.ON.getString(null) })));
        }

        //set custom attack speed
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) attribute.setBaseValue(23.4);

        //register perms
        plugin.getPermissionManager().register(player);

        //vanish check
        if (cacheManager.isVanishPlayer(uuid)) {
            if (!data.getVanishedPlayers().contains(uuid)) data.addVanishedPlayer(uuid);
            for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                if (!data.getVanishedPlayers().contains(oPlayer.getUniqueId())) oPlayer.hidePlayer(plugin, player);
            }
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
        pu.updateDisplayName(player, false, true);
        for (BoardManager board : data.getBoardPackets()) board.sendPacket(player);

        //set join message format
        if (data.getVanishedPlayers().contains(uuid)) e.joinMessage(null);
        else e.joinMessage(player.displayName().append(Lang.PLAYER_JOIN.getComponent(null)));

        //playtime update
        cacheManager.setPlayTime(uuid, player.getStatistic(Statistic.PLAY_ONE_MINUTE));

        //motd
        for (Component line : data.getServerMOTD()) player.sendMessage(line);
    }
}
