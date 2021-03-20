package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.builders.NameBuilder;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPrefixCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Cache cache = plugin.getCache();

            if (player.hasPermission("essentials.command.setprefix")) {

                if (args.length > 1) {

                    if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            String prefix = plugin.getPU().buildStringFromArgs(args, 1) + " ";
                            cache.setPrefix(target.getUniqueId(), prefix);
                            new NameBuilder(target).setColor(ChatColor.valueOf(cache.getColor(target.getUniqueId()))).setPrefix(prefix).setSuffix(cache.getSuffix(target.getUniqueId())).build();
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_SETPREFIX_SUCCESSFUL.getString(new String[] { target.getName(), plugin.getPU().format(prefix) }));
                        }
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[]{ args[0] }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_SETPREFIX_ARG.getString(null));
            }
        }
        return true;
    }
}
