package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MoneyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[1]);
            if (target != null) {
                if (BukkitUtils.containOnlyNumbers(args[2])) {
                    long amount = Long.parseLong(args[2]);
                    UUID tUUID = target.getUniqueId();
                    String subCommand = args[0];
                    Player oTarget = target.getPlayer();

                    switch (subCommand) {
                        case "set" -> {
                            cacheManager.setBalance(tUUID, amount);
                            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_SET.getComponent(new String[]{target.getName(), BukkitUtils.parseValue(amount) })));
                            if (oTarget != null && oTarget.isOnline()) oTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_SET_TARGET.getComponent(new String[]{ BukkitUtils.parseValue(amount) })));
                        }
                        case "remove" -> {
                            cacheManager.withdraw(tUUID, amount);
                            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_REMOVE.getComponent(new String[]{target.getName(), BukkitUtils.parseValue(amount)})));
                            if (oTarget != null && oTarget.isOnline()) oTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_REMOVE_TARGET.getComponent(new String[]{ BukkitUtils.parseValue(amount) })));
                        }
                        case "give" -> {
                            cacheManager.deposit(tUUID, amount);
                            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_GIVE.getComponent(new String[]{target.getName(), BukkitUtils.parseValue(amount)})));
                            if (oTarget != null && oTarget.isOnline()) oTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_GIVE_TARGET.getComponent(new String[]{ BukkitUtils.parseValue(amount) })));
                        }
                        default -> sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getComponent(new String[]{ args[0] })));
                    }
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_MONEY_VALUE.getComponent(new String[]{ args[2] } )));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[1] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
