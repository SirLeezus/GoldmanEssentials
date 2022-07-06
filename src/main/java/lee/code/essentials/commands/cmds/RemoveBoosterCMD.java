package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveBoosterCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 0) {
            if (cacheManager.areBoosters()) {
                if (BukkitUtils.containOnlyNumbers(args[0])) {
                    int id = Integer.parseInt(args[0]);
                    if (cacheManager.getBoosterIDList().contains(id)) {
                        cacheManager.removeBooster(id);
                        Bukkit.getServer().hideBossBar(plugin.getPU().getBoosterBar());
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BOOSTER_REMOVE_SUCCESSFUL.getComponent(new String[] { String.valueOf(id) })));
                    }
                }
            }
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));

        return true;
    }
}
