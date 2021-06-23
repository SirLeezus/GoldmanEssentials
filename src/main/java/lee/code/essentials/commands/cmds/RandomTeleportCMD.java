package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomTeleportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            Player target = player;
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null && oTarget.isOnline()) target = oTarget.getPlayer();
            }
            if (target != null) plugin.getPU().rtpPlayer(target);

        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null && oTarget.isOnline()) {
                    Player target = oTarget.getPlayer();
                    if (target != null) plugin.getPU().rtpPlayer(target);
                }
            }
        }
        return true;
    }
}
