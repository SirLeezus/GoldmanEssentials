package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookListener implements Listener {

    @EventHandler @SuppressWarnings("deprecation")
    public void onBookSign(PlayerEditBookEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        // Adventure API does not work correctly with books
        if (e.isSigning()) {
            BookMeta meta = e.getNewBookMeta();
            List<String> pages = meta.getPages();
            String title = meta.getTitle();
            if (title != null) meta.setTitle(plugin.getPU().format(title));
            int number = 1;
            for (String page : pages) {
                meta.setPage(number, plugin.getPU().format(page));
                number++;
            }
        }
    }
}
