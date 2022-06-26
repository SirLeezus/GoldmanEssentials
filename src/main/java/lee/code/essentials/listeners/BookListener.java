package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookListener implements Listener {

    @EventHandler
    public void onBookSign(PlayerEditBookEvent e) {
        if (e.isSigning()) {
            BookMeta meta = e.getNewBookMeta();
            List<Component> pages = meta.pages();
            if (meta.hasTitle()) meta.title(BukkitUtils.parseColorComponent(meta.getTitle()));
            int number = 1;
            for (Component page : pages) {
                Component newPage = BukkitUtils.parseColorComponent(BukkitUtils.serializeComponent(GoldmanEssentials.getPlugin().getPU().parseVariables(page)));
                meta.page(number, newPage);
                number++;
            }
            e.setNewBookMeta(meta);
        }
    }
}
