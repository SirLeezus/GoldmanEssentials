package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.PremiumRankList;
import lee.code.essentials.lists.RankList;
import net.kyori.adventure.text.Component;
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
            List<Component> lines = new ArrayList<>();

            int number = 1;
            String playerRank = cache.getRank(uuid);

            lines.add(Lang.COMMAND_RANKLIST_TITLE.getComponent(null));
            lines.add(Component.text(""));
            lines.add(Lang.COMMAND_RANKLIST_SERVER_RANKS.getComponent(null));
            for (String rank : plugin.getPU().getRanks()) {
                if (!RankList.valueOf(rank).getNextRank().equals("STAFF")) {
                    String line = "&3" + number + "&b. " + RankList.valueOf(rank).getPrefix();
                    if (playerRank.equals(rank)) line = "&3" + number + "&b. &2> " + RankList.valueOf(rank).getPrefix() + " &2<";
                    lines.add(plugin.getPU().formatC(line));
                    number++;
                }
            }
            lines.add(Component.text(""));

            number = 1;
            lines.add(Lang.COMMAND_RANKLIST_STAFF_RANKS.getComponent(null));
            for (String rank : plugin.getPU().getRanks()) {
                if (RankList.valueOf(rank).getNextRank().equals("STAFF")) {
                    String line = "&3" + number + "&b. " + RankList.valueOf(rank).getPrefix();
                    if (playerRank.equals(rank)) line = "&3" + number + "&b. &2> " + RankList.valueOf(rank).getPrefix() + " &2<";
                    lines.add(plugin.getPU().formatC(line));
                    number++;
                }
            }
            lines.add(Component.text(""));

            number = 1;

            lines.add(Lang.COMMAND_RANKLIST_PREMIUM_RANKS.getComponent(null));
            for (String rank : plugin.getPU().getPremiumRanks()) {
                lines.add(plugin.getPU().formatC("&3" + number + "&b. " + PremiumRankList.valueOf(rank).getDisplayName() + PremiumRankList.valueOf(rank).getSuffix()));
                number++;
            }
            lines.add(Component.text(""));
            lines.add(Lang.COMMAND_RANKLIST_SPLITTER.getComponent(null));

            for (Component line : lines) player.sendMessage(line);
        }
        return true;
    }
}
