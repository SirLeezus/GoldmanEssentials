package lee.code.essentials.commands.cmds;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.RankList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankupCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            String staff = "STAFF";
            String last = "LAST";
            String rank = cache.getRank(uuid);
            String nextRank = RankList.valueOf(rank).getNextRank();
            List<String> ranks = data.getRankKeys();
            String nextColor = ranks.contains(nextRank) ? RankList.valueOf(nextRank).getColor() : "YELLOW";
            String nextRankPrefix = ranks.contains(nextRank) ? RankList.valueOf(nextRank).getPrefix() : "&6&lPrestige";

            int level = cache.getLevel(uuid);
            int maxLevel = plugin.getData().getAdvancementNames().size();
            int rankupLevel = RankList.valueOf(rank).getRankupLevel();
            int prestigeLevel = cache.getPrestige(uuid);
            double prestigePercentageMultiplier = prestigeLevel != 0 ? Double.parseDouble("0." + prestigeLevel) : 0;
            prestigePercentageMultiplier = prestigeLevel > 9 ? 1.0 : prestigePercentageMultiplier;

            int exp = RankList.valueOf(rank).getExp();
            long cash = RankList.valueOf(rank).getCash();
            int expAmount = ranks.contains(rank) ? (int) (exp * prestigePercentageMultiplier) + exp : 0;
            long cashAmount =  ranks.contains(rank) ? (int) (cash * prestigePercentageMultiplier) + cash : 0;

            if (args.length < 1) {
                if (level < maxLevel) {
                    if (level < rankupLevel) {
                        List<Component> lines = new ArrayList<>();
                        Component spacer = Component.text("");
                        lines.add(Lang.COMMAND_RANKUP_TITLE.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_HOVER.getComponent(new String[]{pu.formatAmount(level), pu.formatAmount(maxLevel)})));
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_ADVANCEMENT_PROGRESS.getComponent(new String[] { pu.getProgressBar(level, rankupLevel, 50, "|", "&#00E835", "&7"), pu.formatAmount(level), pu.formatAmount(rankupLevel) }));
                        if (!nextRank.equals(staff)) {
                            lines.add(spacer);
                            lines.add(Lang.COMMAND_RANKUP_NEXT_RANK.getComponent(new String[] { nextRankPrefix }));
                        }
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_REWARDS.getComponent(new String[] { pu.formatAmount(expAmount), pu.formatAmount(cashAmount) }));
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_SPLITTER.getComponent(null));

                        for (Component line : lines) player.sendMessage(line);
                    } else {
                        Component rankup = Lang.COMMAND_RANKUP_CONFIRM_RANKUP_MESSAGE.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_BUTTON.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_CONFIRM_RANKUP_HOVER.getComponent(new String[]{nextRankPrefix})).clickEvent(ClickEvent.runCommand("/rankup confirm")));
                        player.sendMessage(rankup);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
                    }
                } else {
                    String playerNextPrestige = String.valueOf(cache.getPrestige(uuid) + 1);
                    Component prestige = Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_BUTTON.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE_HOVER.getComponent(new String[]{playerNextPrestige})).clickEvent(ClickEvent.runCommand("/rankup prestige")));
                    player.sendMessage(prestige);
                    player.sendMessage(Lang.WARNING.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE_WARNING.getComponent(null)));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
                }

            } else if (args[0].equalsIgnoreCase("confirm")) {
                if (level >= rankupLevel) {
                    if (!nextRank.equals(last) && !nextRank.equals(staff)) {
                        if (!player.hasPermission("essentials.command.namecolor")) cache.setColor(uuid, nextColor);
                        cache.setRank(uuid, nextRank);
                        cache.setPrefix(uuid, nextRankPrefix);
                        pu.updateDisplayName(player, false);
                        player.giveExp(expAmount);
                        cache.deposit(uuid, cashAmount);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_CASH.getComponent(new String[] { pu.formatAmount(cashAmount) })));
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_EXP.getComponent(new String[] { pu.formatAmount(expAmount) })));
                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_BROADCAST.getComponent(new String[]{player.getName(), nextRankPrefix})));
                        for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_RANKUP_CONFIRM.getComponent(new String[] { pu.formatAmount(level), pu.formatAmount(rankupLevel) })));

            } else if (args[0].equalsIgnoreCase("prestige")) {
                if (level >= maxLevel) {
                    String playerNextPrestige = String.valueOf(cache.getPrestige(uuid) + 1);
                    cache.addPrestige(uuid);
                    cache.setLevel(uuid, "0");

                    if (!nextRank.equals(staff)) {
                        if (!player.hasPermission("essentials.command.namecolor")) cache.setColor(uuid, nextColor);
                        cache.setRank(uuid, RankList.NOMAD.name());
                        cache.setPrefix(uuid, RankList.NOMAD.getPrefix());
                    }

                    for (String sKey : plugin.getData().getAdvancementNames()) {
                        NamespacedKey key = NamespacedKey.minecraft(sKey);
                        Advancement advancement = Bukkit.getAdvancement(key);
                        if (advancement != null) {
                            AdvancementProgress progress = player.getAdvancementProgress(advancement);
                            for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
                        }
                    }
                    pu.updateDisplayName(player, false);
                    player.giveExp(expAmount);
                    cache.deposit(uuid, cashAmount);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_CASH.getComponent(new String[] { pu.formatAmount(cashAmount) })));
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_EXP.getComponent(new String[] { pu.formatAmount(expAmount) })));
                    plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_PRESTIGE_BROADCAST.getComponent(new String[]{player.getName(), playerNextPrestige})));
                    for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PRESTIGE.getComponent(new String[] { pu.formatAmount(level), pu.formatAmount(maxLevel) })));

            } else if (args[0].equalsIgnoreCase("check")) {
                int levelCheck = 0;
                for (String sKey : plugin.getData().getAdvancementNames()) {
                    NamespacedKey key = NamespacedKey.minecraft(sKey);
                    Advancement advancement = Bukkit.getAdvancement(key);
                    if (advancement != null) {
                        if (player.getAdvancementProgress(advancement).isDone()) {
                            levelCheck++;
                        }
                    }
                }
                cache.setLevel(uuid, String.valueOf(levelCheck));
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_CHECK.getComponent(new String[] { pu.formatAmount(levelCheck), pu.formatAmount(maxLevel) })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
