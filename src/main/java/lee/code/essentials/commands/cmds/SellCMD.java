package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.ItemSellValue;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SellCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        PU pu = plugin.getPU();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            ItemStack itemHand = new ItemStack(player.getInventory().getItemInMainHand());
            itemHand.setAmount(1);

            if (data.getSupportedSellItems().contains(itemHand)) {
                String name = itemHand.getType().name();
                if (itemHand.hasItemMeta()) if (itemHand.getItemMeta().hasDisplayName()) name = itemHand.getItemMeta().getDisplayName();
                if (ItemSellValue.valueOf(name).getItem().equals(itemHand)) {
                    int amount = player.getInventory().getItemInMainHand().getAmount();
                    long value = ItemSellValue.valueOf(name).getValue() * amount;
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    cacheManager.deposit(uuid, value);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SELL_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseCapitalization(name), String.valueOf(amount), BukkitUtils.parseValue(value) })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
