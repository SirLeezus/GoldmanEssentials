package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.PremiumRankList;
import lee.code.essentials.lists.RankList;
import lee.code.essentials.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        PermissionManager pm = plugin.getPermissionManager();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        if (args.length > 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (target != null) {
                UUID tUUID = target.getUniqueId();
                String rank = args[1].toUpperCase();
                boolean premium = false;
                if (pu.getRanks().contains(rank)) {
                    RankList normalRank = RankList.valueOf(rank);
                    cache.setColor(tUUID, normalRank.getColor());
                    cache.setRank(tUUID, rank);
                    cache.setPrefix(tUUID, normalRank.getPrefix());
                } else if (pu.getPremiumRanks().contains(rank)) {
                    PremiumRankList premiumRank = PremiumRankList.valueOf(rank);
                    cache.setSuffix(tUUID, premiumRank.getSuffix());
                    pm.addPremiumPerms(tUUID, premiumRank);
                    premium = true;
                }
                if (target.isOnline()) {
                    Player tPlayer = target.getPlayer();
                    if (tPlayer != null) {
                        pu.updateDisplayName(tPlayer, false);
                        if (!tPlayer.isOp()) pm.register(tPlayer);
                        if (premium) tPlayer.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETRANK_PREMIUM_SUCCESSFUL.getComponent(new String[] { PremiumRankList.valueOf(rank).getDisplayName() })));
                    }
                }
                sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKSET_SUCCESSFUL.getComponent(new String[] { rank, target.getName() })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
