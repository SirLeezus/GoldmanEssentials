package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DeleteHomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = plugin.getPU().buildStringFromArgs(args, 0);
                if (cache.isAlreadyHome(uuid, name)) {
                    cache.removeHome(uuid, name);
                    player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_DELETEHOME_SUCCESSFUL.getString(new String[] { name }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_DELETEHOME_NOT_SAVED.getString(new String[] { name }));
            }
        }
        return true;
    }
}
