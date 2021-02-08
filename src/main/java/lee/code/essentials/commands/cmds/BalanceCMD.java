package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BalanceCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();

            if (player.hasPermission("essentials.command.balance")) {
                player.sendMessage(Lang.COMMAND_BALANCE_SUCCESSFUL.getString(new String[] { plugin.getPU().formatAmount(SQL.getBalance(uuid)) }));
            }
        }
        return true;
    }
}
