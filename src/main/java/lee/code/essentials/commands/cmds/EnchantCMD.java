package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class EnchantCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String enchant = args[0];
                if (plugin.getData().getEnchantNames().contains(enchant)) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() != Material.AIR) {
                        ItemMeta itemMeta = item.getItemMeta();
                        NamespacedKey key = NamespacedKey.minecraft(enchant);
                        Enchantment enchantment = Enchantment.getByKey(key);
                        if (enchantment != null) {
                            int amount = 1;
                            if (args.length > 1) {
                                Scanner valueScanner = new Scanner(args[1]);
                                if (valueScanner.hasNextInt()) amount = Integer.parseInt(args[1]);
                            }
                            itemMeta.addEnchant(enchantment, amount, true);
                            item.setItemMeta(itemMeta);
                        }
                    }
                }
            }
        }
        return true;
    }
}
