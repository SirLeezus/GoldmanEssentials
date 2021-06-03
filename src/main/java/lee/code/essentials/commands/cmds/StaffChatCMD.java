package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;

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

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String message = plugin.getPU().buildStringFromArgs(args, 0);

                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("essentials.command.staffchat")) {
                        oPlayer.sendMessage(plugin.getPU().formatC("&#0073A5[&#A50000&lSC&#0073A5] ").append(player.displayName()).append(plugin.getPU().formatC("&#0073A5: ")).append(Component.text(message)).color(NamedTextColor.GOLD));
                    }
                }
            }
        }
        return true;
    }
}
