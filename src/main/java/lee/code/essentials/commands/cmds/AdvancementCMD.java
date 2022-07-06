package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdvancementCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 2) {
                if (BukkitUtils.getOnlinePlayers().contains(args[1])) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        String subCommand = args[0].toLowerCase();
                        switch (subCommand) {
                            case "grant":
                                if (!args[2].equalsIgnoreCase("-all")) {
                                    NamespacedKey key = NamespacedKey.minecraft(args[2]);
                                    Advancement advancement = Bukkit.getAdvancement(key);
                                    if (advancement != null) {
                                        AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                        for (String criteria : progress.getRemainingCriteria()) progress.awardCriteria(criteria);
                                    }
                                } else {
                                    for (String sKey : plugin.getData().getAdvancementNames()) {
                                        NamespacedKey key = NamespacedKey.minecraft(sKey);
                                        Advancement advancement = Bukkit.getAdvancement(key);
                                        if (advancement != null) {
                                            AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                            for (String criteria : progress.getRemainingCriteria()) progress.awardCriteria(criteria);
                                        }
                                    }
                                }
                                break;

                            case "revoke":
                                if (!args[2].equalsIgnoreCase("-all")) {
                                    NamespacedKey key = NamespacedKey.minecraft(args[2]);
                                    Advancement advancement = Bukkit.getAdvancement(key);
                                    if (advancement != null) {
                                        AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                        for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
                                    }
                                } else {
                                    for (String sKey : plugin.getData().getAdvancementNames()) {
                                        NamespacedKey key = NamespacedKey.minecraft(sKey);
                                        Advancement advancement = Bukkit.getAdvancement(key);
                                        if (advancement != null) {
                                            AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                            for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
                                        }
                                    }
                                }
                                break;

                            default:
                                //TODO send message not subcommand
                                break;
                        }
                    } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
