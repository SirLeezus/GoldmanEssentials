package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class HomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0 && !args[0].equals("page")) {
                if (!args[0].equalsIgnoreCase("bed")) {
                    String name = pu.buildStringFromArgs(args, 0);
                    if (cache.hasHome(uuid)) {
                        List<String> homes = cache.getHomes(uuid);
                        for (String home : homes) {
                            String homeName = pu.unFormatPlayerHomeName(home);
                            if (homeName.equals(name)) {
                                Location location = pu.unFormatPlayerHomeLocation(home);
                                player.teleportAsync(location);
                                player.sendActionBar(Lang.TELEPORT.getComponent(null));
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TELEPORT_SUCCESSFUL.getComponent(new String[] { name })));
                                return true;
                            }
                        }
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NOT_SAVED.getComponent(new String[] { name })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NO_SAVED_HOMES.getComponent(null)));
                } else {
                    Location bedLocation = player.getBedSpawnLocation();
                    if (bedLocation != null) {
                        player.teleportAsync(bedLocation);
                        player.sendActionBar(Lang.TELEPORT.getComponent(null));
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TELEPORT_BED_SUCCESSFUL.getComponent(null)));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_BED_NOT_SAVED.getComponent(null)));
                }
            } else {
                if (cache.hasHome(uuid)) {

                    List<String> homes = cache.getHomeNames(uuid);

                    int index;
                    int maxDisplayed = 5;
                    int page = 0;

                    if (args.length == 2) {
                        if (pu.containOnlyNumbers(args[1])) {
                            page = Integer.parseInt(args[1]);
                        } else {
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[0] } )));
                            return true;
                        }
                    }

                    if (page < 0) return true;
                    int position = page * maxDisplayed + 1;

                    List<Component> lines = new ArrayList<>();

                    lines.add(Lang.COMMAND_HOME_TITLE.getComponent(null));
                    lines.add(Component.text(""));

                    if (page == 0) {

                        int defaultHomes = Settings.DEFAULT_PLAYER_HOMES.getValue();
                        int maxHomes = pu.getMaxHomes(player);
                        int usedHomes = homes.size();
                        int accruedHomes = maxHomes - defaultHomes;

                        long time = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
                        String timePlayed = pu.formatSeconds(time);
                        String timeRequired = pu.formatSeconds(Settings.ACCRUED_HOME_BASE_TIME_REQUIRED.getValue());

                        lines.add(Lang.COMMAND_DEFAULT_HOMES.getComponent(new String[] { String.valueOf(defaultHomes) }));
                        lines.add(Component.text(""));

                        lines.add(Lang.COMMAND_HOME_TIME_PLAYED.getComponent(new String[] { timePlayed }));
                        lines.add(Lang.COMMAND_HOME_ACCRUED_TIMER.getComponent(new String[] { timeRequired }));
                        lines.add(Lang.COMMAND_HOME_ACCRUED_HOMES.getComponent(new String[] { String.valueOf(accruedHomes), String.valueOf(Settings.ACCRUED_HOME_MAX.getValue()) }));
                        lines.add(Component.text(""));

                        lines.add(Lang.COMMAND_HOME_TOTAL_HOMES.getComponent(new String[] { String.valueOf(usedHomes), String.valueOf(maxHomes) }));
                        lines.add(Component.text(""));
                    }

                    for (int i = 0; i < maxDisplayed; i++) {
                        index = maxDisplayed * page + i;
                        if (index >= homes.size()) break;
                        if (homes.get(index) != null) {
                            String name = homes.get(index);
                            lines.add(pu.formatC("&3" + position + ". &e" + name).hoverEvent(pu.formatC("&6&l" + name)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + name)));
                            position++;
                        }
                    }

                    if (lines.size() <= 2) return true;

                    lines.add(Component.text(""));
                    Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/home page " + (page + 1)));
                    Component split = Lang.PAGE_SPACER.getComponent(null);
                    Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/home page " + (page - 1)));
                    lines.add(prev.append(split).append(next));
                    for (Component line : lines) player.sendMessage(line);
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_NO_SAVED_HOMES.getString(null));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
