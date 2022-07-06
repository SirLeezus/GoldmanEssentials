package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            String worldName = player.getWorld().getName();
            Location location = player.getLocation();
            if (args.length > 0) {
                String world = args[0].toLowerCase();
                switch (world) {
                    case "resource_world" -> {
                        cacheManager.setWorldResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[]{ BukkitUtils.parseCapitalization(worldName), worldName })));
                    }
                    case "resource_nether" -> {
                        cacheManager.setNetherResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[]{ BukkitUtils.parseCapitalization(worldName), worldName })));
                    }
                    case "resource_end" -> {
                        cacheManager.setEndResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseCapitalization(worldName), worldName })));
                    }
                }
            } else {
                cacheManager.setSpawn(location);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_MAIN_SUCCESSFUL.getComponent(new String[] { worldName })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
