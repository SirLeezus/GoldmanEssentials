package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MessageCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 1) {
                if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target != player) {
                        if (!cache.isMuted(uuid)) {
                            String message = plugin.getPU().buildStringFromArgs(args, 1);
                            cache.setLastReplied(target.getUniqueId(), uuid);
                            cache.setLastReplied(uuid, target.getUniqueId());
                            player.sendMessage(plugin.getPU().formatC("&9[&eYou &9-> &e" + target.getName() + "&9] ").append(Component.text(message).color(NamedTextColor.DARK_GREEN)));
                            target.sendMessage(plugin.getPU().formatC("&9[&e" + player.getName() + " &9-> &eYou&9] ").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(Component.text(message).color(NamedTextColor.DARK_GREEN)));
                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.MUTED.getString(new String[] { cache.getMuteReason(uuid) }));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_MESSAGE_TO_SELF.getString(null));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { args[0] }));
            }
        }
        return true;
    }
}
