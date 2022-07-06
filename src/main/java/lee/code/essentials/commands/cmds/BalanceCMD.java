package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
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

public class BalanceCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            CacheManager cacheManager = GoldmanEssentials.getPlugin().getCacheManager();
            UUID uuid = player.getUniqueId();
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null) {
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BALANCE_TARGET_SUCCESSFUL.getComponent(new String[] { oTarget.getName(), BukkitUtils.parseValue(cacheManager.getBalance(oTarget.getUniqueId())) })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BALANCE_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseValue(cacheManager.getBalance(uuid)) })));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
