package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ItemLoreCMD implements CommandExecutor {

    @Override @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                Scanner numberScanner = new Scanner(args[0]);
                if (numberScanner.hasNextInt()) {
                    int number = Integer.parseInt(args[0]);
                    if (args.length > 1) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        String itemName = plugin.getPU().formatMaterial(item.getType().name());
                        if (item.getType() != Material.AIR) {
                            String message = plugin.getPU().buildStringFromArgs(args, 1);
                            ItemMeta itemMeta = item.getItemMeta();
                            if (itemMeta != null) {
                                List<String> lore = new ArrayList<>();
                                if (itemMeta.hasLore() && itemMeta.getLore() != null) {
                                    lore = new ArrayList<>(itemMeta.getLore());
                                    if (lore.size() > number) {
                                        lore.set(number, plugin.getPU().format(message));
                                    } else {
                                        for (int i = lore.size(); i < number; i++) lore.add("\n");
                                        lore.add(plugin.getPU().format(message));
                                    }
                                } else {
                                    for (int i = 0; i < number; i++) lore.add("\n");
                                    lore.add(plugin.getPU().format(message));
                                }
                                itemMeta.setLore(lore);
                                item.setItemMeta(itemMeta);
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ITEMLORE_SUCCESSFUL.getComponent(new String[] { String.valueOf(number), message, itemName })));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
