package lee.code.essentials.menusystem;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.MenuItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    protected ItemStack botChecker = MenuItems.BOT_CHECKER.getItem();

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

    private List<ItemStack> getColorItems() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        return plugin.getPU().getNameColorItems();
    }
}