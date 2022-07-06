package lee.code.essentials.menusystem.menus;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.managers.CountdownTimer;
import lee.code.essentials.menusystem.PlayerMU;
import lee.code.essentials.menusystem.TradeMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.time.Duration;
import java.util.UUID;

public class PlayerTradeMenu extends TradeMenu {

    public PlayerTradeMenu(PlayerMU pmu, UUID trading) {
        super(pmu, trading);
        pmu.setTrading(trading);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_TRADE_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player owner = pmu.getOwner();
        Player trader = pmu.getTrader();
        Player whoClicked = (Player) e.getWhoClicked();

        UUID ownerUUID = owner.getUniqueId();
        UUID whoClickedUUID = whoClicked.getUniqueId();

        ItemStack clickedItem = e.getCurrentItem();

        if (!(e.getClickedInventory() == owner.getInventory())) {
            if (!(e.getClickedInventory() == trader.getInventory())) {
                int slot = e.getSlot();
                switch (slot) {
                    case 10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39 -> {
                        if (whoClickedUUID.equals(trading)) {
                            if (!pmu.isTraderTradeConfirmed()) e.setCancelled(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY));
                        }
                    }
                    case 14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43  -> {
                        if (whoClickedUUID.equals(ownerUUID)) {
                            if (!pmu.isOwnerTradeConfirmed()) e.setCancelled(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY));
                        }
                    }
                    case 47 -> {
                        if (clickedItem != null) {
                            if (whoClickedUUID.equals(trading)) {
                                if (clickedItem.equals(confirmTradeFalse)) {
                                    inventory.setItem(47, confirmTradeTrue);
                                    pmu.setTraderTradeConfirmed(true);
                                    checkBothConfirmed();
                                    playClickOnSound(trader);
                                } else if (clickedItem.equals(confirmTradeTrue)) {
                                    if (pmu.isTraderTradeConfirmed() && pmu.isOwnerTradeConfirmed()) {
                                        owner.closeInventory();
                                        trader.closeInventory();
                                        playVillagerNoSound(owner);
                                        playVillagerNoSound(trader);
                                    } else {
                                        inventory.setItem(47, confirmTradeFalse);
                                        pmu.setTraderTradeConfirmed(false);
                                        checkBothConfirmed();
                                        playClickOffSound(trader);
                                    }
                                }
                            }
                        }
                    }
                    case 51 -> {
                        if (clickedItem != null) {
                            if (whoClickedUUID.equals(ownerUUID)) {
                                if (clickedItem.equals(confirmTradeFalse)) {
                                    inventory.setItem(51, confirmTradeTrue);
                                    pmu.setOwnerTradeConfirmed(true);
                                    checkBothConfirmed();
                                    playClickOnSound(owner);
                                } else if (clickedItem.equals(confirmTradeTrue)) {
                                    if (pmu.isTraderTradeConfirmed() && pmu.isOwnerTradeConfirmed()) {
                                        owner.closeInventory();
                                        trader.closeInventory();
                                        playVillagerNoSound(owner);
                                        playVillagerNoSound(trader);
                                    } else {
                                        inventory.setItem(51, confirmTradeFalse);
                                        pmu.setOwnerTradeConfirmed(false);
                                        checkBothConfirmed();
                                        playClickOffSound(owner);
                                    }
                                }
                            }
                        }
                    }
                }
            } else e.setCancelled(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY));
        } else e.setCancelled(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY));

    }

    @Override
    public void setMenuItems() {
        setTradeMenu();

        Player owner = pmu.getOwner();
        Player trader = pmu.getTrader();
        pmu.setOwnerTradeConfirmed(false);
        pmu.setTraderTradeConfirmed(false);
        pmu.setOwnerTrading(true);
        pmu.setTraderTrading(true);

        ItemStack ownerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta ownerSkullMeta = (SkullMeta) ownerHead.getItemMeta();
        ownerSkullMeta.setOwningPlayer(owner);
        ownerSkullMeta.displayName(BukkitUtils.parseColorComponent("&6" + owner.getName()));
        ownerHead.setItemMeta(ownerSkullMeta);

        ItemStack traderHead = new ItemStack(Material.PLAYER_HEAD);;
        SkullMeta traderSkullMeta = (SkullMeta) traderHead.getItemMeta();
        traderSkullMeta.setOwningPlayer(trader);
        traderSkullMeta.displayName(BukkitUtils.parseColorComponent("&6" + trader.getName()));
        traderHead.setItemMeta(traderSkullMeta);

        inventory.setItem(6, ownerHead);
        inventory.setItem(2, traderHead);
    }

    private void checkBothConfirmed() {
        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(3000), Duration.ofMillis(1000));

        if (pmu.isTraderTradeConfirmed() && pmu.isOwnerTradeConfirmed()) {
            CountdownTimer timer = new CountdownTimer(GoldmanEssentials.getPlugin(),
                    6,
                    () -> {},
                    () -> {
                        Player owner = pmu.getOwner();
                        Player trader = pmu.getTrader();

                        if (pmu.isTraderTradeConfirmed() && pmu.isOwnerTradeConfirmed()) {
                            int[] ownerSlots = new int[]{14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43};
                            int[] traderSlots = new int[]{10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};

                            pmu.setOwnerTrading(false);
                            pmu.setTraderTrading(false);

                            for (int slot : ownerSlots) {
                                ItemStack item = inventory.getItem(slot);
                                if (item != null && !item.getType().equals(Material.AIR)) {
                                    if (BukkitUtils.getFreeSpace(trader, item) >= item.getAmount()) {
                                        trader.getInventory().addItem(item);
                                    } else trader.getWorld().dropItemNaturally(trader.getLocation(), item);
                                }
                            }

                            for (int slot : traderSlots) {
                                ItemStack item = inventory.getItem(slot);
                                if (item != null && !item.getType().equals(Material.AIR)) {
                                    if (BukkitUtils.getFreeSpace(owner, item) >= item.getAmount()) {
                                        owner.getInventory().addItem(item);
                                    } else owner.getWorld().dropItemNaturally(owner.getLocation(), item);
                                }
                            }

                            owner.closeInventory();
                            trader.closeInventory();
                            playVillagerYesSound(owner);
                            playVillagerYesSound(trader);
                            owner.showTitle(Title.title(Lang.TRADE_COMPLETED_TITLE.getComponent(null), Lang.TRADE_COMPLETED_SUBTITLE.getComponent(new String[] { trader.getName() }), times));
                            trader.showTitle(Title.title(Lang.TRADE_COMPLETED_TITLE.getComponent(null), Lang.TRADE_COMPLETED_SUBTITLE.getComponent(new String[] { owner.getName() }), times));
                        }
                    },
                    (t) -> {
                        Player owner = pmu.getOwner();
                        Player trader = pmu.getTrader();
                        if (pmu.isTraderTradeConfirmed() && pmu.isOwnerTradeConfirmed()) {

                            switch (t.getSecondsLeft()) {
                                case 6 -> {
                                    inventory.setItem(4, tradeCountDown);
                                    playPingSound(owner, 1);
                                    playPingSound(trader, 1);
                                }
                                case 5 -> {
                                    inventory.setItem(13, tradeCountDown);
                                    playPingSound(owner, 1);
                                    playPingSound(trader, 1);
                                }
                                case 4 -> {
                                    inventory.setItem(22, tradeCountDown);
                                    playPingSound(owner, 1);
                                    playPingSound(trader, 1);
                                }
                                case 3 -> {
                                    inventory.setItem(31, tradeCountDown);
                                    playPingSound(owner, 1);
                                    playPingSound(trader, 1);
                                }
                                case 2 -> {
                                    inventory.setItem(40, tradeCountDown);
                                    playPingSound(owner, 1);
                                    playPingSound(trader, 1);
                                }
                                case 1 -> {
                                    inventory.setItem(49, tradeCountDown);
                                    playPingSound(owner, 2);
                                    playPingSound(trader, 2);
                                }
                            }
                        } else {
                            t.stop();
                        }
                    });
            timer.scheduleTimer();
        }
    }

    public void returnTradeItems(UUID user) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(3000), Duration.ofMillis(1000));
        boolean isOwner = pmu.getOwnerUUID().equals(user);
        int[] userSlots = isOwner ? new int[]{14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43} : new int[]{10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};

        Player userPlayer = Bukkit.getPlayer(user);
        Player owner = pmu.getOwner();
        Player trader = pmu.getTrader();

        if (userPlayer == null) return;

        ItemStack air = new ItemStack(Material.AIR);

        for (int slot : userSlots) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && !item.getType().equals(Material.AIR)) {
                if (BukkitUtils.getFreeSpace(userPlayer, item) >= item.getAmount()) {
                    userPlayer.getInventory().addItem(item);
                } else userPlayer.getWorld().dropItemNaturally(userPlayer.getLocation(), item);
                inventory.setItem(slot, air);
            }
        }
        userPlayer.updateInventory();
        if (isOwner) {
            pmu.setOwnerTradeConfirmed(false);
            pmu.setOwnerTrading(false);
            userPlayer.showTitle(Title.title(Lang.TRADE_FAILED_TITLE.getComponent(null), Lang.TRADE_FAILED_SUBTITLE.getComponent(new String[] { trader.getName() }), times));
        } else {
            pmu.setTraderTradeConfirmed(false);
            pmu.setTraderTrading(false);
            userPlayer.showTitle(Title.title(Lang.TRADE_FAILED_TITLE.getComponent(null), Lang.TRADE_FAILED_SUBTITLE.getComponent(new String[] { owner.getName() }), times));
        }
        playVillagerNoSound(userPlayer);
    }
}
