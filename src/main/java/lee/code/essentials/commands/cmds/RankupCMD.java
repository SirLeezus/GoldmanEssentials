package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Rank;
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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankupCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            String staff = "STAFF";
            String last = "LAST";
            String rank = cacheManager.getRank(uuid);
            String nextRank = Rank.valueOf(rank).getNextRank();
            List<String> ranks = data.getRankKeys();
            String nextColor = ranks.contains(nextRank) ? Rank.valueOf(nextRank).getColor() : "YELLOW";
            String nextRankPrefix = ranks.contains(nextRank) ? Rank.valueOf(nextRank).getPrefix() : "&6&lPrestige";

            int level = cacheManager.getLevel(uuid);
            int maxLevel = plugin.getData().getAdvancementNames().size();
            int rankupLevel = Rank.valueOf(rank).getRankupLevel();
            int prestigeLevel = cacheManager.getPrestige(uuid);
            double prestigePercentageMultiplier = prestigeLevel != 0 ? Double.parseDouble("0." + prestigeLevel) : 0;
            prestigePercentageMultiplier = prestigeLevel > 9 ? 1.0 : prestigePercentageMultiplier;

            int exp = Rank.valueOf(rank).getExp();
            long cash = Rank.valueOf(rank).getCash();
            int expAmount = ranks.contains(rank) ? (int) (exp * prestigePercentageMultiplier) + exp : 0;
            long cashAmount =  ranks.contains(rank) ? (int) (cash * prestigePercentageMultiplier) + cash : 0;

            if (args.length < 1) {
                if (level < maxLevel) {
                    if (level < rankupLevel) {
                        List<Component> lines = new ArrayList<>();
                        Component spacer = Component.text("");
                        lines.add(Lang.COMMAND_RANKUP_TITLE.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_HOVER.getComponent(new String[]{ BukkitUtils.parseValue(level), BukkitUtils.parseValue(maxLevel) })));
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_ADVANCEMENT_PROGRESS.getComponent(new String[] { pu.getProgressBar(level, rankupLevel, 50, "|", "&#00E835", "&7"), BukkitUtils.parseValue(level), BukkitUtils.parseValue(rankupLevel) }));
                        if (!nextRank.equals(staff)) {
                            lines.add(spacer);
                            lines.add(Lang.COMMAND_RANKUP_NEXT_RANK.getComponent(new String[] { nextRankPrefix }));
                        }
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_REWARDS.getComponent(new String[] { BukkitUtils.parseValue(expAmount), BukkitUtils.parseValue(cashAmount) }));
                        lines.add(spacer);
                        lines.add(Lang.COMMAND_RANKUP_SPLITTER.getComponent(null));

                        for (Component line : lines) player.sendMessage(line);
                    } else {
                        Component rankup = Lang.COMMAND_RANKUP_CONFIRM_RANKUP_MESSAGE.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_BUTTON.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_CONFIRM_RANKUP_HOVER.getComponent(new String[]{nextRankPrefix})).clickEvent(ClickEvent.runCommand("/rankup confirm")));
                        player.sendMessage(rankup);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
                    }
                } else {
                    String playerNextPrestige = String.valueOf(cacheManager.getPrestige(uuid) + 1);
                    Component prestige = Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_BUTTON.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE_HOVER.getComponent(new String[]{playerNextPrestige})).clickEvent(ClickEvent.runCommand("/rankup prestige")));
                    player.sendMessage(prestige);
                    player.sendMessage(Lang.WARNING.getComponent(null).append(Lang.COMMAND_RANKUP_CONFIRM_PRESTIGE_WARNING.getComponent(null)));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
                }

            } else if (args[0].equalsIgnoreCase("confirm")) {
                if (level >= rankupLevel) {
                    if (!nextRank.equals(last) && !nextRank.equals(staff)) {
                        if (!player.hasPermission("essentials.command.namecolor")) cacheManager.setColor(uuid, nextColor);
                        cacheManager.setRank(uuid, nextRank);
                        cacheManager.setPrefix(uuid, nextRankPrefix);
                        pu.updateDisplayName(player, false);
                        player.giveExp(expAmount);
                        cacheManager.deposit(uuid, cashAmount);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_CASH.getComponent(new String[] { BukkitUtils.parseValue(cashAmount) })));
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_EXP.getComponent(new String[] { BukkitUtils.parseValue(expAmount) })));
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givemysterybox " + player.getName());
                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_BROADCAST.getComponent(new String[]{ player.getName(), nextRankPrefix })));
                        for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_RANKUP_CONFIRM.getComponent(new String[] { BukkitUtils.parseValue(level), BukkitUtils.parseValue(rankupLevel) })));

            } else if (args[0].equalsIgnoreCase("prestige")) {
                if (level >= maxLevel) {
                    String playerNextPrestige = String.valueOf(cacheManager.getPrestige(uuid) + 1);
                    cacheManager.addPrestige(uuid);
                    cacheManager.setLevel(uuid, 0);

                    if (!nextRank.equals(staff)) {
                        if (!player.hasPermission("essentials.command.namecolor")) cacheManager.setColor(uuid, nextColor);
                        cacheManager.setRank(uuid, Rank.NOMAD.name());
                        cacheManager.setPrefix(uuid, Rank.NOMAD.getPrefix());
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
                    cacheManager.deposit(uuid, cashAmount);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_CASH.getComponent(new String[] { BukkitUtils.parseValue(cashAmount) })));
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_REWARD_EXP.getComponent(new String[] { BukkitUtils.parseValue(expAmount) })));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givemysterybox " + player.getName());
                    plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_PRESTIGE_BROADCAST.getComponent(new String[]{player.getName(), playerNextPrestige})));
                    for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PRESTIGE.getComponent(new String[] { BukkitUtils.parseValue(level), BukkitUtils.parseValue(maxLevel) })));

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
                cacheManager.setLevel(uuid, levelCheck);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_CHECK.getComponent(new String[] { BukkitUtils.parseValue(levelCheck), BukkitUtils.parseValue(maxLevel) })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
