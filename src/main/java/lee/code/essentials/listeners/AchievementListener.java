package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
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

        Advancement advancement = e.getAdvancement();
        Player player = e.getPlayer();

        if (player.getAdvancementProgress(advancement).isDone()) {
            String key = advancement.getKey().getKey();

            if (plugin.getData().getGameAdvancements().contains(key)) {
                cache.addLevel(e.getPlayer().getUniqueId());
                System.out.println("Point added! " + key);
            } else System.out.println("Point NOOOT added! " + key);
        }
    }
}
