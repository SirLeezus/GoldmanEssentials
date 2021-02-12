package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.Lang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeleportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();

            if (player.hasPermission("essentials.command.teleport")) {


                if (args.length > 0) {
                    if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null && target != player) {

                            if (!plugin.getData().isPlayerRequestingTeleportForTarget(uuid, target.getUniqueId())) {

                                TextComponent targetMessage = new TextComponent(plugin.getPU().format("&6&l[&e&l!&6&l] &ePlayer &6&l" + player.getName() +  " &eis requesting teleportation: "));

                                TextComponent confirmTeleport = new TextComponent(plugin.getPU().format("                           &a&l[&2&lACCEPT&a&l]          "));
                                confirmTeleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName()));
                                confirmTeleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(plugin.getPU().format("&6&l[&e&l!&6&l] &2Click to accept &6&l" + player.getName() + "'s &2teleport request."))));

                                TextComponent denyTeleport = new TextComponent(plugin.getPU().format("&4&l[&c&lDENY&4&l]"));
                                denyTeleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + player.getName()));
                                denyTeleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(plugin.getPU().format("&6&l[&e&l!&6&l] &cClick to deny &6&l" + player.getName() + "'s &cteleport request."))));

                                plugin.getData().setPlayerRequestingTeleport(uuid, target.getUniqueId());

                                target.spigot().sendMessage(targetMessage, confirmTeleport, denyTeleport);
                                target.playSound(target.getLocation(), Sound.UI_TOAST_IN, 1,1);
                                plugin.getPU().teleportTimer(player, target);
                                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_TELEPORT_REQUEST_SUCCESSFUL.getString(new String[] { target.getName() }));

                            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED.getString(new String[] { target.getName() }));
                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_TO_SELF.getString(null));
                    }
                }
            }
        }
        return true;
    }
}
