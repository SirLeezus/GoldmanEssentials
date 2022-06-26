package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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

import java.util.*;

public class CheckAdvancementCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            Component splitter = Component.text("");
            if (args.length > 0) {
                NamespacedKey key = NamespacedKey.minecraft(args[0]);
                Advancement advancement = Bukkit.getAdvancement(key);
                if (advancement != null) {
                    LinkedHashSet<Component> lines = new LinkedHashSet<>();
                    lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_TITLE.getComponent(null));
                    lines.add(splitter);
                    AdvancementProgress progress = player.getAdvancementProgress(advancement);
                    if (progress.getAdvancement().getDisplay() != null) {
                        Component title = progress.getAdvancement().getDisplay().title().color(NamedTextColor.GREEN);
                        Component description = progress.getAdvancement().getDisplay().description().color(NamedTextColor.GREEN);
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_TITLE.getComponent(null).append(title));
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_DESCRIPTION.getComponent(null).append(description).append(BukkitUtils.parseColorComponent("&a.")));
                        lines.add(splitter);
                        for (String criteriaCompleted : progress.getAwardedCriteria()) lines.add(BukkitUtils.parseColorComponent("&e• &6" + BukkitUtils.parseCapitalization(criteriaCompleted.replaceAll("minecraft:", "")) + " &a✔"));
                        for (String criteriaRemaining : progress.getRemainingCriteria()) lines.add(BukkitUtils.parseColorComponent("&e• &6" + BukkitUtils.parseCapitalization(criteriaRemaining.replaceAll("minecraft:", "")) + " &c✗"));
                        lines.add(splitter);
                        lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_SPLITTER.getComponent(null));
                        for (Component line : lines) player.sendMessage(line);
                    }
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_CHECK_ADVANCEMENT_NOT_ADVANCEMENT.getComponent(new String[] { args[0] })));
            } else {
                HashMap<Component, Boolean> advancementMap = new HashMap<>();
                LinkedHashSet<Component> lines = new LinkedHashSet<>();
                lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_TITLE.getComponent(null));
                lines.add(splitter);

                for (String sKey : plugin.getData().getAdvancementNames()) {
                    NamespacedKey key = NamespacedKey.minecraft(sKey);
                    Advancement advancement = Bukkit.getAdvancement(key);
                    if (advancement != null && advancement.getDisplay() != null) {
                        if (player.getAdvancementProgress(advancement).isDone()) {
                            Component title = advancement.getDisplay().title().color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/checkadvancement " + advancement.getKey().getKey()))
                                    .hoverEvent(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_TITLE_HOVER.getComponent(new String[] { BukkitUtils.serializeComponent(advancement.getDisplay().title()) }))
                                    .append(BukkitUtils.parseColorComponent(" &a✔"));
                            advancementMap.put(title, true);
                        } else {
                            Component title = advancement.getDisplay().title().color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/checkadvancement " + advancement.getKey().getKey()))
                                    .hoverEvent(Lang.COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_TITLE_HOVER.getComponent(new String[] { BukkitUtils.serializeComponent(advancement.getDisplay().title()) }))
                                    .append(BukkitUtils.parseColorComponent(" &c✗"));
                            advancementMap.put(title, false);
                        }
                    }
                }

                int count = 1;
                for (Component component : advancementMap.keySet()) {
                    if (advancementMap.get(component)) {
                        lines.add(BukkitUtils.parseColorComponent("&3" + count + "&b. ").append(component));
                        count++;
                    }
                }
                for (Component component : advancementMap.keySet()) {
                    if (!advancementMap.get(component)) {
                        lines.add(BukkitUtils.parseColorComponent("&3" + count + "&b. ").append(component));
                        count++;
                    }
                }
                lines.add(splitter);
                lines.add(Lang.COMMAND_CHECK_ADVANCEMENT_SPLITTER.getComponent(null));

                for (Component line : lines) player.sendMessage(line);
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
