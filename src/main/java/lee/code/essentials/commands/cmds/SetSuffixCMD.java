package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
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

public class SetSuffixCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (target != null) {
                UUID uuid = target.getUniqueId();
                String suffix = " " + BukkitUtils.buildStringFromArgs(args, 1);
                cacheManager.setSuffix(uuid, suffix);
                if (target.isOnline()) {
                    Player tPlayer = target.getPlayer();
                    if (tPlayer != null) pu.updateDisplayName(tPlayer, false);
                }
                sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETSUFFIX_SUCCESSFUL.getComponent(new String[]{ target.getName(), suffix })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
