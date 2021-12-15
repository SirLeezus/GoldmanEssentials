package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
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
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        PU pu = plugin.getPU();

        if (args.length > 1) {
            if (pu.getOnlinePlayers().contains(args[0])) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (plugin.getData().getColorNames().contains(args[1])) {
                        String color = args[1];
                        cache.setColor(target.getUniqueId(), color);
                        pu.updateDisplayName(target, false);
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETCOLOR_SUCCESSFUL.getComponent(new String[] { target.getName(), ChatColor.valueOf(color) + color })));
                    }
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[]{ args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
