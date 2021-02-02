package lee.code.essentials.commands.cmds;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.nametags.NameTagBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ColorCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            TheEssentials plugin = TheEssentials.getPlugin();
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();

            if (player.hasPermission("essentials.command.color")) {

                SQL.setPrefix(uuid, "&c[Owner] ");

                player.sendMessage("Test!");
            }
        }
        return true;
    }
}
