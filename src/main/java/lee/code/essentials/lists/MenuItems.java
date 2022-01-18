package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum MenuItems {

    BOT_CHECKER(Material.LIME_STAINED_GLASS_PANE, Lang.MENU_BOT_CHECKER_ITEM_NAME.getString(), Lang.MENU_BOT_CHECKER_ITEM_LORE.getString()),

    FILLER_GLASS(Material.BLACK_STAINED_GLASS_PANE, "&r", null),
    TRADE_COUNT_DOWN(Material.LIME_STAINED_GLASS_PANE, "&r", null),
    NEXT_PAGE(Material.PAPER, "&eNext Page >", null),
    PREVIOUS_PAGE(Material.PAPER, "&e< Previous Page", null),

    ARMOR_STAND_SETTING_TRUE(Material.LIME_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_SETTING_FALSE(Material.RED_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_HEAD_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_TORSO_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_LEFT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_RIGHT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_LEFT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_RIGHT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_DIRECTION_POSITION(Material.COMPASS, "", Lang.MENU_ARMOR_STAND_DIRECTION_POSITION_LORE.getString()),

    RESOURCE_WORLD(Material.GRASS_BLOCK, "&a&lResource World", null),
    RESOURCE_END(Material.END_STONE, "&5&lResource End", null),
    RESOURCE_NETHER(Material.NETHERRACK, "&c&lResource Nether", null),

    HOME_BED(Material.RED_BED, "&c&lBed", null),
    HOME_CLOCK(Material.CLOCK, Lang.MENU_HOME_CLOCK_NAME.getString(), null),

    TRADE_CONFIRM_TRUE(Material.LIME_STAINED_GLASS_PANE, "&6&lTrade Confirmed&7: &aTrue", null),
    TRADE_CONFIRM_FALSE(Material.RED_STAINED_GLASS_PANE, "&6&lTrade Confirmed&7: &cFalse", null),

    ;

    @Getter private final Material type;
    @Getter private final String name;
    @Getter private final String lore;

    public ItemStack getItem() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        return plugin.getPU().getItem(type, name, lore, null);
    }
}
