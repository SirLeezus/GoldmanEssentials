package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ServerSendMessageCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (args.length > 0) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (sender instanceof Player) {
                if (targetPlayer != null) {
                    if (args.length > 1) {
                        String message = plugin.getPU().buildStringFromArgs(args, 1);
                        if (targetPlayer.isOnline()) {
                            Player tPlayer = targetPlayer.getPlayer();
                            if (tPlayer != null) tPlayer.sendMessage(plugin.getPU().formatC(message));
                        }
                    }
                }
            } else if (sender instanceof ConsoleCommandSender) {
                if (targetPlayer != null) {
                    if (args.length > 1) {
                        String message = plugin.getPU().buildStringFromArgs(args, 1);
                        if (targetPlayer.isOnline()) {
                            Player tPlayer = targetPlayer.getPlayer();
                            if (tPlayer != null) tPlayer.sendMessage(plugin.getPU().formatC(message));
                        }
                    }
                }
            }
        }
        return true;
    }
}
