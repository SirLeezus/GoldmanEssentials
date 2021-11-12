package lee.code.essentials.managers;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TabListManager {

    public void scheduleTabListUpdater() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                int lastPlayed = data.getLastBroadcast();
                List<Component> broadcasts = pu.getBroadcasts();
                Component play = broadcasts.get(lastPlayed);
                Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(new String[] { String.valueOf(pu.getOnlinePlayers().size()) }), Component.text("\n").append(play).append(Component.text("\n")));
                if (lastPlayed + 1 > pu.getBroadcasts().size() - 1) data.setLastBroadcast(0);
                else data.setLastBroadcast(lastPlayed + 1);
            }

        }, 10, 15 * 20);
    }

    public void updatePlayer(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(new String[] { String.valueOf(pu.getOnlinePlayers().size()) }), Component.text(""));
    }
}
