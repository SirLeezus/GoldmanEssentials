package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null) {
                    if (oTarget.isOnline()) {
                        Player target = oTarget.getPlayer();
                        if (target != null) {
                            target.getInventory().clear();
                            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_CLEAR.getComponent(new String[] { target.getName() })));
                        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else {
                player.getInventory().clear();
                sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_CLEAR.getComponent(new String[] { player.getName() })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
