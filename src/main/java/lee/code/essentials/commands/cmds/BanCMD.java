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

public class BanCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (targetPlayer != null) {
                    UUID tUUID = targetPlayer.getUniqueId();
                    if (args.length > 1) {
                        if (!cache.isTempBanned(tUUID) && !cache.isBanned(tUUID)) {
                            String reason = plugin.getPU().buildStringFromArgs(args, 1).replaceAll("[^a-zA-Z0-9 ]", "");
                            if (!reason.isBlank()) {
                                cache.setBannedPlayer(tUUID, player.getUniqueId(), reason, true);
                                if (targetPlayer.isOnline()) {
                                    Player tPlayer = targetPlayer.getPlayer();
                                    if (tPlayer != null) tPlayer.kick(Lang.BANNED.getComponent(new String[] { reason }));
                                }
                                plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BANNED_FOREVER.getComponent(new String[] { targetPlayer.getName(), reason })));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
