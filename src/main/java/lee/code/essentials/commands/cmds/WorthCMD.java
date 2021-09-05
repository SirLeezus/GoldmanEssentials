package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.ItemSellValues;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorthCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length < 1) {
                ItemStack itemHand = new ItemStack(player.getInventory().getItemInMainHand());
                itemHand.setAmount(1);

                if (plugin.getPU().getSellableItems().contains(itemHand)) {
                    String name = itemHand.getType().name();
                    if (itemHand.hasItemMeta()) {
                        if (itemHand.getItemMeta().hasDisplayName()) {
                            name = plugin.getPU().unFormatC(itemHand.getItemMeta().displayName());
                        }
                    }
                    if (ItemSellValues.valueOf(name).getItem().equals(itemHand)) {
                        long value = ItemSellValues.valueOf(name).getValue();
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WORTH_SUCCESSFUL.getComponent(new String[] { plugin.getPU().formatCapitalization(name), plugin.getPU().formatAmount(value) })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
            } else {
                String arg = args[0].toLowerCase();
                if (arg.equals("list")) {
                    List<Component> lines = new ArrayList<>();
                    lines.add(Lang.COMMAND_WORTH_LIST_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    int number = 1;
                    for (ItemSellValues id : ItemSellValues.values()) {
                        lines.add(Lang.COMMAND_WORTH_LIST_LINE.getComponent(new String[] { String.valueOf(number), plugin.getPU().formatCapitalization(id.getItem().getType().name()), plugin.getPU().formatAmount(id.getValue()) }));
                        number++;
                    }
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_WORTH_LIST_SPLITTER.getComponent(null));
                    for (Component line : lines) player.sendMessage(line);
                }
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
