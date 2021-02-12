package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankupCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            double mine = (double) player.getStatistic(Statistic.MINE_BLOCK, Material.BARREL) / 1000;
            double craft = (double) player.getStatistic(Statistic.CRAFT_ITEM, Material.BARREL) / 1000;

            double level = ((mine + craft) / 2);

            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

            player.sendMessage(plugin.getPU().formatAmount(level));
            player.sendMessage(String.valueOf(level));
            player.sendMessage(plugin.getPU().format("#8CF136YEYEYEYE"));
            player.sendMessage(plugin.getPU().format("YEEEEEEEEEEEEEE", "#8CF136", "#71d9e2", true, false, false, false, false));

            //TODO do prefix manager system first

        }

        return true;
    }
}
