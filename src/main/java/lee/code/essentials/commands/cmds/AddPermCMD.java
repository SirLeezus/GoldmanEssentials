package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AddPermCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        //addperm {player} {perm}
        if (args.length > 0) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (sender instanceof Player player) {
                if (targetPlayer != null) {
                    UUID tUUID = targetPlayer.getUniqueId();
                    String name = targetPlayer.getName();
                    if (args.length > 1) {
                        String perm = args[1];
                        if (!cache.hasPerms(tUUID, perm)) {
                            cache.addPerm(tUUID, perm);
                            if (targetPlayer.isOnline()) {
                                Player tPlayer = targetPlayer.getPlayer();
                                if (tPlayer != null && !tPlayer.isOp()) plugin.getPermissionManager().register(tPlayer);
                            }
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ADDPERM_SUCCESSFUL.getComponent(new String[] { perm, name })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ADDPERM_HAS_PERM.getComponent(new String[] { name, perm })));
                    }
                }
            } else if (sender instanceof ConsoleCommandSender console) {
                if (targetPlayer != null) {
                    UUID tUUID = targetPlayer.getUniqueId();
                    String name = targetPlayer.getName();
                    if (args.length > 1) {
                        String perm = args[1];
                        if (!cache.hasPerms(tUUID, perm)) {
                            cache.addPerm(tUUID, perm);
                            if (targetPlayer.isOnline()) {
                                Player tPlayer = targetPlayer.getPlayer();
                                if (tPlayer != null && !tPlayer.isOp()) plugin.getPermissionManager().register(tPlayer);
                            }
                            console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ADDPERM_SUCCESSFUL.getComponent(new String[] { perm, name })));
                        } else console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ADDPERM_HAS_PERM.getComponent(new String[] { name, perm })));
                    }
                }
            }
        }
        return true;
    }
}
