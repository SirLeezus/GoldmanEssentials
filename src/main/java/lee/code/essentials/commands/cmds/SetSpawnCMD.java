package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.setspawn")) {
                cache.setSpawn(player.getLocation());
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_SETSPAWN_SUCCESSFUL.getString(null));
            }
        }
        return true;
    }
}
