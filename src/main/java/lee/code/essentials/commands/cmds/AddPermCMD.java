package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AddPermCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 1) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (targetPlayer != null) {
                UUID tUUID = targetPlayer.getUniqueId();
                String name = targetPlayer.getName();
                String perm = args[1];
                if (!cacheManager.hasPerm(tUUID, perm)) {
                    cacheManager.addPerm(tUUID, perm);
                    if (targetPlayer.isOnline()) {
                        Player tPlayer = targetPlayer.getPlayer();
                        if (tPlayer != null && !tPlayer.isOp()) plugin.getPermissionManager().register(tPlayer);
                    }
                    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ADDPERM_SUCCESSFUL.getComponent(new String[] { perm, name })));
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ADDPERM_HAS_PERM.getComponent(new String[] { name, perm })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
