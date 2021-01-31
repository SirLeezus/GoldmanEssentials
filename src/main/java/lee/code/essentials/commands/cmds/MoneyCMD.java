package lee.code.essentials.commands.cmds;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.files.defaults.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoneyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            TheEssentials plugin = TheEssentials.getPlugin();
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();


            ///money set {player} {amount}
            if (player.hasPermission("essentials.command.money")) {

                if (args.length > 1) {

                    switch (args[0]) {

                        case "set":
                            int value = Integer.parseInt(args[2]);

                            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                                Player targetPlayer = Bukkit.getPlayer(args[1]);
                                SQL.deposit(targetPlayer.getUniqueId(), value);
                                player.sendMessage("Money sent.");
                            }
                    }
                }
            }
        }
        return true;
    }
}
