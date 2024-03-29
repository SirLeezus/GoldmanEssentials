package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            List<Component> lines = new ArrayList<>();
            Component spacer = Component.text("");
            lines.add(Lang.COMMAND_VOTE_TITLE.getComponent(null));
            lines.add(spacer);
            lines.add(Lang.COMMAND_VOTE_TOTAL_VOTES.getComponent(new String[]{ String.valueOf(cacheManager.getVotes(uuid)) }));
            lines.add(Lang.COMMAND_VOTE_WEBSITE.getComponent(null).append(BukkitUtils.parseColorComponent(" &6[&cLINK&6]").hoverEvent(BukkitUtils.parseColorComponent("&6Click to preview link!")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://www.planetminecraft.com/server/journey-survival-5279490"))));
            lines.add(Lang.COMMAND_VOTE_REWARDS.getComponent(null));
            lines.add(spacer);
            lines.add(Lang.COMMAND_VOTE_SPLITTER.getComponent(null));

            for (Component line : lines) player.sendMessage(line);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
