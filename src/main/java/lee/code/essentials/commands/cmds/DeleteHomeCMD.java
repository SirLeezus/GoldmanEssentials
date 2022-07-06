package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DeleteHomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = BukkitUtils.buildStringFromArgs(args, 0);
                if (!name.equalsIgnoreCase("bed")) {
                    if (cacheManager.isAlreadyHome(uuid, name)) {
                        cacheManager.removeHome(uuid, name);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DELETEHOME_SUCCESSFUL.getComponent(new String[] { name })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_DELETEHOME_NOT_SAVED.getComponent(new String[] { name })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_DELETEHOME_BED.getComponent(null)));
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
