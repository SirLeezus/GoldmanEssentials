package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.UUID;

public class AchievementListener implements Listener {

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        UUID uuid = e.getPlayer().getUniqueId();

        Advancement advancement = e.getAdvancement();
        Player player = e.getPlayer();
        Component am = e.message();
        if (am != null && !cacheManager.isVanishPlayer(uuid)) plugin.getServer().sendMessage(Lang.ADVANCEMENT_PREFIX.getComponent(null).append(am).append(BukkitUtils.parseColorComponent("&2!")).color(NamedTextColor.DARK_GREEN));

        if (player.getAdvancementProgress(advancement).isDone()) {
            String key = advancement.getKey().getKey();
            if (plugin.getData().getAdvancementNames().contains(key)) {
                cacheManager.addLevel(e.getPlayer().getUniqueId());
            }
        }
    }
}
