package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.menus.BotCheckerMenu;
import lee.code.essentials.menusystem.menus.ResourceWorldMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu menu) {
            e.setCancelled(true);
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof BotCheckerMenu) {
            Player player = (Player) e.getPlayer();
            UUID uuid = player.getUniqueId();
            if (!cache.hasBeenBotChecked(uuid)) {
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.runTaskLater(plugin, () -> new BotCheckerMenu(plugin.getData().getPlayerMU(uuid)).open(), 10);
            }
        } else if (holder instanceof ResourceWorldMenu) {
            Player player = (Player) e.getPlayer();
            UUID uuid = player.getUniqueId();
            data.getResourceWorldTask(uuid).cancel();
            data.removeResourceWorldTaskActive(uuid);
        }
    }
}
