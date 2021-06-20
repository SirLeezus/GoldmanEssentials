package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KickCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (targetPlayer != null) {
                    if (args.length > 1) {
                        String reason = plugin.getPU().buildStringFromArgs(args, 1).replaceAll("[^a-zA-Z0-9 ]", "");
                        if (!reason.isBlank()) {
                            if (targetPlayer.isOnline()) {
                                Player tPlayer = targetPlayer.getPlayer();
                                if (tPlayer != null) tPlayer.kick(Lang.KICKED.getComponent(new String[] { reason }));
                                plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_KICKED.getComponent(new String[] { targetPlayer.getName(), reason })));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
