package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.PremiumRankList;
import lee.code.essentials.lists.RankList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SetRankCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (args.length > 1) {
            if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    UUID tUUID = target.getUniqueId();
                    String rank = args[1].toUpperCase();
                    if (plugin.getPU().getRanks().contains(rank)) {
                        cache.setRank(tUUID, rank);
                        cache.setPrefix(tUUID, RankList.valueOf(rank).getPrefix());
                    } else if (plugin.getPU().getPremiumRanks().contains(rank)) cache.setSuffix(tUUID, " " + PremiumRankList.valueOf(rank).getSuffix());
                    plugin.getPU().updateDisplayName(target);
                    plugin.getPermissionManager().register(target);
                    sender.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_RANKSET_SUCCESSFUL.getString(new String[] { rank, target.getName() }));
                }
            }
        }
        return true;
    }
}