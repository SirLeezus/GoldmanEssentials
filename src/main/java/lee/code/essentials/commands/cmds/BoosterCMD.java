package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoosterCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            if (cacheManager.areBoosters()) {
                int activeID = cacheManager.getActiveBoosterID();
                String activeName = cacheManager.getBoosterPlayerName(activeID);
                int activeMultiplier = cacheManager.getBoosterMultiplier(activeID);
                List<Integer> queue = cacheManager.getBoosterIDList();
                int number = 1;

                List<Component> lines = new ArrayList<>();

                lines.add(Lang.COMMAND_BOOSTER_TITLE.getComponent(null));
                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_BOOSTER_ACTIVE.getComponent(new String[] { String.valueOf(activeMultiplier), activeName }).hoverEvent(Lang.COMMAND_BOOSTER_ID_HOVER.getComponent(new String[] { String.valueOf(activeID) })));
                lines.add(Component.text(""));
                if (queue.size() > 1) {
                    for (int id : queue) {
                        if (id != activeID) {
                            String qName = cacheManager.getBoosterPlayerName(id);
                            int qMultiplier = cacheManager.getBoosterMultiplier(id);
                            lines.add(Lang.COMMAND_BOOSTER_QUEUE.getComponent(new String[] { String.valueOf(number), String.valueOf(qMultiplier), qName }).hoverEvent(Lang.COMMAND_BOOSTER_ID_HOVER.getComponent(new String[] { String.valueOf(id) })));
                            number++;
                        }
                    }
                    lines.add(Component.text(""));
                }
                lines.add(Lang.COMMAND_BOOSTER_SPLITTER.getComponent(null));

                for (Component line : lines) player.sendMessage(line);
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_BOOSTER.getComponent(null)));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));

        return true;
    }
}
