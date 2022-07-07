package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.ItemSellValue;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
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
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            if (args.length < 1) {
                ItemStack handItem = new ItemStack(player.getInventory().getItemInMainHand());
                int stackSize = handItem.getAmount();
                handItem.setAmount(1);
                if (data.getSupportedSellItems().contains(handItem)) {
                    String name = handItem.getType().name();
                    if (handItem.hasItemMeta()) {
                        if (handItem.getItemMeta().hasDisplayName()) {
                            name = BukkitUtils.serializeComponent(handItem.getItemMeta().displayName());
                        }
                    }
                    if (ItemSellValue.valueOf(name).getItem().equals(handItem)) {
                        long value = ItemSellValue.valueOf(name).getValue();
                        long handValue = value * stackSize;
                        long inventoryValue = value * BukkitUtils.getItemAmount(player, handItem);
                        List<Component> lines = new ArrayList<>();
                        lines.add(Lang.COMMAND_WORTH_TITLE.getComponent(null));
                        lines.add(Component.text(""));
                        lines.add(Lang.COMMAND_WORTH_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseCapitalization(name), BukkitUtils.parseValue(value), BukkitUtils.parseValue(handValue), BukkitUtils.parseValue(inventoryValue) }));
                        lines.add(Component.text(""));
                        lines.add(Lang.COMMAND_WORTH_SPLITTER.getComponent(null));
                        for (Component line : lines) player.sendMessage(line);
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
            } else {
                String arg = args[0].toLowerCase();
                if (arg.equalsIgnoreCase("list")) {

                    int index;
                    int maxDisplayed = 20;
                    int page = 0;

                    if (args.length > 1) {
                        if (BukkitUtils.containOnlyNumbers(args[1])) {
                            page = Integer.parseInt(args[1]);
                        } else {
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[0] } )));
                            return true;
                        }
                    }

                    if (page < 0) return true;
                    int position = page * maxDisplayed + 1;

                    List<ItemStack> items = data.getSupportedSellItems();
                    List<Component> lines = new ArrayList<>();

                    lines.add(Lang.COMMAND_WORTH_LIST_TITLE.getComponent(null));
                    lines.add(Component.text(""));

                    for (int i = 0; i < maxDisplayed; i++) {
                        index = maxDisplayed * page + i;
                        if (index >= items.size()) break;
                        if (items.get(index) != null) {
                            Material type = items.get(index).getType();
                            lines.add(Lang.COMMAND_WORTH_LIST_LINE.getComponent(new String[] { String.valueOf(position), BukkitUtils.parseCapitalization(type.name()), BukkitUtils.parseValue(ItemSellValue.valueOf(type.name()).getValue()) }));
                            position++;
                        }
                    }

                    if (lines.size() <= 2) return true;

                    lines.add(Component.text(""));
                    Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/worth list " + (page + 1)));
                    Component split = Lang.PAGE_SPACER.getComponent(null);
                    Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/worth list " + (page - 1)));
                    lines.add(prev.append(split).append(next));
                    for (Component line : lines) player.sendMessage(line);
                }
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
