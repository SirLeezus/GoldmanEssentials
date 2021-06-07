package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.RankList;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Cache cache = plugin.getCache();

        //first time joining
        if (!cache.hasPlayerData(uuid)) {
            cache.setPlayerData(uuid, "0", "NOMAD", "n", RankList.NOMAD.getPrefix(), "n", "YELLOW", "0", "0", "0", "0", "0", true);
        }

        //set custom attack speed
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) attribute.setBaseValue(23.4);

        //register perms
        if (!player.isOp()) plugin.getPermissionManager().register(player);

        //update player display name
        plugin.getPU().updateDisplayName(player);

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

        //set join message format
        if (plugin.getData().getVanishedPlayers().contains(uuid)) e.joinMessage(null);
        else e.joinMessage(player.displayName().append(Lang.PLAYER_JOIN.getComponent(null)));
    }
}
