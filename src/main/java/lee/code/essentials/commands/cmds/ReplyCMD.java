package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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

        if (sender instanceof Player player) {
            if (args.length > 0) {
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
                                player.sendMessage(Lang.MESSAGE_SENT.getComponent(new String[] { target.getName() }).append(Component.text(message).color(TextColor.color(0, 220, 234))));
                                target.sendMessage(Lang.MESSAGE_RECEIVED.getComponent(new String[] { player.getName() }).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " ")).append(Component.text(message).color(TextColor.color(0, 220, 234))));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getComponent(null)));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { oTarget.getName() })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_REPLY_NO_PLAYER.getComponent(null)));
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
