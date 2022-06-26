package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
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
        List<Component> lines = e.lines();
        Component line1 = lines.get(0);
        String sLine1 = BukkitUtils.serializeComponent(line1);
        if (!sLine1.equals("[shop]") && !sLine1.equals("[lock]")) {
            int number = 0;
            for (Component line : lines) {
                Component newLine = BukkitUtils.parseColorComponent(BukkitUtils.serializeComponent(line));
                e.line(number, GoldmanEssentials.getPlugin().getPU().parseVariables(newLine));
                number++;
            }
        }
    }
}
