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

        if (sender instanceof Player) {
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target != player) {

                        if (player.isOp()) {
                            player.teleportAsync(target.getLocation());
                            player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_TELEPORT_ADMIN_SUCCESSFUL.getString(new String[] { target.getName() }));
                        } else if (!plugin.getData().isPlayerRequestingTeleportForTarget(uuid, target.getUniqueId())) {

                            Component targetMessage = plugin.getPU().formatC("&6&l[&e&l!&6&l] &ePlayer &6&l" + player.getName() +  " &eis requesting teleportation: ");

                            Component accept = plugin.getPU().formatC("                          &a&l[&2&lACCEPT&a&l]          ").hoverEvent(plugin.getPU().formatC("&6&l[&e&l!&6&l] &2Click to accept &6&l" + player.getName() + "'s &2teleport request.")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName()));
                            Component deny = plugin.getPU().formatC("&4&l[&c&lDENY&4&l]").hoverEvent(plugin.getPU().formatC("&6&l[&e&l!&6&l] &cClick to deny &6&l" + player.getName() + "'s &cteleport request.")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + player.getName()));

                            plugin.getData().setPlayerRequestingTeleport(uuid, target.getUniqueId());

                            target.sendMessage(targetMessage.append(accept).append(deny));
                            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
                            plugin.getPU().teleportTimer(player, target);
                            player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_TELEPORT_REQUEST_SUCCESSFUL.getString(new String[] { target.getName() }));

                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED.getString(new String[] { target.getName() }));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getString(null));
                }
            }
        }
        return true;
    }
}
