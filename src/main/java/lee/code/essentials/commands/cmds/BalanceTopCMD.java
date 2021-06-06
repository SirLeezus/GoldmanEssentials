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
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class BalanceTopCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Cache cache = plugin.getCache();

            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            Map<String, String> cPlayers = cache.getTopBalances();
            Map<String, Integer> intMap = cPlayers.entrySet().stream().collect(toMap(Map.Entry::getKey, entry -> Integer.parseInt(entry.getValue())));
            Map<String, Integer> sortedMap = intMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int index;
            int maxDisplayed = 10;
            int page = 0;

            //page check
            if (args.length > 0) {
                Scanner sellScanner = new Scanner(args[0]);
                if (sellScanner.hasNextInt()) {
                    page = Integer.parseInt(args[0]);
                } else {
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_BALANCETOP_LIST_PAGE.getString(new String[]{ args[2]} ));
                    return true;
                }
            }

            if (page < 0) return true;

            List<String> players = new ArrayList<>(sortedMap.keySet());
            List<String> line = new ArrayList<>();

            line.add(Lang.COMMAND_BALANCETOP_TITLE.getString(null));
            line.add("");

            boolean onPage = false;

            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= players.size()) break;
                if (players.get(index) != null) {
                    int position = index + 1;
                    String thePlayer = players.get(index);
                    UUID pUUID = UUID.fromString(thePlayer);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pUUID);
                    String name = "Notch";
                    String posColor = "&3";
                    if (offlinePlayer.getName() != null) name = offlinePlayer.getName();
                    String balance = plugin.getPU().formatAmount(sortedMap.get(thePlayer));
                    if (name.equals(player.getName())) {
                        posColor = "&2";
                        onPage = true;
                    }
                    line.add(plugin.getPU().format(posColor + position + ". &e" + name + " &7| &6$" + balance));
                }
            }

            if (line.size() <= 2) return true;

            if (!onPage) {
                line.add("");
                line.add(plugin.getPU().format("&2" + (players.indexOf(String.valueOf(uuid)) + 1) + ". &e" + player.getName() + " &7| &6$" + plugin.getPU().formatAmount(sortedMap.get(String.valueOf(uuid)))));
            }

            line.add("");
            Component nextPage = plugin.getPU().formatC("&2&lNext &a&l>>--------").hoverEvent(plugin.getPU().formatC("&6&lNext Page")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page + 1)));
            Component prevPage = plugin.getPU().formatC("&a&l--------<< &2&lPrev").hoverEvent(plugin.getPU().formatC("&6&lPrevious Page")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page - 1)));
            Component spacer = plugin.getPU().formatC(" &e| ");

            for (String message : line) player.sendMessage(message);
            player.sendMessage(prevPage.append(spacer).append(nextPage));
        }
        return true;
    }
}
