package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Emoji;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class EmojisCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            int maxRow = 20; int count = 1; int total = 1;
            Component componentBuilder = Component.empty();
            List<Component> lines = new ArrayList<>();

            for (Emoji emoji : Emoji.values()) {
                componentBuilder = componentBuilder
                        .append(Component.text(emoji.getUnicode() + " ")
                                .hoverEvent(BukkitUtils.parseColorComponent("&6:" + emoji.name().toLowerCase() + ":"))
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, ":" + emoji.name().toLowerCase() + ":"))
                                .append(Component.text()));
                if (count == maxRow || total == Emoji.values().length) {
                    lines.add(componentBuilder);
                    componentBuilder = Component.empty();
                    count = 0;
                }
                count++;
                total++;
            }

            List<Component> displayLines = new ArrayList<>();

            int index;
            int maxDisplayed = 10;
            int page = 0;

            //page check
            if (args.length > 0) {
                if (BukkitUtils.containOnlyNumbers(args[0])) {
                    page = Integer.parseInt(args[0]);
                } else {
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[0] } )));
                    return true;
                }
            }

            displayLines.add(Lang.COMMAND_EMOJI_TITLE.getComponent(null));
            displayLines.add(Component.text(""));

            if (page < 0) return true;

            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= lines.size()) break;
                if (lines.get(index) != null) {
                    displayLines.add(lines.get(index));
                }
            }

            displayLines.add(Component.text(""));
            Component next = Lang.EMOJIS_NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/emojis " + (page + 1)));
            Component split = Lang.PAGE_SPACER.getComponent(null);
            Component prev = Lang.EMOJIS_PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/emojis " + (page - 1)));
            displayLines.add(prev.append(split).append(next));

            for (Component line : displayLines) player.sendMessage(line);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
