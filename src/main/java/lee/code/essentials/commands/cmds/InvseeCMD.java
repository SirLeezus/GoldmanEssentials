package lee.code.essentials.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class InvseeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null) {
                    if (oTarget.isOnline()) {
                        Player target = oTarget.getPlayer();
                        if (target != null) {
                            player.openInventory(target.getInventory());
                        }
                    }
                }
            }
        }
        return true;
    }
}
