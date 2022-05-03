package lee.code.essentials.menusystem;

import lee.code.essentials.lists.MenuItems;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class TradeMenu implements InventoryHolder {

    public TradeMenu(PlayerMU pmu, UUID trading) {
        this.pmu = pmu;
        this.trading = trading;
    }

    @Getter protected PlayerMU pmu;
    protected Inventory inventory;
    @Getter protected UUID trading;

    protected ItemStack fillerGlass = MenuItems.FILLER_GLASS.getItem();
    protected ItemStack tradeCountDown = MenuItems.TRADE_COUNT_DOWN.getItem();
    protected ItemStack confirmTradeTrue = MenuItems.TRADE_CONFIRM_TRUE.getItem();
    protected ItemStack confirmTradeFalse = MenuItems.TRADE_CONFIRM_FALSE.getItem();

    public abstract Component getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        Player owner = pmu.getOwner();
        Player trader = Bukkit.getPlayer(trading);
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        if (owner != null && trader != null) {
            owner.openInventory(inventory);
            trader.openInventory(inventory);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setTradeMenu() {
        for (int i = 0; i < 10; i++) inventory.setItem(i, fillerGlass);
        for (int i = 44; i < 54; i++) inventory.setItem(i, fillerGlass);

        //right
        inventory.setItem(17, fillerGlass);
        inventory.setItem(26, fillerGlass);
        inventory.setItem(35, fillerGlass);

        //left
        inventory.setItem(18, fillerGlass);
        inventory.setItem(27, fillerGlass);
        inventory.setItem(36, fillerGlass);

        //middle
        inventory.setItem(13, fillerGlass);
        inventory.setItem(22, fillerGlass);
        inventory.setItem(31, fillerGlass);
        inventory.setItem(40, fillerGlass);

        //panels
        inventory.setItem(47, confirmTradeFalse);
        inventory.setItem(51, confirmTradeFalse);
    }

    public void playPingSound(Player player, int pitch) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, (float) 0.5, (float) pitch);
    }

    public void playVillagerYesSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, (float) 0.5, (float) 1);
    }

    public void playVillagerNoSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, (float) 0.5, (float) 1);
    }

    public void playClickOnSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, (float) 0.5, (float) 1);
    }

    public void playClickOffSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 0.5, (float) 1);
    }
}