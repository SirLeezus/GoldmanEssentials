package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemInfoCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = handItem.getItemMeta();
            Component id = Component.text(handItem.getType().name()).color(NamedTextColor.YELLOW);
            Component displayName = itemMeta.hasDisplayName() ? itemMeta.displayName() : Component.empty();
            Component spacer = Component.text(" ");
            Component downSpacer = BukkitUtils.parseColorComponent("\n");
            Component lore = Component.empty();
            Component enchantments = Component.empty();
            Component durability = Component.empty();

            if (displayName != null) {
                List<Component> lines = new ArrayList<>();

                if (itemMeta.hasLore()) for (Component loreLine : Objects.requireNonNull(itemMeta.lore())) lore = lore.append(BukkitUtils.parseColorComponent("\n&5&o")).append(loreLine);
                if (itemMeta.hasEnchants() || itemMeta instanceof EnchantmentStorageMeta) {
                    Map<Enchantment, Integer> enchants = itemMeta instanceof EnchantmentStorageMeta book ? book.getStoredEnchants() : itemMeta.getEnchants();
                    for (Map.Entry<Enchantment, Integer> enchant : enchants.entrySet()) {
                        Component enchantment = BukkitUtils.parseColorComponent(BukkitUtils.parseCapitalization(enchant.getKey().getKey().getKey()));
                        Component enchantmentLevel = enchant.getKey().getMaxLevel() > 1 ? BukkitUtils.parseColorComponent(BukkitUtils.getRomanNumber(enchant.getValue())) : Component.empty();
                        Component newEnchantLine = downSpacer.append(enchantment.append(spacer).append(enchantmentLevel)).color(enchant.getKey().isCursed() ? NamedTextColor.RED : NamedTextColor.GRAY);
                        enchantments = enchantments.append(newEnchantLine);
                    }
                }
                if (itemMeta instanceof Damageable damageable) {
                    int maxDam = handItem.getType().getMaxDurability();
                    int dam = maxDam - damageable.getDamage();
                    durability = BukkitUtils.parseColorComponent("&e" + dam + "/" + maxDam);
                }

                lines.add(Lang.COMMAND_ITEM_INFO_TITLE.getComponent(null));
                lines.add(spacer);
                lines.add(BukkitUtils.parseColorComponent("&6&lItem ID: ").append(id));
                lines.add(BukkitUtils.parseColorComponent("&6&lDisplay Name: ").append(displayName));
                lines.add(BukkitUtils.parseColorComponent("&6&lLore: ").append(lore));
                lines.add(BukkitUtils.parseColorComponent("&6&lEnchantments: ").append(enchantments));
                lines.add(BukkitUtils.parseColorComponent("&6&lDurability: ").append(durability));
                lines.add(spacer);
                lines.add(Lang.COMMAND_ITEM_INFO_SPLITTER.getComponent(null));

                for (Component line : lines) player.sendMessage(line);
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
