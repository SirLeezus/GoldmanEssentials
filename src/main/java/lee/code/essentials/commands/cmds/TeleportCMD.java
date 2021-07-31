package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeleportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target != player) {
                            if (player.isOp()) {
                                player.teleportAsync(target.getLocation());
                                target.sendActionBar(Lang.TELEPORT.getComponent(null));
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ADMIN_SUCCESSFUL.getComponent(new String[] { target.getName() })));

                            } else if (!plugin.getData().isPlayerRequestingTeleportForTarget(uuid, target.getUniqueId())) {
                                Component targetMessage = Lang.REQUEST_TELEPORT_TARGET.getComponent(new String[] { player.getName() });

                                Component accept = Lang.REQUEST_TELEPORT_ACCEPT.getComponent(null).hoverEvent(Lang.REQUEST_TELEPORT_ACCEPT_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName()));
                                Component deny = Lang.REQUEST_TELEPORT_DENY.getComponent(null).hoverEvent(Lang.REQUEST_TELEPORT_DENY_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + player.getName()));

                                plugin.getData().setPlayerRequestingTeleport(uuid, target.getUniqueId());

                                target.sendMessage(targetMessage.append(accept).append(deny));
                                plugin.getPU().teleportTimer(player, target);
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_REQUEST_SUCCESSFUL.getComponent(new String[] { target.getName() })));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED.getComponent(new String[] { target.getName() })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getComponent(null)));
                    } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
