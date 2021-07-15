package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;

import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String message = plugin.getPU().buildStringFromArgs(args, 0);

                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("essentials.command.staffchat")) {
                        oPlayer.sendMessage(Lang.STAFF_CHAT_PREFIX.getComponent(null).append(player.displayName()).append(plugin.getPU().formatC("&#0073A5: ")).append(Component.text(message)).color(NamedTextColor.GOLD));
                    }
                }
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
