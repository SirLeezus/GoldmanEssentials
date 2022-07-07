package lee.code.essentials.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum NameColor {

    COLOR_DARK_RED(Material.RED_CONCRETE, "&4&lDark Red", null),
    COLOR_RED(Material.RED_CONCRETE_POWDER, "&c&lRed", null),
    COLOR_DARK_GREEN(Material.GREEN_CONCRETE, "&2&lDark Green", null),
    COLOR_GREEN(Material.LIME_CONCRETE, "&a&lGreen", null),
    COLOR_YELLOW(Material.YELLOW_CONCRETE, "&e&lYellow", null),
    COLOR_GOLD(Material.ORANGE_CONCRETE, "&6&lGold", null),
    COLOR_DARK_BLUE(Material.BLUE_CONCRETE, "&1&lDark Blue", null),
    COLOR_BLUE(Material.BLUE_CONCRETE_POWDER, "&9&lBlue", null),
    COLOR_DARK_AQUA(Material.CYAN_CONCRETE, "&3&lDark Aqua", null),
    COLOR_AQUA(Material.CYAN_CONCRETE_POWDER, "&b&lAqua", null),
    COLOR_DARK_PURPLE(Material.PURPLE_CONCRETE, "&5&lDark Purple", null),
    COLOR_LIGHT_PURPLE(Material.PURPLE_CONCRETE_POWDER, "&d&lLight Purple", null),

    ;

    @Getter private final Material type;
    @Getter private final String name;
    @Getter private final String lore;

    public ItemStack getItem() {
        return BukkitUtils.getItem(type, name, lore, null, true);
    }
}
