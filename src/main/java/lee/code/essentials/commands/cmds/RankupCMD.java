package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.builders.NameBuilder;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.RankList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankupCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            String rank = cache.getRank(uuid);

            int level = cache.getLevel(uuid);
            int rankupLevel = RankList.valueOf(rank).getRankupLevel();
            String nextRank = RankList.valueOf(rank).getNextRank();
            String nextRankPrefix = RankList.valueOf(nextRank).getPrefix();

            if (args.length < 1) {
                if (level < rankupLevel) {
                    int percent = level * 100 / rankupLevel;
                    double b = Math.round(percent * 10.0) / 10.0;

                    List<String> lines = new ArrayList<>();

                    TextComponent title = Component.text().content(Lang.COMMAND_RANKUP_TITLE.getString(null)).hoverEvent(Component.text(Lang.COMMAND_RANKUP_HOVER.getString(new String[] { plugin.getPU().formatAmount(level), plugin.getPU().formatAmount(plugin.getData().getGameAdvancements().size()) }))).build();
                    lines.add("");
                    lines.add(plugin.getPU().format("&a&lAdvancements Required&7: &2" + plugin.getPU().formatAmount(level) + "&7/&2" + plugin.getPU().formatAmount(rankupLevel)));
                    lines.add(plugin.getPU().format("&6&lProgress&7: &8[" + plugin.getPU().getProgressBar(level, rankupLevel, 50, "|", "&a", "&7") + "&8] &e" + b + "%"));
                    lines.add("");
                    lines.add(plugin.getPU().format("&9&lNext Rank&7: " + nextRankPrefix));
                    lines.add("");
                    lines.add(Lang.COMMAND_RANKUP_SPLITTER.getString(null));

                    player.sendMessage(title);
                    for (String line : lines) player.sendMessage(line);
                } else {
                    TextComponent rankup = Component.text().content(Lang.COMMAND_RANKUP_CONFIRM_MESSAGE.getString(null)).append(Component.text(Lang.COMMAND_RANKUP_CONFIRM_BUTTON.getString(null))).hoverEvent(Component.text(Lang.COMMAND_RANKUP_CONFIRM_HOVER.getString(new String[] { nextRankPrefix } ))).clickEvent(ClickEvent.runCommand("/rankup confirm")).build();
                    player.sendMessage(rankup);
                }
            } else if (args[0].equalsIgnoreCase("confirm")) {

                if (level >= rankupLevel) {
                    cache.setRank(uuid, nextRank);
                    cache.setPrefix(uuid, nextRankPrefix + " ");
                    new NameBuilder(player).setColor(ChatColor.valueOf(cache.getColor(uuid))).setPrefix(nextRankPrefix + " ").setSuffix(cache.getSuffix(uuid)).build();
                    Bukkit.broadcastMessage(Lang.ANNOUNCEMENT.getString(null) + Lang.COMMAND_RANKUP_BROADCAST.getString(new String[] { player.getName(), nextRankPrefix }));

                    for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                        oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                    }
                }
            }
        }
        return true;
    }
}
