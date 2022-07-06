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

public class PayCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {

            if (args.length > 1) {
                UUID uuid = player.getUniqueId();
                OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (target != null) {
                    UUID targetUUID = target.getUniqueId();
                    if (BukkitUtils.containOnlyNumbers(args[1])) {
                        long payAmount = Long.parseLong(args[1]);
                        if (payAmount < 0) payAmount = 0;
                        long senderBalance = cacheManager.getBalance(uuid);
                        if (!targetUUID.equals(uuid)) {
                            if (senderBalance >= payAmount) {
                                if (payAmount != 0) {
                                    cacheManager.withdraw(uuid, payAmount);
                                    cacheManager.deposit(targetUUID, payAmount);
                                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PAY_SENDER_SUCCESSFUL.getComponent(new String[] { target.getName(), BukkitUtils.parseValue(payAmount) })));
                                    if (target.isOnline()) {
                                        Player oTarget = target.getPlayer();
                                        if (oTarget != null) oTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PAY_TARGET_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseValue(payAmount), player.getName() })));
                                    }
                                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PAY_ZERO.getComponent(null)));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PAY_INSUFFICIENT_FUNDS.getComponent(new String[] { target.getName(), BukkitUtils.parseValue(payAmount), BukkitUtils.parseValue(senderBalance) })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PAY_SELF.getComponent(null)));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PAY_NOT_NUMBER.getComponent(new String[] { args[1] })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
