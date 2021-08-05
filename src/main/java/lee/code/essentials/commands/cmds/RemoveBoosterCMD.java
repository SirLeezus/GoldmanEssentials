package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Scanner;

public class RemoveBoosterCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (args.length > 0) {
            if (cache.areBoosters()) {
                Scanner numberScanner = new Scanner(args[0]);
                if (numberScanner.hasNextInt()) {
                    String id = args[0];
                    List<String> ids = cache.getBoosterIDStringList();
                    if (ids.contains(id)) {
                        cache.removeBooster(id);
                        Bukkit.getServer().hideBossBar(plugin.getPU().getBoosterBar());
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BOOSTER_REMOVE_SUCCESSFUL.getComponent(new String[] { id })));
                    }
                }
            }
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));

        return true;
    }
}
