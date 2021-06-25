package lee.code.essentials.menusystem;

import lee.code.essentials.lists.MenuItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {

    public Menu(PlayerMU pmu) {
        this.pmu = pmu;
    }

    protected PlayerMU pmu;
    protected Inventory inventory;
    protected ItemStack fillerGlass = MenuItems.FILLER_GLASS.getItem();
    protected ItemStack close = MenuItems.CLOSE_MENU.getItem();
    protected ItemStack nextPage = MenuItems.NEXT_PAGE.getItem();
    protected ItemStack previousPage = MenuItems.PREVIOUS_PAGE.getItem();
    protected ItemStack asSettingTrue = MenuItems.ARMOR_STAND_SETTING_TRUE.getItem();
    protected ItemStack asSettingFalse = MenuItems.ARMOR_STAND_SETTING_FALSE.getItem();
    protected ItemStack asPosition = MenuItems.ARMOR_STAND_POSITION.getItem();
    protected ItemStack asHeadPosition = MenuItems.ARMOR_STAND_HEAD_POSITION.getItem();
    protected ItemStack asTorsoPosition = MenuItems.ARMOR_STAND_TORSO_POSITION.getItem();
    protected ItemStack asLeftArmPosition = MenuItems.ARMOR_STAND_LEFT_ARM_POSITION.getItem();
    protected ItemStack asRightArmPosition = MenuItems.ARMOR_STAND_RIGHT_ARM_POSITION.getItem();
    protected ItemStack asLeftLegPosition = MenuItems.ARMOR_STAND_LEFT_LEG_POSITION.getItem();
    protected ItemStack asRightLegPosition = MenuItems.ARMOR_STAND_RIGHT_LEG_POSITION.getItem();
    protected ItemStack asDirectionPosition = MenuItems.ARMOR_STAND_DIRECTION_POSITION.getItem();

    public abstract Component getMenuName();
    public abstract int getSlots();
    public abstract void handleMenu(InventoryClickEvent e);
    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        pmu.getOwner().openInventory(inventory);
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
}