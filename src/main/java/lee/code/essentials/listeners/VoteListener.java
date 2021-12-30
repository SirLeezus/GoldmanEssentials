package lee.code.essentials.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class VoteListener implements Listener {

    @EventHandler
    public void onVote(VotifierEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Vote vote = e.getVote();
        String user = vote.getUsername();

        Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.VOTE.getComponent(new String[] { user })));

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(user);
        if (player != null) {
            UUID uuid = player.getUniqueId();
            cache.deposit(uuid, 10000);
            cache.addVote(uuid);
        }
    }
}
