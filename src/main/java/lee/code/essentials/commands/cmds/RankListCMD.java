package lee.code.essentials.commands.cmds;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
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
        PU pu = plugin.getPU();
        Data data = plugin.getData();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            List<Component> lines = new ArrayList<>();

            int number = 1;
            String playerRank = cache.getRank(uuid);
            String playerSuffix = cache.getSuffix(uuid);
            Component spacer = Component.text("");

            lines.add(Lang.COMMAND_RANKLIST_TITLE.getComponent(null));
            lines.add(spacer);
            lines.add(Lang.COMMAND_RANKLIST_SERVER_RANKS.getComponent(null));
            for (String rank : data.getRankKeys()) {
                RankList sRank = RankList.valueOf(rank);
                if (!sRank.isStaffRank()) {
                    Component line = playerRank.equals(rank) ? Lang.COMMAND_RANKLIST_HAS_LINE.getComponent(new String[] { String.valueOf(number), sRank.getPrefix() }) : Lang.COMMAND_RANKLIST_LINE.getComponent(new String[] { String.valueOf(number), sRank.getPrefix() });
                    lines.add(line);
                    number++;
                }
            }
            lines.add(spacer);

            number = 1;
            lines.add(Lang.COMMAND_RANKLIST_STAFF_RANKS.getComponent(null));
            for (String rank : data.getRankKeys()) {
                RankList sRank = RankList.valueOf(rank);
                if (sRank.isStaffRank()) {
                    Component line = playerRank.equals(rank) ? Lang.COMMAND_RANKLIST_HAS_LINE.getComponent(new String[] { String.valueOf(number), sRank.getPrefix() }) : Lang.COMMAND_RANKLIST_LINE.getComponent(new String[] { String.valueOf(number), sRank.getPrefix() });
                    lines.add(line);
                    number++;
                }
            }
            lines.add(spacer);

            number = 1;
            lines.add(Lang.COMMAND_RANKLIST_PREMIUM_RANKS.getComponent(null));
            for (String rank : data.getPremiumRankKeys()) {
                PremiumRankList sRank = PremiumRankList.valueOf(rank);
                Component line = playerSuffix.equals(sRank.getSuffix()) ? Lang.COMMAND_RANKLIST_HAS_LINE.getComponent(new String[] { String.valueOf(number), sRank.getDisplayName() + sRank.getSuffix() }) : Lang.COMMAND_RANKLIST_LINE.getComponent(new String[] { String.valueOf(number), sRank.getDisplayName() + sRank.getSuffix() });
                lines.add(line);
                number++;
            }
            lines.add(spacer);
            lines.add(Lang.COMMAND_RANKLIST_SPLITTER.getComponent(null));

            for (Component line : lines) player.sendMessage(line);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
