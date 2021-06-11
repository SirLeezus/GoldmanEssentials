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

public class UnMuteCMD implements CommandExecutor {

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
                    if (cache.isMuted(tUUID)) {
                        cache.setMutedPlayer(tUUID, "0", false);
                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_UNMUTED.getComponent(new String[] { targetPlayer.getName() })));
                    }
                }
            }
        }
        return true;
    }
}
