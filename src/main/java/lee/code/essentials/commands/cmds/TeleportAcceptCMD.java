package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeleportAcceptCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            UUID uuid = player.getUniqueId();

            if (player.hasPermission("essentials.command.teleportaccept")) {

                if (args.length > 0) {
                    if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null && target != player) {
                            if (plugin.getData().isPlayerRequestingTeleportForTarget(target.getUniqueId(), uuid)) {

                                target.teleportAsync(player.getLocation());
                                target.sendActionBar(Lang.TELEPORT.getString(null));
                                target.playSound(target.getLocation(), Sound.UI_TOAST_OUT, 1,1);
                                plugin.getData().removePlayerRequestingTeleport(target.getUniqueId());

                                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_TELEPORT_ACCEPT_SUCCESSFUL.getString(new String[] { target.getName() }));
                            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_NOT_REQUESTING.getString(new String[] { target.getName() }));
                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getString(null));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { args[0] }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORTACCEPT_ARG.getString(null));
            }
        }
        return true;
    }
}