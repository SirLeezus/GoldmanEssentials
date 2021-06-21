package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            List<String> lines = new ArrayList<>();

            lines.add(Lang.COMMAND_COLORS_TITLE.getString(null));
            lines.add("");
            lines.add("  §0&0  §1&1  §2&2  §3&3  §4&4");
            lines.add("  §5&5  §6&6  §7&7  §8&8  §9&9");
            lines.add("  §a&a  §b&b  §c&c  §d&d  §e&e");
            lines.add("  §f&f  §k&k§r  §l&l§r  §m&m§r  §n&n§r");
            lines.add("  §o&o§r  §r&r");
            for (String line : lines) player.sendMessage(line);
            player.sendMessage("");
            player.sendMessage(plugin.getPU().formatC("&#059CF8Hex Colors: &#F86105" + "#F86105").hoverEvent(plugin.getPU().formatC("&#FFF667Click to open website!")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://htmlcolorcodes.com")));
            player.sendMessage("");
            player.sendMessage(Lang.COMMAND_COLORS_SPLITTER.getString(null));
        }
        return true;
    }
}
