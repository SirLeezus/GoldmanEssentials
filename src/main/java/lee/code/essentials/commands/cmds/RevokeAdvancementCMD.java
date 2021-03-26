package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RevokeAdvancementCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.revokeadvancement")) {

                if (args.length > 1) {
                    if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (!args[1].equalsIgnoreCase("-all")) {
                                NamespacedKey key = NamespacedKey.minecraft(args[1]);
                                Advancement advancement = Bukkit.getAdvancement(key);
                                if (advancement != null) {
                                    AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                    for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
                                }
                            } else {
                                for (String sKey : plugin.getData().getGameAdvancements()) {
                                    NamespacedKey key = NamespacedKey.minecraft(sKey);
                                    Advancement advancement = Bukkit.getAdvancement(key);
                                    if (advancement != null) {
                                        AdvancementProgress progress = target.getAdvancementProgress(advancement);
                                        for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
