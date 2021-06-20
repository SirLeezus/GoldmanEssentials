package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.ItemSellValues;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SellAllCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            ItemStack itemHand = new ItemStack(player.getInventory().getItemInMainHand());
            itemHand.setAmount(1);

            if (plugin.getPU().getSellableItems().contains(itemHand)) {
                String name = itemHand.getType().name();
                if (itemHand.hasItemMeta()) if (itemHand.getItemMeta().hasDisplayName()) name = itemHand.getItemMeta().getDisplayName();
                if (ItemSellValues.valueOf(name).getItem().equals(itemHand)) {
                    int amount = plugin.getPU().getItemAmount(player, itemHand);
                    int value = ItemSellValues.valueOf(name).getValue() * amount;
                    plugin.getPU().takeItems(player, itemHand, amount);
                    cache.deposit(uuid, value);
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_SELL_SUCCESSFUL.getString(new String[] { String.valueOf(amount), plugin.getPU().formatMaterial(name), plugin.getPU().formatAmount(value) }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_SELL_NOT_SELLABLE.getString(null));
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_SELL_NOT_SELLABLE.getString(null));
        }
        return true;
    }
}
