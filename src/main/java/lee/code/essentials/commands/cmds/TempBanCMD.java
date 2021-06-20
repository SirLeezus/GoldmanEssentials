package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
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

public class TempBanCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        //tempban (player) {time} {reason}
        if (sender instanceof Player) {
            if (args.length > 0) {
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (targetPlayer != null) {
                    UUID tUUID = targetPlayer.getUniqueId();
                    if (args.length > 1) {
                        if (!cache.isTempBanned(tUUID) && !cache.isBanned(tUUID)) {
                            long secondsBanned = plugin.getPU().unFormatSeconds(args[1]);
                            if (secondsBanned != 0) {
                                long milliseconds = System.currentTimeMillis();
                                long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + secondsBanned;
                                if (args.length > 2) {
                                    String reason = plugin.getPU().buildStringFromArgs(args, 2).replaceAll("[^a-zA-Z0-9 ]", "");
                                    if (!reason.isBlank()) {
                                        cache.setTempBannedPlayer(tUUID, reason, time);
                                        cache.addBanList(tUUID);
                                        if (targetPlayer.isOnline()) {
                                            Player tPlayer = targetPlayer.getPlayer();
                                            if (tPlayer != null) tPlayer.kick(Lang.TEMPBANNED.getComponent(new String[] { plugin.getPU().formatSeconds(secondsBanned), reason }));
                                        }
                                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_TEMPBANNED_FOREVER.getComponent(new String[] { targetPlayer.getName(), plugin.getPU().formatSeconds(secondsBanned), reason })));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
