package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
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

public class EnchantCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

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
                                if (pu.containOnlyNumbers(args[1])) amount = Integer.parseInt(args[1]);
                                itemMeta.addEnchant(enchantment, amount, true);
                            } else if (itemMeta.hasEnchant(enchantment)) {
                                itemMeta.removeEnchant(enchantment);
                            } else {
                                itemMeta.addEnchant(enchantment, amount, true);
                            }
                            item.setItemMeta(itemMeta);
                        }
                    }
                }
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
    } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
