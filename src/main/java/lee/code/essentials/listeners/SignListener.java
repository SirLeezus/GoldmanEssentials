package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;

public class SignListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        List<Component> lines = e.lines();
        Component line1 = lines.get(0);
        if (!plugin.getPU().unFormatC(line1).equals("[shop]") && !plugin.getPU().unFormatC(line1).equals("[lock]")) {
            int number = 0;
            for (Component line : lines) {
                e.line(number, plugin.getPU().formatC(plugin.getPU().unFormatC(line)));
                number++;
            }
        }
    }
}
