package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CheckAdvancementCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                NamespacedKey key = NamespacedKey.minecraft(args[0]);
                Advancement advancement = Bukkit.getAdvancement(key);
                if (advancement != null) {
                    List<Component> lines = new ArrayList<>();
                    Component splitter = Component.text("");
                    lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_TITLE.getComponent(null));
                    lines.add(splitter);
                    AdvancementProgress progress = player.getAdvancementProgress(advancement);
                    if (progress.getAdvancement().getDisplay() != null) {
                        Component title = progress.getAdvancement().getDisplay().title().color(NamedTextColor.GREEN);
                        Component description = progress.getAdvancement().getDisplay().description().color(NamedTextColor.GREEN);
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_TITLE.getComponent(null).append(title));
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_DESCRIPTION.getComponent(null).append(description).append(pu.formatC("&a.")));
                        lines.add(splitter);
                        for (String criteriaCompleted : progress.getAwardedCriteria()) lines.add(pu.formatC("&e• &6" + pu.formatCapitalization(criteriaCompleted.replaceAll("minecraft:", "")) + " &a✔"));
                        for (String criteriaRemaining : progress.getRemainingCriteria()) lines.add(pu.formatC("&e• &6" + pu.formatCapitalization(criteriaRemaining.replaceAll("minecraft:", "")) + " &c✗"));
                        lines.add(splitter);
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_SPLITTER.getComponent(null));
                        for (Component line : lines) player.sendMessage(line);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_CHECK_ADVANCEMENT_NOT_ADVANCEMENT.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
