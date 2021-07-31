package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
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

import static java.util.stream.Collectors.toMap;

public class BalanceTopCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            Map<String, String> cPlayers = cache.getTopBalances();
            Map<String, Long> intMap = cPlayers.entrySet().stream().collect(toMap(Map.Entry::getKey, entry -> Long.parseLong(entry.getValue())));
            Map<String, Long> sortedMap = intMap.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int index;
            int maxDisplayed = 10;
            int page = 0;

            //page check
            if (args.length > 0) {
                Scanner sellScanner = new Scanner(args[0]);
                if (sellScanner.hasNextInt()) {
                    page = Integer.parseInt(args[0]);
                } else {
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[2]} )));
                    return true;
                }
            }

            if (page < 0) return true;

            List<String> players = new ArrayList<>(sortedMap.keySet());
            List<Component> lines = new ArrayList<>();

            lines.add(Lang.COMMAND_BALANCETOP_TITLE.getComponent(null));
            lines.add(Component.text(""));

            boolean onPage = false;
            int position = 1;

            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= players.size()) break;
                if (players.get(index) != null) {
                    String thePlayer = players.get(index);
                    UUID pUUID = UUID.fromString(thePlayer);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pUUID);
                    String posColor = "&3";
                    if (offlinePlayer.getName() != null) {
                        String name = offlinePlayer.getName();
                        String balance = plugin.getPU().formatAmount(sortedMap.get(thePlayer));
                        if (name.equals(player.getName())) {
                            posColor = "&2";
                            onPage = true;
                        }
                        lines.add(plugin.getPU().formatC(posColor + position + ". &e" + name + " &7| &6$" + balance));
                        position++;
                    }
                }
            }

            if (lines.size() <= 2) return true;

            if (!onPage) {
                lines.add(Component.text(""));
                lines.add(plugin.getPU().formatC("&2" + (players.indexOf(String.valueOf(uuid)) + 1) + ". &e" + player.getName() + " &7| &6$" + plugin.getPU().formatAmount(sortedMap.get(String.valueOf(uuid)))));
            }

            lines.add(Component.text(""));
            Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page + 1)));
            Component split = Lang.PAGE_SPACER.getComponent(null);
            Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page - 1)));
            lines.add(prev.append(split).append(next));
            for (Component message : lines) player.sendMessage(message);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
