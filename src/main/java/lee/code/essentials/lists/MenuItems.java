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
