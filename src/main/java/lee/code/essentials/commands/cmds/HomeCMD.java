package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                if (!args[0].equals("bed")) {
                    String name = plugin.getPU().buildStringFromArgs(args, 0);
                    List<String> homes = cache.getHomes(uuid);
                    for (String home : homes) {
                        String homeName = plugin.getPU().unFormatPlayerHomeName(home);
                        if (homeName.equals(name)) {
                            Location location = plugin.getPU().unFormatPlayerHomeLocation(home);
                            player.teleportAsync(location);
                            player.sendActionBar(Lang.TELEPORT.getComponent(null));
                            //player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1,1);
                            player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_HOME_TELEPORT_SUCCESSFUL.getString(new String[] { name }));
                            return true;
                        }
                    }
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_NOT_SAVED.getString(new String[] { name }));
                } else {
                    Location bedLocation = player.getBedSpawnLocation();
                    if (bedLocation != null) {
                        player.teleportAsync(bedLocation);
                        player.sendActionBar(Lang.TELEPORT.getComponent(null));
                        //player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1,1);
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_HOME_TELEPORT_BED_SUCCESSFUL.getString(null));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_BED_NOT_SAVED.getString(null));
                }
            } else {
                if (cache.hasHome(uuid)) {
                    int count = 1;
                    List<Component> lines = new ArrayList<>();
                    List<String> names = cache.getHomeNames(uuid);

                    lines.add(Lang.COMMAND_HOME_TITLE.getComponent(null));
                    lines.add(plugin.getPU().formatC(""));

                    Location bedLocation = player.getBedSpawnLocation();
                    if (bedLocation != null) {
                        lines.add(plugin.getPU().formatC("&c╔═╗ Bed").hoverEvent(plugin.getPU().formatC("&c&lBed")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/home bed")));
                        lines.add(plugin.getPU().formatC(""));
                    }

                    for (String name : names) {
                        lines.add(plugin.getPU().formatC("&3" + count + ". &e" + name).hoverEvent(plugin.getPU().formatC("&6&l" + name)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + name)));
                        count++;
                    }
                    lines.add(plugin.getPU().formatC(""));
                    lines.add(Lang.COMMAND_HOME_SPLITTER.getComponent(null));

                    for (Component line : lines) player.sendMessage(line);
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_NO_SAVED_HOMES.getString(null));
            }
        }
        return true;
    }
}
