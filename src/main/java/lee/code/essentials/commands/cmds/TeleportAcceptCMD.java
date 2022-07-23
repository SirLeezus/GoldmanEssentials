package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeleportAcceptCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                if (BukkitUtils.getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target != player) {
                            if (data.isPlayerRequestingTeleportForTarget(target.getUniqueId(), uuid)) {
                                target.teleportAsync(player.getLocation()).thenAccept(result -> {
                                    data.removePlayerRequestingTeleport(target.getUniqueId());
                                    target.sendActionBar(Lang.TELEPORT.getComponent(null));
                                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ACCEPT_SUCCESSFUL.getComponent(new String[] { target.getName() })));
                                    target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ACCEPT_SUCCESSFUL_TARGET.getComponent(new String[] { player.getName() })));
                                });
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_NOT_REQUESTING.getComponent(new String[] { target.getName() })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getComponent(null)));
                    } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
