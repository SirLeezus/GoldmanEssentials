package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BanListCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        int index;
        int maxDisplayed = 10;
        int page = 0;

        //page check
        if (args.length > 0) {
            if (pu.containOnlyNumbers(args[0])) {
                page = Integer.parseInt(args[0]);
            } else {
                sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[2]} )));
                return true;
            }
        }

        if (page < 0) return true;

        //TODO change how date banned to long in sql
        List<String> banedPlayers = new ArrayList<>(cache.getBanList());
        HashMap<String, Long> banMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
        for (String player : banedPlayers) {
            try {
                Date date = formatter.parse(cache.getBanDate(UUID.fromString(player)));
                long dateTime = date.getTime();
                banMap.put(player, dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, Long> sortedMap = pu.sortByValue(banMap);
        List<String> players = new ArrayList<>(sortedMap.keySet());

        if (players.isEmpty()) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BANLIST_NO_BANS.getComponent(null)));
            return true;
        }

        List<Component> lines = new ArrayList<>();

        lines.add(Lang.COMMAND_BANLIST_TITLE.getComponent(null));
        lines.add(Component.text(""));

        for (int i = 0; i < maxDisplayed; i++) {
            index = maxDisplayed * page + i;
            if (index >= players.size()) break;
            if (players.get(index) != null) {
                int position = index + 1;
                String thePlayer = players.get(index);
                UUID pUUID = UUID.fromString(thePlayer);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pUUID);
                String name = "null";
                String staff = "&cConsole";
                String time = "&cForever";
                if (offlinePlayer.getName() != null) name = offlinePlayer.getName();
                long timeBanned = cache.getTempBanTime(pUUID);
                if (timeBanned < 0) timeBanned = 0;
                if (cache.isTempBanned(pUUID)) time = pu.formatSeconds(timeBanned);
                OfflinePlayer offlineStaff = Bukkit.getOfflinePlayer(cache.getStaffWhoPunished(pUUID));
                if (offlineStaff.getName() != null) staff = offlineStaff.getName();
                lines.add(pu.formatC("&3" + position + ". &6" + name + " &3Time: &7" + time).hoverEvent(pu.formatC("&3Date: &7" + cache.getBanDate(pUUID) + "\n&3Staff Member: &7" + staff + "\n&3Reason: &7" + cache.getBanReason(pUUID))));
            }
        }

        if (lines.size() <= 2) return true;

        lines.add(Component.text(""));
        Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/banlist " + (page + 1)));
        Component spacer = Lang.PAGE_SPACER.getComponent(null);
        Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/banlist " + (page - 1)));
        lines.add(prev.append(spacer).append(next));

        for (Component message : lines) sender.sendMessage(message);
        return true;
    }
}
