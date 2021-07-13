package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
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

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            String staff = "STAFF";
            String last = "LAST";
            String rank = cache.getRank(uuid);
            String nextRank = RankList.valueOf(rank).getNextRank();
            List<String> ranks = plugin.getPU().getRanks();
            String nextColor = ranks.contains(nextRank) ? RankList.valueOf(nextRank).getColor() : "YELLOW";
            String nextRankPrefix = ranks.contains(nextRank) ? RankList.valueOf(nextRank).getPrefix() : "";

            int level = cache.getLevel(uuid);
            int maxLevel = plugin.getData().getAdvancementNames().size();
            int rankupLevel = RankList.valueOf(rank).getRankupLevel();
            int prestigeLevel = plugin.getData().getAdvancementNames().size();
            int expAmount = ranks.contains(nextRank) ? RankList.valueOf(nextRank).getExp() : RankList.valueOf(rank).getExp();
            long cashAmount =  ranks.contains(nextRank) ? RankList.valueOf(nextRank).getCash() : RankList.valueOf(rank).getCash();

            if (args.length < 1) {
                if (!nextRank.equals(last)) {
                    if (level < rankupLevel) {
                        List<Component> lines = new ArrayList<>();
                        Component title = Lang.COMMAND_RANKUP_TITLE.getComponent(null).hoverEvent(Lang.COMMAND_RANKUP_HOVER.getComponent(new String[]{plugin.getPU().formatAmount(level), plugin.getPU().formatAmount(maxLevel)}));
                        lines.add(Component.text(""));
                        lines.add(plugin.getPU().formatC("&a&lAdvancements&7: &8[" + plugin.getPU().getProgressBar(level, rankupLevel, 50, "|", "&#00E835", "&7") + "&8] &2" + plugin.getPU().formatAmount(level) + "&7/&2" + plugin.getPU().formatAmount(rankupLevel)));
                        if (!nextRank.equals(staff)) {
                            lines.add(Component.text(""));
                            lines.add(plugin.getPU().formatC("&9&lNext Rank&7: " + nextRankPrefix));
                        }
                        lines.add(Component.text(""));
                        lines.add(plugin.getPU().formatC("&2&lRewards&7:"));
                        lines.add(plugin.getPU().formatC("&e- &aEXP&7: &e" + plugin.getPU().formatAmount(expAmount)));
                        lines.add(plugin.getPU().formatC("&e- &aCash&7: &6$" + plugin.getPU().formatAmount(cashAmount)));
                        lines.add(Component.text(""));
                        lines.add(Lang.COMMAND_RANKUP_SPLITTER.getComponent(null));

                        player.sendMessage(title);
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
                        plugin.getPU().updateDisplayName(player);
                        player.giveExp(expAmount);
                        cache.deposit(uuid, cashAmount);
                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_BROADCAST.getComponent(new String[]{player.getName(), nextRankPrefix})));
                        for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_RANKUP_CONFIRM.getComponent(new String[] { plugin.getPU().formatAmount(level), plugin.getPU().formatAmount(rankupLevel) })));

            } else if (args[0].equalsIgnoreCase("prestige")) {
                if (level >= prestigeLevel) {
                    if (nextRank.equals(last) || nextRank.equals(staff)) {
                        String playerNextPrestige = String.valueOf(cache.getPrestige(uuid) + 1);
                        cache.addPrestige(uuid);
                        cache.setLevel(uuid, "0");

                        if (!nextRank.equals(staff)) {
                            if (!player.hasPermission("essentials.command.namecolor")) cache.setColor(uuid, nextColor);
                            cache.setRank(uuid, "NOMAD");
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
                        plugin.getPU().updateDisplayName(player);
                        player.giveExp(expAmount);
                        cache.deposit(uuid, cashAmount);
                        plugin.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.COMMAND_RANKUP_PRESTIGE_BROADCAST.getComponent(new String[]{player.getName(), playerNextPrestige})));
                        for (Player oPlayer : Bukkit.getOnlinePlayers()) oPlayer.playSound(oPlayer.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_PRESTIGE.getComponent(new String[] { plugin.getPU().formatAmount(level), plugin.getPU().formatAmount(prestigeLevel) })));

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
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKUP_CHECK.getComponent(new String[] { plugin.getPU().formatAmount(levelCheck), plugin.getPU().formatAmount(maxLevel) })));
            }
        }
        return true;
    }
}
