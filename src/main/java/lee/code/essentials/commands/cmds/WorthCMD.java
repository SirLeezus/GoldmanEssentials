package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.ItemSellValues;
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
import java.util.Scanner;

public class WorthCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            if (args.length < 1) {
                ItemStack itemHand = new ItemStack(player.getInventory().getItemInMainHand());
                itemHand.setAmount(1);

                if (pu.getSellableItems().contains(itemHand)) {
                    String name = itemHand.getType().name();
                    if (itemHand.hasItemMeta()) {
                        if (itemHand.getItemMeta().hasDisplayName()) {
                            name = pu.unFormatC(itemHand.getItemMeta().displayName());
                        }
                    }
                    if (ItemSellValues.valueOf(name).getItem().equals(itemHand)) {
                        long value = ItemSellValues.valueOf(name).getValue();
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WORTH_SUCCESSFUL.getComponent(new String[] { pu.formatCapitalization(name), pu.formatAmount(value) })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NOT_SELLABLE.getComponent(null)));
            } else {
                String arg = args[0].toLowerCase();
                if (arg.equals("list")) {

                    int index;
                    int maxDisplayed = 20;
                    int page = 0;

                    if (args.length > 1) {
                        Scanner numberScanner = new Scanner(args[1]);
                        if (numberScanner.hasNextInt()) {
                            page = Integer.parseInt(args[1]);
                        } else {
                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_LIST_PAGE_NOT_NUMBER.getComponent(new String[]{ args[0] } )));
                            return true;
                        }
                    }

                    if (page < 0) return true;
                    int position = page * maxDisplayed + 1;

                    List<ItemStack> items = pu.getSellableItems();
                    List<Component> lines = new ArrayList<>();

                    lines.add(Lang.COMMAND_WORTH_LIST_TITLE.getComponent(null));
                    lines.add(Component.text(""));

                    for (int i = 0; i < maxDisplayed; i++) {
                        index = maxDisplayed * page + i;
                        if (index >= items.size()) break;
                        if (items.get(index) != null) {
                            Material type = items.get(index).getType();
                            lines.add(Lang.COMMAND_WORTH_LIST_LINE.getComponent(new String[] { String.valueOf(position), pu.formatCapitalization(type.name()), pu.formatAmount(ItemSellValues.valueOf(type.name()).getValue()) }));
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
