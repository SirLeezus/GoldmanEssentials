package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.UUID;

public class MoneyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            Cache cache = plugin.getCache();
            UUID uuid = player.getUniqueId();

            if (args.length > 2) {

                int amount = 0;
                Player target = player;

                //online player check
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                    //target player
                    target = Bukkit.getPlayer(args[1]);
                } else {
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[]{args[1]}));
                    return true;
                }

                //value check
                Scanner buyScanner = new Scanner(args[2]);
                if (buyScanner.hasNextInt()) {
                    amount = Integer.parseInt(args[2]);
                } else {
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_MONEY_VALUE.getString(new String[]{ args[2] } ));
                    return true;
                }

                //switch on sub command
                String subCommand = args[0];

                switch (subCommand) {

                    case "set":
                        cache.setBalance(target.getUniqueId(), amount);
                        player.sendMessage("Money sent.");
                        target.sendMessage("Your balance has been set to " + amount + " by " + player.getName() + ".");
                        break;

                    case "remove":
                        cache.withdraw(target.getUniqueId(), amount);
                        player.sendMessage("Money taken.");
                        target.sendMessage("The amount " + amount + " has been taken from your account by " + player.getName() + ".");
                        break;

                    case "add":
                        cache.deposit(target.getUniqueId(), amount);
                        player.sendMessage("Money added.");
                        target.sendMessage("You received " + amount + " from " + player.getName() + ".");
                        break;

                    default:
                        player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getString(new String[]{ args[0] }));
                        break;
                }
            }
        }
        return true;
    }
}
