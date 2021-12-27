package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AddBoosterCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        if (args.length > 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (target != null) {
                UUID uuid = target.getUniqueId();
                long durationBooster = pu.unFormatSeconds(args[1]);
                if (durationBooster != 0) {
                    if (pu.containOnlyNumbers(args[2])) {
                        String multiplier = args[2];
                        cache.queueBooster(uuid, multiplier, durationBooster);
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BOOSTER_ADD_SUCCESSFUL.getComponent(new String[] { target.getName(), multiplier })));
                    }
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));

        return true;
    }
}
