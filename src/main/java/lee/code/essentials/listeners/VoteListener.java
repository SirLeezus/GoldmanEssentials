package lee.code.essentials.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
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
        CacheManager cacheManager = plugin.getCacheManager();
        Vote vote = e.getVote();
        String user = vote.getUsername();

        if (!user.equalsIgnoreCase("pmc")) {
            Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.VOTE.getComponent(new String[] { user })));

            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(user);
            if (player != null) {
                UUID uuid = player.getUniqueId();
                cacheManager.deposit(uuid, 5000);
                cacheManager.addVote(uuid);
            }
        }
    }
}
