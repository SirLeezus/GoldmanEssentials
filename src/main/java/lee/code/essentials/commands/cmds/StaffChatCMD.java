package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;

import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StaffChatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (args.length > 0) {
                String message = BukkitUtils.buildStringFromArgs(args, 0);

                for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                    if (oPlayer.hasPermission("essentials.command.staffchat")) {
                        oPlayer.sendMessage(Lang.STAFF_CHAT_PREFIX.getComponent(null).append(player.displayName()).append(BukkitUtils.parseColorComponent("&3: ")).append(Component.text(message)).color(TextColor.color(86, 40, 255)));
                    }
                }
            } else {
                if (!data.isStaffChatting(uuid)) {
                    data.addStaffChat(uuid);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAFFCHAT_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.ON.getString() })));
                } else {
                    data.removeStaffChat(uuid);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAFFCHAT_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.OFF.getString() })));
                }
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
