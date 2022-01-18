package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
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
        PU pu = plugin.getPU();
        List<Component> lines = e.lines();
        Component line1 = lines.get(0);
        String sLine1 = pu.unFormatC(line1);
        if (!sLine1.equals("[shop]") && !sLine1.equals("[lock]")) {
            int number = 0;
            for (Component line : lines) {
                e.line(number, plugin.getPU().formatC(plugin.getPU().unFormatC(line)));
                number++;
            }
        }
    }
}
