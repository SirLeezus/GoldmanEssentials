package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeleportDenyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target != player) {
                        if (plugin.getData().isPlayerRequestingTeleportForTarget(target.getUniqueId(), uuid)) {

                            plugin.getData().removePlayerRequestingTeleport(target.getUniqueId());
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_TELEPORT_DENY_SUCCESSFUL.getString(new String[] { target.getName() }));
                            target.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORTDENY_DENIED.getString(new String[] { player.getName() }));

                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_NOT_REQUESTING.getString(new String[] { target.getName() }));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getString(null));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { args[0] }));
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORTDENY_ARG.getString(null));
        }
        return true;
    }
}
