package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<Component> lines = new ArrayList<>();

        lines.add(Lang.COMMAND_COLORS_TITLE.getComponent(null));
        lines.add(Component.text(""));
        lines.add(Component.text("  §0&0  §1&1  §2&2  §3&3  §4&4"));
        lines.add(Component.text("  §5&5  §6&6  §7&7  §8&8  §9&9"));
        lines.add(Component.text("  §a&a  §b&b  §c&c  §d&d  §e&e"));
        lines.add(Component.text("  §f&f  &k§kl§r  §l&l§r  §m&m§r  §n&n§r"));
        lines.add(Component.text("  §o&o§r  §r&r"));

        lines.add(Component.text(""));
        lines.add(BukkitUtils.parseColorComponent("&#059CF8Hex Colors: ").append(Component.text("&#F500AE").color(TextColor.color(245, 0, 174))).hoverEvent(BukkitUtils.parseColorComponent("&#FFF667Click to open website!")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://htmlcolorcodes.com")));
        lines.add(Component.text(""));
        lines.add(Lang.COMMAND_COLORS_SPLITTER.getComponent(null));

        for (Component line : lines) sender.sendMessage(line);
        return true;
    }
}
