package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;

public class TabListManager {

    public void scheduleTabListUpdater() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                plugin.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[] { String.valueOf(Bukkit.getOnlinePlayers().size() - plugin.getData().getVanishedPlayers().size()) }));
            }
        }, 10, 40);
    }
}
