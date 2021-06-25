package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;

public class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        List<Component> lines = e.lines();
        int number = 0;
        for (Component line : lines) {
            e.line(number, plugin.getPU().formatC(plugin.getPU().unFormatC(line)));
            number++;
        }
    }
}
