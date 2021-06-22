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
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            //money give {player} {amount}
            if (args.length > 2) {

                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                    Player target = Bukkit.getPlayer(args[1]);

                    Scanner buyScanner = new Scanner(args[2]);
                    if (buyScanner.hasNextLong()) {
                        long amount = Long.parseLong(args[2]);

                        if (target != null) {
                            UUID tUUID = target.getUniqueId();
                            String subCommand = args[0];

                            switch (subCommand) {
                                case "set" -> {
                                    cache.setBalance(tUUID, amount);
                                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_SET.getString(new String[]{target.getName(), plugin.getPU().formatAmount(amount)}));
                                    target.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_SET_TARGET.getString(new String[]{plugin.getPU().formatAmount(amount)}));
                                }
                                case "remove" -> {
                                    cache.withdraw(tUUID, amount);
                                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_REMOVE.getString(new String[]{target.getName(), plugin.getPU().formatAmount(amount)}));
                                    target.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_REMOVE_TARGET.getString(new String[]{plugin.getPU().formatAmount(amount)}));
                                }
                                case "give" -> {
                                    cache.deposit(tUUID, amount);
                                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_GIVE.getString(new String[]{target.getName(), plugin.getPU().formatAmount(amount)}));
                                    target.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_MONEY_GIVE_TARGET.getString(new String[]{plugin.getPU().formatAmount(amount)}));
                                }
                                default -> player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getString(new String[]{args[0]}));
                            }
                        }
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_MONEY_VALUE.getString(new String[]{ args[2] } ));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[]{args[1]}));
            }
        }
        return true;
    }
}
