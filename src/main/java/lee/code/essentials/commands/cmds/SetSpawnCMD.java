package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
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
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            String worldName = player.getWorld().getName();
            Location location = player.getLocation();
            if (args.length > 0) {
                String world = args[0].toLowerCase();
                switch (world) {
                    case "resource_world" -> {
                        cache.setWorldResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[]{pu.formatCapitalization(worldName), worldName})));
                    }
                    case "resource_nether" -> {
                        cache.setNetherResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[]{pu.formatCapitalization(worldName), worldName})));
                    }
                    case "resource_end" -> {
                        cache.setEndResourceSpawn(location);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL.getComponent(new String[] { pu.formatCapitalization(worldName), worldName })));
                    }
                }
            } else {
                cache.setSpawn(location);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSPAWN_MAIN_SUCCESSFUL.getComponent(new String[] { worldName })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
