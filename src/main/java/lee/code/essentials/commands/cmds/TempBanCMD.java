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

        if (args.length > 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            UUID senderUUID = sender instanceof Player player ? player.getUniqueId() : UUID.fromString(Lang.SERVER_UUID.getString());
            if (target != null) {
                UUID tUUID = target.getUniqueId();
                if (!cache.isTempBanned(tUUID) && !cache.isBanned(tUUID)) {
                    long secondsBanned = plugin.getPU().unFormatSeconds(args[1]);
                    if (secondsBanned != 0) {
                        long milliseconds = System.currentTimeMillis();
                        long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + secondsBanned;
                        String reason = plugin.getPU().buildStringFromArgs(args, 2).replaceAll("[^a-zA-Z0-9 ]", "");
                        if (!reason.isBlank()) {
                            cache.setTempBannedPlayer(tUUID, senderUUID, reason, time, true);
                            if (target.isOnline()) {
                                Player tPlayer = target.getPlayer();
                                if (tPlayer != null) tPlayer.kick(Lang.TEMPBANNED.getComponent(new String[] { plugin.getPU().formatSeconds(secondsBanned), reason }));
                            }
                            plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_TEMPBANNED.getComponent(new String[] { target.getName(), plugin.getPU().formatSeconds(secondsBanned), reason })));
                        }
                    }
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
