package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.menus.HomeMenu;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = BukkitUtils.buildStringFromArgs(args, 0);
                if (!name.equalsIgnoreCase("bed")) {
                    if (cacheManager.hasHome(uuid)) {
                        Location homeLocation = cacheManager.getHome(uuid, name);
                        if (homeLocation != null) {
                            player.teleportAsync(homeLocation);
                            player.sendActionBar(Lang.TELEPORT.getComponent(null));
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TELEPORT_SUCCESSFUL.getComponent(new String[] { name })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NOT_SAVED.getComponent(new String[] { name })));
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
                if (cacheManager.hasHome(uuid)) {
                    new HomeMenu(data.getPlayerMU(uuid)).open();
                    player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NO_SAVED_HOMES.getComponent(null)));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
