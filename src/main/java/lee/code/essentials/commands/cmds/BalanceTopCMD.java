package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.files.defaults.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class BalanceTopCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();

            if (player.hasPermission("essentials.command.balancetop")) {
                List<Integer> balances = SQL.getBalanceTopValues();
                List<UUID> players = SQL.getBalanceTopPlayers();

                player.sendMessage(Lang.COMMAND_BALANCETOP_TITLE.getConfigValue(null));
                player.sendMessage("");

                int number = 1;
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).equals(uuid)) player.sendMessage(Lang.COMMAND_BALANCETOP_SUCCESSFUL.getConfigValue(new String[] { plugin.getPluginUtility().format("&2" + SQL.getBalanceTopRank(uuid)), plugin.getPluginUtility().format("&a&l" +  player.getName()), plugin.getPluginUtility().formatAmount(SQL.getBalance(uuid)) }));
                    else player.sendMessage(Lang.COMMAND_BALANCETOP_SUCCESSFUL.getConfigValue(new String[] { plugin.getPluginUtility().format("&e" + number), plugin.getPluginUtility().format("&b&l" +  Bukkit.getOfflinePlayer(players.get(i)).getName()), plugin.getPluginUtility().formatAmount(balances.get(i)) }));
                    number++;
                }

                if (!players.contains(uuid)) player.sendMessage(Lang.COMMAND_BALANCETOP_SUCCESSFUL.getConfigValue(new String[] { plugin.getPluginUtility().format("&2" + SQL.getBalanceTopRank(uuid)), plugin.getPluginUtility().format("&a&l" +  player.getName()), plugin.getPluginUtility().formatAmount(SQL.getBalance(uuid)) }));
                player.sendMessage("");
                player.sendMessage(Lang.COMMAND_BALANCETOP_SPLITTER.getConfigValue(null));
            }
        }
        return true;
    }
}
