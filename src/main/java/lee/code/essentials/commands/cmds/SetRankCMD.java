package lee.code.essentials.commands.cmds;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.PremiumRank;
import lee.code.essentials.lists.Rank;
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
        Data data = plugin.getData();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (target != null) {
                UUID tUUID = target.getUniqueId();
                String rank = args[1].toUpperCase();
                boolean premium = false;
                if (data.getRankKeys().contains(rank)) {
                    Rank normalRank = Rank.valueOf(rank);
                    cacheManager.setColor(tUUID, normalRank.getColor());
                    cacheManager.setRank(tUUID, rank);
                    cacheManager.setPrefix(tUUID, normalRank.getPrefix());
                } else if (data.getPremiumRankKeys().contains(rank)) {
                    PremiumRank premiumRank = PremiumRank.valueOf(rank);
                    cacheManager.setSuffix(tUUID, premiumRank.getSuffix());
                    pm.addPremiumPerms(tUUID, premiumRank);
                    premium = true;
                }
                if (target.isOnline()) {
                    Player tPlayer = target.getPlayer();
                    if (tPlayer != null) {
                        pu.updateDisplayName(tPlayer, false, false);
                        pm.register(tPlayer);
                        if (premium) tPlayer.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETRANK_PREMIUM_SUCCESSFUL.getComponent(new String[] { PremiumRank.valueOf(rank).getDisplayName() })));
                    }
                }
                sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKSET_SUCCESSFUL.getComponent(new String[] { rank, target.getName() })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
