package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckPermsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 0) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (targetPlayer != null) {
                UUID tUUID = targetPlayer.getUniqueId();

                int index;
                int maxDisplayed = 15;
                int page = 0;

                if (args.length > 1) {
                    if (BukkitUtils.containOnlyNumbers(args[1])) {
                        page = Integer.parseInt(args[1]);
                    } else {
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[0] } )));
                        return true;
                    }
                }

                if (page < 0) return true;

                int position = page * maxDisplayed + 1;

                List<String> perms = cacheManager.getPerms(tUUID);
                List<Component> lines = new ArrayList<>();

                lines.add(Lang.COMMAND_CHECKPERMS_TITLE.getComponent(null));
                lines.add(Component.text(""));

                for (int i = 0; i < maxDisplayed; i++) {
                    index = maxDisplayed * page + i;
                    if (index >= perms.size()) break;
                    if (perms.get(index) != null) {
                        String perm = perms.get(index);
                        lines.add(Lang.COMMAND_CHECKPERMS_LINE.getComponent(new String[] { String.valueOf(position), perm }));
                        position++;
                    }
                }

                if (lines.size() <= 2) return true;

                lines.add(Component.text(""));
                Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/checkperms " + targetPlayer.getName() + " " + (page + 1)));
                Component split = Lang.PAGE_SPACER.getComponent(null);
                Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/checkperms " + targetPlayer.getName() + " " + (page - 1)));
                lines.add(prev.append(split).append(next));
                for (Component message : lines) sender.sendMessage(message);

            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
