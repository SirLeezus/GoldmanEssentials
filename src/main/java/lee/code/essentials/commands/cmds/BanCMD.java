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

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (targetPlayer != null) {
                    UUID tUUID = targetPlayer.getUniqueId();
                    cache.setBannedPlayer(tUUID, true);
                    if (targetPlayer.isOnline()) {
                        Player tPlayer = targetPlayer.getPlayer();
                        if (tPlayer != null) tPlayer.kick(Lang.BANNED_FOREVER.getComponent(null));
                    }
                    plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BANNED_FOREVER.getComponent(new String[] { targetPlayer.getName() })));
                }
            }
        }
        return true;
    }
}
