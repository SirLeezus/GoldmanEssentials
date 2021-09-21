package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommandSpam(PlayerCommandPreprocessEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!data.isSpamTaskActive(uuid)) {
            String help = e.getMessage().substring(0, 5);
            if (!help.equalsIgnoreCase("/help")) plugin.getPU().addSpamDelay(uuid);
        } else {
            e.setCancelled(true);
            plugin.getPU().addSpamDelay(uuid);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SPAM_COMMAND.getComponent(null)));
        }
        System.out.println(e.getMessage());
    }
}
