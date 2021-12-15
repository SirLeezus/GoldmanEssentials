package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TempMuteCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        PU pu = plugin.getPU();

        if (args.length > 2) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (targetPlayer != null) {
                UUID tUUID = targetPlayer.getUniqueId();
                if (!cache.isTempMuted(tUUID) && !cache.isMuted(tUUID)) {
                    long secondsBanned = pu.unFormatSeconds(args[1]);
                    if (secondsBanned != 0) {
                        long milliseconds = System.currentTimeMillis();
                        long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + secondsBanned;
                        String reason = pu.buildStringFromArgs(args, 2).replaceAll("[^a-zA-Z0-9 ]", "");
                        if (!reason.isBlank()) {
                            cache.setTempMutedPlayer(tUUID, reason, time, true);
                            if (targetPlayer.isOnline()) {
                                Player tPlayer = targetPlayer.getPlayer();
                                if (tPlayer != null) tPlayer.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.TEMPMUTED.getComponent(new String[] { pu.formatSeconds(secondsBanned), reason })));
                            }
                            plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_TEMPMUTED.getComponent(new String[] { targetPlayer.getName(), pu.formatSeconds(secondsBanned), reason })));
                        }
                    }
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
