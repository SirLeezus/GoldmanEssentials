package lee.code.essentials.menusystem;

import lee.code.essentials.lists.MenuItem;
import lee.code.essentials.lists.NameColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Menu implements InventoryHolder {

    public Menu(PlayerMU pmu) {
        this.pmu = pmu;
    }

    protected PlayerMU pmu;
    protected Inventory inventory;

    protected ItemStack botChecker = MenuItem.BOT_CHECKER.getItem();

    protected ItemStack fillerGlass = MenuItem.FILLER_GLASS.getItem();
    protected ItemStack nextPage = MenuItem.NEXT_PAGE.getItem();
    protected ItemStack previousPage = MenuItem.PREVIOUS_PAGE.getItem();

    protected ItemStack asSettingTrue = MenuItem.ARMOR_STAND_SETTING_TRUE.getItem();
    protected ItemStack asSettingFalse = MenuItem.ARMOR_STAND_SETTING_FALSE.getItem();
    protected ItemStack asPosition = MenuItem.ARMOR_STAND_POSITION.getItem();
    protected ItemStack asHeadPosition = MenuItem.ARMOR_STAND_HEAD_POSITION.getItem();
    protected ItemStack asTorsoPosition = MenuItem.ARMOR_STAND_TORSO_POSITION.getItem();
    protected ItemStack asLeftArmPosition = MenuItem.ARMOR_STAND_LEFT_ARM_POSITION.getItem();
    protected ItemStack asRightArmPosition = MenuItem.ARMOR_STAND_RIGHT_ARM_POSITION.getItem();
    protected ItemStack asLeftLegPosition = MenuItem.ARMOR_STAND_LEFT_LEG_POSITION.getItem();
    protected ItemStack asRightLegPosition = MenuItem.ARMOR_STAND_RIGHT_LEG_POSITION.getItem();
    protected ItemStack asDirectionPosition = MenuItem.ARMOR_STAND_DIRECTION_POSITION.getItem();

    protected ItemStack resourceWorld = MenuItem.RESOURCE_WORLD.getItem();
    protected ItemStack resourceEnd = MenuItem.RESOURCE_END.getItem();
    protected ItemStack resourceNether = MenuItem.RESOURCE_NETHER.getItem();

    protected ItemStack homeBed = MenuItem.HOME_BED.getItem();
    protected ItemStack homeClock = MenuItem.HOME_CLOCK.getItem();

    protected ItemStack welcome1 = MenuItem.WELCOME_1.getItem();
    protected ItemStack welcome2 = MenuItem.WELCOME_2.getItem();
    protected ItemStack welcome3 = MenuItem.WELCOME_3.getItem();
    protected ItemStack welcome4 = MenuItem.WELCOME_4.getItem();
    protected ItemStack welcome5 = MenuItem.WELCOME_5.getItem();
    protected ItemStack welcome6 = MenuItem.WELCOME_6.getItem();
    protected ItemStack welcome7 = MenuItem.WELCOME_7.getItem();
    protected ItemStack welcome8 = MenuItem.WELCOME_8.getItem();
    protected ItemStack welcome9 = MenuItem.WELCOME_9.getItem();
    protected ItemStack welcome10 = MenuItem.WELCOME_10.getItem();
    protected ItemStack welcome11 = MenuItem.WELCOME_11.getItem();
    protected ItemStack welcome12 = MenuItem.WELCOME_12.getItem();

    protected List<ItemStack> colorItems = getColorItems();

    public abstract Component getMenuName();
    public abstract int getSlots();
    public abstract void handleMenu(InventoryClickEvent e);
    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        if (pmu.getOwner() != null) pmu.getOwner().openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass() {
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerGlass);
            }
        }
    }

    public void playClickSound(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, (float) 0.5, (float) 1);
    }

    public void playClickOnSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, (float) 0.5, (float) 1);
    }

    public void playClickOffSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 0.5, (float) 1);
    }

    private List<ItemStack> getColorItems() {
        return EnumSet.allOf(NameColor.class).stream().map(NameColor::getItem).collect(Collectors.toList());
    }
}