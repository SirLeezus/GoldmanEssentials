package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.PremiumRankList;
import lee.code.essentials.lists.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankListCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            List<String> serverRanks = new ArrayList<>();
            List<String> staffRanks = new ArrayList<>();
            List<String> premiumRanks = new ArrayList<>();

            int number = 1;
            String playerRank = cache.getRank(uuid);

            serverRanks.add("");
            serverRanks.add(Lang.COMMAND_RANKLIST_SERVER_RANKS.getString(null));
            for (String rank : plugin.getPU().getRanks()) {
                if (!RankList.valueOf(rank).getNextRank().equals("STAFF")) {
                    String line = "&3" + number + "&b. " + RankList.valueOf(rank).getPrefix();
                    if (playerRank.equals(rank)) line = "&a> " + line + " &a<";
                    serverRanks.add(plugin.getPU().format(line));
                    number++;
                }
            }

            serverRanks.add("");

            number = 1;

            serverRanks.add(Lang.COMMAND_RANKLIST_STAFF_RANKS.getString(null));
            for (String rank : plugin.getPU().getRanks()) {
                if (RankList.valueOf(rank).getNextRank().equals("STAFF")) {
                    String line = "&3" + number + "&b. " + RankList.valueOf(rank).getPrefix();
                    if (playerRank.equals(rank)) line = "&a> " + line + " &a<";
                    staffRanks.add(plugin.getPU().format(line));
                    number++;
                }
            }

            staffRanks.add("");

            number = 1;

            premiumRanks.add(Lang.COMMAND_RANKLIST_PREMIUM_RANKS.getString(null));
            for (String rank : plugin.getPU().getPremiumRanks()) {
                premiumRanks.add(plugin.getPU().format("&3" + number + "&b. " + PremiumRankList.valueOf(rank).getDisplayName() + PremiumRankList.valueOf(rank).getSuffix()));
                number++;
            }

            premiumRanks.add("");

            player.sendMessage(Lang.COMMAND_RANKLIST_TITLE.getString(null));
            for (String line : serverRanks) player.sendMessage(line);
            for (String line : staffRanks) player.sendMessage(line);
            for (String line : premiumRanks) player.sendMessage(line);
            player.sendMessage(Lang.COMMAND_RANKLIST_SPLITTER.getString(null));
        }
        return true;
    }
}
