package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemNameCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                String name = BukkitUtils.buildStringFromArgs(args, 0);
                if (name.length() < 25) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() != Material.AIR) {
                        if (item.getType() != Material.SPAWNER && item.getType() != Material.PLAYER_HEAD) {
                            ItemMeta itemMeta = item.getItemMeta();
                            if (itemMeta.hasCustomModelData()) {
                                if (itemMeta.getCustomModelData() != 4000 && itemMeta.getCustomModelData() != 1337) {
                                    itemMeta.displayName(BukkitUtils.parseColorComponentLiteral(name));
                                    item.setItemMeta(itemMeta);
                                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RENAME_BLOCKED.getComponent(null)));
                            } else {
                                itemMeta.displayName(BukkitUtils.parseColorComponentLiteral(name));
                                item.setItemMeta(itemMeta);
                            }
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RENAME_BLOCKED.getComponent(null)));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RENAME_NO_ITEM.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RENAME_LENGTH.getComponent(null)));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
