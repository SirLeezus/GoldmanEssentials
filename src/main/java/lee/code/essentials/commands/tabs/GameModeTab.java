package lee.code.essentials.commands.tabs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GameModeTab implements TabCompleter {

    private final List<String> blank = new ArrayList<>();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("survival", "creative", "adventure", "spectator"), new ArrayList<>());
            } else return blank;
        } else return blank;
    }
}