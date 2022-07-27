package lee.code.essentials.menusystem.menus;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ResourceWorldMenu extends Menu {

    public ResourceWorldMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_RESOURCE_WORLDS_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Player player = pmu.getOwner();

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        if (e.getClickedInventory() == player.getInventory()) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        if (clickedItem.equals(fillerGlass)) return;

        int slot = e.getSlot();

        switch (slot) {
            case 28 -> {
                Location location = cacheManager.getWorldResourceSpawn();
                playClickSound(player);
                if (location != null) {
                    player.teleportAsync(location).thenAccept(result -> player.sendActionBar(Lang.TELEPORT.getComponent(null)));
                }
                inventory.close();
            }
            case 31 -> {
                Location location = cacheManager.getNetherResourceSpawn();
                playClickSound(player);
                if (location != null) {
                    player.teleportAsync(location).thenAccept(result -> player.sendActionBar(Lang.TELEPORT.getComponent(null)));
                }
                inventory.close();
            }
            case 34 -> {
                Location location = cacheManager.getEndResourceSpawn();
                playClickSound(player);
                if (location != null) {
                    player.teleportAsync(location).thenAccept(result -> player.sendActionBar(Lang.TELEPORT.getComponent(null)));
                }
                inventory.close();
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();

        setFillerGlass();

        inventory.setItem(28, resourceWorld);
        inventory.setItem(31, resourceNether);
        inventory.setItem(34, resourceEnd);

        scheduleUpdateClockItem(uuid);
    }

    private void scheduleUpdateClockItem(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        Data data = plugin.getData();
        if (!data.isResourceWorldTaskActive(uuid)) {
            data.addResourceWorldTaskActive(uuid, new BukkitRunnable() {
                @Override
                public void run() {
                    long seconds = cacheManager.getResourceResetTime();
                    String time = seconds <= 0 ? "&cNext Restart" : BukkitUtils.parseSeconds(seconds);
                    ItemStack clock = BukkitUtils.getItem(Material.CLOCK, Lang.MENU_RESOURCE_WORLD_CLOCK_NAME.getString(new String[] { time }), null, null, true);
                    inventory.setItem(13, clock);
                }
            }.runTaskTimer(plugin, 1L, 20L));
        }
    }
}
