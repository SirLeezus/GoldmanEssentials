package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.List;

public class ItemLoreCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (BukkitUtils.containOnlyNumbers(args[0])) {
                    int number = Integer.parseInt(args[0]);
                    if (args.length > 1) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        String itemName = BukkitUtils.parseCapitalization(item.getType().name());
                        if (item.getType() != Material.AIR) {
                            String message = "&5&o" + BukkitUtils.buildStringFromArgs(args, 1);
                            ItemMeta itemMeta = item.getItemMeta();
                            if (itemMeta != null) {
                                List<Component> lore = new ArrayList<>();
                                if (itemMeta.hasLore() && itemMeta.lore() != null) {
                                    lore = new ArrayList<>(Objects.requireNonNull(itemMeta.lore()));
                                    if (lore.size() > number) {
                                        lore.set(number, BukkitUtils.parseColorComponent(message));
                                    } else {
                                        for (int i = lore.size(); i < number; i++) lore.add(Component.text(""));
                                        lore.add(BukkitUtils.parseColorComponent(message));
                                    }
                                } else {
                                    for (int i = 0; i < number; i++) lore.add(Component.text(""));
                                    lore.add(BukkitUtils.parseColorComponent(message));
                                }
                                itemMeta.lore(lore);
                                item.setItemMeta(itemMeta);
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ITEMLORE_SUCCESSFUL.getComponent(new String[] { String.valueOf(number), message, itemName })));
                            }
                        }
                    } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
                } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
