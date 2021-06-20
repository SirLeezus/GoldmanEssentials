package lee.code.essentials.commands.cmds;

import org.bukkit.Bukkit;
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

            //TODO better check for online player
            if (args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);

                if (!player.equals(target)) {
                    PlayerInventory targetInv = target.getInventory();
                    player.openInventory(targetInv);
                    //TODO send message
                }
            }
        }
        return true;
    }
}
