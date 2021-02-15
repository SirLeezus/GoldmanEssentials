package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRenameCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

            if (player.hasPermission("essentials.command.balance")) {

                if (args.length > 0) {
                    String name = plugin.getPU().buildStringFromArgs(args, 0);
                    ItemStack item = player.getInventory().getItemInMainHand();

                    if (item.getType() != Material.AIR) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setDisplayName(plugin.getPU().format(name));
                        item.setItemMeta(itemMeta);
                    }
                }
            }
        }
        return true;
    }
}
