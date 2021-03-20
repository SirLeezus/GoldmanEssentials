package lee.code.essentials.commands.tabs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ZapTab implements TabCompleter {

    private final List<String> blank = new ArrayList<>();
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return blank;
    }
}