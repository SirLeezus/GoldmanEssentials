package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetColorCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Cache cache = plugin.getCache();

            if (player.hasPermission("essentials.command.setcolor")) {

                if (args.length > 1) {
                    if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (plugin.getData().getChatColors().contains(args[1]) && target != null) {
                            String color = args[1];
                            cache.setColor(target.getUniqueId(), color);
                            plugin.getPU().updateDisplayName(player);
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_SETCOLOR_SUCCESSFUL.getString(new String[] { target.getName(), ChatColor.valueOf(color) + color }));
                        }
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[]{ args[0] }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_SETCOLOR_ARG.getString(null));
            }
        }
        return true;
    }
}
