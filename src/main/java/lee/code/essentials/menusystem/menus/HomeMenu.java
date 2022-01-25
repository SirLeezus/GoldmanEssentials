package lee.code.essentials.menusystem.menus;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import lee.code.essentials.menusystem.PaginatedMenu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeMenu extends PaginatedMenu {

    public HomeMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_HOME_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        if (e.getClickedInventory() == player.getInventory()) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        if (clickedItem.equals(fillerGlass)) return;

        List<String> homes = cache.getHomes(uuid);

        if (clickedItem.equals(previousPage)) {
            if (page == 0) {
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PREVIOUS_PAGE.getString(null));
            } else {
                page = page - 1;
                pmu.setHomePage(page);
                super.open();
                playClickSound(player);
                scheduleUpdateClockItem(player, homes);
            }
        } else if (clickedItem.equals(nextPage)) {
            if (!((index + 1) >= homes.size())) {
                page = page + 1;
                pmu.setHomePage(page);
                super.open();
                playClickSound(player);
                scheduleUpdateClockItem(player, homes);
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_NEXT_PAGE.getString(null));

        } else if (clickedItem.equals(homeBed)) {
            Location bedLocation = player.getBedSpawnLocation();
            if (bedLocation != null) {
                player.teleportAsync(bedLocation);
                player.sendActionBar(Lang.TELEPORT.getComponent(null));
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TELEPORT_BED_SUCCESSFUL.getComponent(null)));
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_BED_NOT_SAVED.getComponent(null)));
            player.getInventory().close();
        } else {
            if (clickedItem.hasItemMeta()) {
                playClickSound(player);
                Location homeLocation = getItemHomeLocation(clickedItem);
                String name = getItemHomeName(clickedItem);
                if (homeLocation != null) {
                    player.teleportAsync(homeLocation);
                    player.sendActionBar(Lang.TELEPORT.getComponent(null));
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TELEPORT_SUCCESSFUL.getComponent(new String[] { name })));
                }
                player.getInventory().close();
            }
        }
    }

    @Override
    public void setMenuItems() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        Player player = pmu.getOwner();
        page = pmu.getHomePage();
        UUID uuid = player.getUniqueId();

        addMenuBorder();

        List<String> homes = cache.getHomes(uuid);
        List<ItemStack> menuItems = new ArrayList<>();
        for (String home : homes) {
            String name = pu.unFormatPlayerHomeName(home);
            String world = pu.unFormatPlayerHomeWorld(home);

            ItemStack itemHome = switch (world) {
                case "world_nether" -> new ItemStack(Material.NETHERRACK);
                case "world_the_end" -> new ItemStack(Material.END_STONE);
                default -> new ItemStack(Material.GRASS_BLOCK);
            };
            ItemMeta itemHomeMeta = itemHome.getItemMeta();
            itemHomeMeta.displayName(Lang.MENU_HOME_ITEM_HOME_NAME.getComponent(new String[] { name }));

            PersistentDataContainer container = itemHomeMeta.getPersistentDataContainer();
            NamespacedKey homeName = new NamespacedKey(plugin, "home-name");
            NamespacedKey homeLocation = new NamespacedKey(plugin, "home-location");
            container.set(homeLocation, PersistentDataType.STRING, home);
            container.set(homeName, PersistentDataType.STRING, name);

            itemHome.setItemMeta(itemHomeMeta);
            menuItems.add(itemHome);
        }

        inventory.setItem(49, homeBed);
        inventory.setItem(4, homeClock);
        scheduleUpdateClockItem(player, homes);

        if (!menuItems.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= menuItems.size()) break;
                if (menuItems.get(index) != null) {
                    ItemStack theItem = menuItems.get(index);
                    inventory.addItem(theItem);
                }
            }
        }
    }

    private Location getItemHomeLocation(ItemStack item) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "home-location");
        String sHome = container.get(key, PersistentDataType.STRING);
        return sHome != null ? plugin.getPU().unFormatPlayerHomeLocation(sHome) : null;
    }

    private String getItemHomeName(ItemStack item) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "home-name");
        return container.get(key, PersistentDataType.STRING);
    }

    private void scheduleUpdateClockItem(Player player, List<String> homes) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        UUID uuid = player.getUniqueId();
        int defaultHomes = Settings.DEFAULT_PLAYER_HOMES.getValue();
        int maxHomes = pu.getMaxHomes(player);
        int usedHomes = homes.size();
        int accruedHomes = maxHomes - defaultHomes;

        if (!data.isHomeMenuTaskActive(uuid)) {
            data.addHomeMenuTaskActive(uuid, new BukkitRunnable() {
                @Override
                public void run() {
                    long time = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
                    String timePlayed = pu.formatSeconds(time);
                    String timeRequired = pu.formatSeconds(Settings.ACCRUED_HOME_BASE_TIME_REQUIRED.getValue());
                    ItemStack clock = pu.getItem(Material.CLOCK, Lang.MENU_HOME_CLOCK_NAME.getString(), Lang.MENU_HOME_CLOCK_LORE.getString(new String[] { timePlayed, timeRequired, String.valueOf(defaultHomes), String.valueOf(accruedHomes), String.valueOf(Settings.ACCRUED_HOME_MAX.getValue()), String.valueOf(usedHomes), String.valueOf(maxHomes) }), null);
                    inventory.setItem(4, clock);
                }
            }.runTaskTimer(plugin, 1L, 20L));
        }
    }
}
