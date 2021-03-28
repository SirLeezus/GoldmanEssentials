package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemRenameCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

            if (args.length > 0) {
                String name = plugin.getPU().buildStringFromArgs(args, 0);
                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType() != Material.AIR) {
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.displayName(Component.text(plugin.getPU().format(name)));
                    //TODO for testing
                    itemMeta.setCustomModelData(1);
                    item.setItemMeta(itemMeta);
                }
            }
        }
        return true;
    }
}
