package lee.code.essentials.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import lee.code.essentials.menusystem.TradeMenu;
import lee.code.essentials.menusystem.menus.HomeMenu;
import lee.code.essentials.menusystem.menus.PlayerTradeMenu;
import lee.code.essentials.menusystem.menus.ResourceWorldMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        Player player = (Player) e.getWhoClicked();

        if (holder instanceof Menu menu) {
            e.setCancelled(true);
            if (BukkitUtils.hasClickDelay(player)) return;
            menu.handleMenu(e);

        } else if (holder instanceof TradeMenu tradeMenu) {
            e.setCancelled(true);
            if (BukkitUtils.hasClickDelay(player)) return;
            tradeMenu.handleMenu(e);
        }
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) e.setCancelled(true);
        else if (holder instanceof TradeMenu) e.setCancelled(true);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        InventoryHolder holder = e.getInventory().getHolder();
        Player player = (Player) e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (holder instanceof ResourceWorldMenu) {
            data.getResourceWorldTask(uuid).cancel();
            data.removeResourceWorldTaskActive(uuid);

        } else if (holder instanceof PlayerTradeMenu playerTradeMenu) {
            PlayerMU pmu = playerTradeMenu.getPmu();
            UUID owner = pmu.getOwnerUUID();
            UUID trader = pmu.getTraderUUID();
            if (owner.equals(uuid)) {
                if (pmu.isOwnerTrading()) {
                    playerTradeMenu.returnTradeItems(owner);
                    if (pmu.isTraderTrading()) pmu.getTrader().closeInventory();
                }
            } else {
                if (pmu.isTraderTrading()) {
                    playerTradeMenu.returnTradeItems(trader);
                    if (pmu.isOwnerTrading()) pmu.getOwner().closeInventory();
                }
            }
        } else  if (holder instanceof HomeMenu) {
            if (data.isHomeMenuTaskActive(uuid)) {
                data.getHomeMenuTask(uuid).cancel();
                data.removeHomeMenuTaskActive(uuid);
            }
        }
    }
}
