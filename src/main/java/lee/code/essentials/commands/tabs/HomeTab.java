package lee.code.essentials.commands.tabs;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeTab implements TabCompleter {

    private final List<String> blank = new ArrayList<>();
    
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], cacheManager.getHomeNames(player.getUniqueId()), new ArrayList<>());
            } else return blank;
        } else return blank;
    }
}