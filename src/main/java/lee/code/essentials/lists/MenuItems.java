package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum MenuItems {

    FILLER_GLASS(Material.GRAY_STAINED_GLASS_PANE, "&r", null),
    CLOSE_MENU(Material.BARRIER, "&c&lClose", null),
    NEXT_PAGE(Material.PAPER, "&eNext Page >", null),
    PREVIOUS_PAGE(Material.PAPER, "&e< Previous Page", null),

    ARMOR_STAND_SETTING_TRUE(Material.LIME_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_SETTING_FALSE(Material.RED_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_HEAD_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_TORSO_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_LEFT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_RIGHT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_LEFT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_RIGHT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null)),
    ARMOR_STAND_DIRECTION_POSITION(Material.COMPASS, "", Lang.MENU_ARMOR_STAND_DIRECTION_POSITION_LORE.getString(null)),

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
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        ItemStack item = new ItemStack(type);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (name != null) itemMeta.displayName(plugin.getPU().formatC(name));
            if (lore != null) {
                String[] split = StringUtils.split(lore, "\n");
                List<Component> lines = new ArrayList<>();
                for (String line : split) lines.add(plugin.getPU().formatC(line));
                itemMeta.lore(lines);
            }
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}
