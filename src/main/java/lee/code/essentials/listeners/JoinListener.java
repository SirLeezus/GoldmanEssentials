package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.RankList;
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
            cache.setPlayerData(uuid, 0, "NOMAD", "n", RankList.NOMAD.getPrefix(), "n", "YELLOW", "0", "0",true);
        } else {
            //set custom attack speed
            AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            if (attribute != null) attribute.setBaseValue(23.4);
        }

        //register perms
        if (!player.isOp()) plugin.getPermissionManager().register(player);

        //update player display name
        plugin.getPU().updateDisplayName(player);

        //set join message format
        e.joinMessage(player.displayName().append(Lang.PLAYER_JOIN.getComponent(null)));
    }
}
