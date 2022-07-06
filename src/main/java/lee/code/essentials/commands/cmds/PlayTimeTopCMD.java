package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayTimeTopCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            Map<UUID, Long> cPlayers = cacheManager.getTopPlayTime();
            HashMap<UUID, Long> sortedMap = pu.sortByLong(cPlayers);

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

            if (page < 0) return true;
            boolean onPage = false;
            int position = page * maxDisplayed + 1;

            List<UUID> players = new ArrayList<>(sortedMap.keySet());
            List<Component> lines = new ArrayList<>();

            lines.add(Lang.COMMAND_PLAYTIMETOP_TITLE.getComponent(null));
            lines.add(Component.text(""));

            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= players.size()) break;
                if (players.get(index) != null) {
                    UUID pUUID = players.get(index);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pUUID);
                    String posColor = "&3";
                    if (offlinePlayer.getName() != null) {
                        String name = offlinePlayer.getName();
                        String timePlayed = BukkitUtils.parseSeconds(sortedMap.get(pUUID) / 20);
                        char nameColor = cacheManager.getColor(pUUID).getChar();
                        if (name.equals(player.getName())) {
                            posColor = "&2";
                            onPage = true;
                        }
                        lines.add(BukkitUtils.parseColorComponent(posColor + position + ". &" + nameColor + name + " &7| " + timePlayed));
                        position++;
                    }
                }
            }

            if (lines.size() <= 2) return true;

            if (!onPage) {
                lines.add(Component.text(""));
                lines.add(BukkitUtils.parseColorComponent("&2" + (players.indexOf(uuid) + 1) + ". &" + cacheManager.getColor(uuid).getChar() + player.getName() + " &7| " + BukkitUtils.parseSeconds(sortedMap.get(uuid) / 20)));
            }

            lines.add(Component.text(""));
            Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/playtimetop " + (page + 1)));
            Component split = Lang.PAGE_SPACER.getComponent(null);
            Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/playtimetop " + (page - 1)));
            lines.add(prev.append(split).append(next));
            for (Component message : lines) player.sendMessage(message);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
