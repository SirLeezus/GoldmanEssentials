package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayTimeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (oTarget != null) {
                    long time = oTarget.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PLAYTIME_TARGET_SUCCESSFUL.getComponent(new String[] { oTarget.getName(), BukkitUtils.parseSeconds(time) })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else {
                long time = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PLAYTIME_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseSeconds(time) })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
