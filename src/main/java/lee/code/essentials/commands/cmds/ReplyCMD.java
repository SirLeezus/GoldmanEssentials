package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ReplyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (cache.hasLastReplied(uuid)) {
                UUID uuidTarget = cache.getLastReplied(uuid);
                OfflinePlayer oTarget = Bukkit.getPlayer(uuidTarget);
                if (oTarget != null) {
                    if (oTarget.isOnline()) {
                        Player target = oTarget.getPlayer();
                        if (target != null) {
                            String message = plugin.getPU().buildStringFromArgs(args, 0);
                            cache.setLastReplied(uuid, target.getUniqueId());
                            player.sendMessage(plugin.getPU().formatC("&9[&eYou &9-> &e" + target.getName() + "&9] ").append(Component.text(message).color(NamedTextColor.DARK_GREEN)));
                            target.sendMessage(plugin.getPU().formatC("&9[&e" + player.getName() + " &9-> &eYou&9] ").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(Component.text(message).color(NamedTextColor.DARK_GREEN)));
                        } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getString(null));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { oTarget.getName() }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getString(null));
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getString(null));
        }
        return true;
    }
}
