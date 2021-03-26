package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler @SuppressWarnings("deprecation")
    public void onSignChange(SignChangeEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        String[] lines = e.getLines();
        int number = 0;
        for (String line : lines) {
            e.setLine(number, plugin.getPU().format(line));
            number++;
        }
    }
}
