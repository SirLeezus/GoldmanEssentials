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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BanListCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {

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

            List<String> players = new ArrayList<>(cache.getBanList());
            List<String> line = new ArrayList<>();

            line.add(Lang.COMMAND_BANLIST_TITLE.getString(null));
            line.add("");

            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= players.size()) break;
                if (players.get(index) != null) {
                    int position = index + 1;
                    String thePlayer = players.get(index);
                    UUID pUUID = UUID.fromString(thePlayer);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pUUID);
                    String name = "null";
                    String time = "&cForever";
                    if (offlinePlayer.getName() != null) name = offlinePlayer.getName();
                    long milliseconds = System.currentTimeMillis();
                    long timeBanned = cache.getTempBanTime(pUUID) - TimeUnit.MILLISECONDS.toSeconds(milliseconds);
                    if (timeBanned < 0) timeBanned = 0;
                    if (cache.isTempBanned(pUUID)) time = plugin.getPU().formatSeconds(timeBanned);
                    line.add(plugin.getPU().format("&3" + position + ". &6" + name + " &7Time: &e" + time + " &7Reason: &e" + cache.getBanReason(pUUID)));
                }
            }

            if (line.size() <= 2) {
                if (players.isEmpty()) {
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_BANLIST_NO_BANS.getString(null));
                }
                return true;
            }

            line.add("");
            Component nextPage = plugin.getPU().formatC("&2&lNext &a&l>>---------").hoverEvent(plugin.getPU().formatC("&6&lNext Page")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/banlist " + (page + 1)));
            Component prevPage = plugin.getPU().formatC("&a&l---------<< &2&lPrev").hoverEvent(plugin.getPU().formatC("&6&lPrevious Page")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/banlist " + (page - 1)));
            Component spacer = plugin.getPU().formatC(" &e| ");

            for (String message : line) player.sendMessage(message);
            player.sendMessage(prevPage.append(spacer).append(nextPage));
        }
        return true;
    }
}
