package lee.code.essentials.commands.tabs;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeleportAcceptTab implements TabCompleter {

    private final List<String> blank = new ArrayList<>();
    
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], plugin.getPU().getOnlinePlayers(), new ArrayList<>());
            } else return blank;
        } else return blank;
    }
}