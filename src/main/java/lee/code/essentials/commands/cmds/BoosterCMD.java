package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
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
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            if (cache.areBoosters()) {
                String activeID = cache.getActiveBoosterID();
                String activeName = cache.getBoosterPlayerName(activeID);
                String activeMultiplier = cache.getBoosterMultiplier(activeID);
                List<Integer> queue = cache.getBoosterIDIntegerList();
                int number = 1;

                List<Component> lines = new ArrayList<>();

                lines.add(Lang.COMMAND_BOOSTER_TITLE.getComponent(null));
                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_BOOSTER_ACTIVE.getComponent(new String[] { activeMultiplier, activeName }).hoverEvent(Lang.COMMAND_BOOSTER_ID_HOVER.getComponent(new String[] { activeID })));
                lines.add(Component.text(""));
                if (queue.size() > 1) {
                    for (int id : queue) {
                        if (!String.valueOf(id).equals(activeID)) {
                            String qNumber = String.valueOf(number);
                            String qID = String.valueOf(id);
                            String qName = cache.getBoosterPlayerName(qID);
                            String qMultiplier = cache.getBoosterMultiplier(qID);
                            lines.add(Lang.COMMAND_BOOSTER_QUEUE.getComponent(new String[] { qNumber, qMultiplier, qName }).hoverEvent(Lang.COMMAND_BOOSTER_ID_HOVER.getComponent(new String[] { qID })));
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
