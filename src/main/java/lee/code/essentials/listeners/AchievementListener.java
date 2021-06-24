package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AchievementListener implements Listener {

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Component am = e.message();
        if (am != null) plugin.getServer().sendMessage(Lang.ADVANCEMENT_PREFIX.getComponent(null).append(am).append(plugin.getPU().formatC("&2!")).color(NamedTextColor.DARK_GREEN));

        Advancement advancement = e.getAdvancement();
        Player player = e.getPlayer();

        if (player.getAdvancementProgress(advancement).isDone()) {
            String key = advancement.getKey().getKey();
            if (plugin.getData().getAdvancementNames().contains(key)) {
                cache.addLevel(e.getPlayer().getUniqueId());
            }
        }
    }
}
