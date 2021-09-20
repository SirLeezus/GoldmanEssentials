package lee.code.essentials.menusystem.menus;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.MenuItems;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
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
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();
        if (!(e.getClickedInventory() == player.getInventory())) {
            if (plugin.getData().hasPlayerClickDelay(uuid)) return;
            else plugin.getPU().addPlayerClickDelay(uuid);

            int slot = e.getSlot();

            switch (slot) {
                case 10 -> {
                    Location location = cache.getWorldResourceSpawn();
                    playClickSound(player);
                    if (location != null) {
                        player.teleportAsync(location);
                        player.sendActionBar(Lang.TELEPORT.getComponent(null));
                    }
                    player.getInventory().close();
                }
                case 13 -> {
                    Location location = cache.getNetherResourceSpawn();
                    playClickSound(player);
                    if (location != null) {
                        NamespacedKey key = NamespacedKey.minecraft("story/enter_the_nether");
                        Advancement advancement = plugin.getServer().getAdvancement(key);
                        if (advancement != null) {
                            if (player.getAdvancementProgress(advancement).isDone()) {
                                player.teleportAsync(location);
                                player.sendActionBar(Lang.TELEPORT.getComponent(null));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_RESOURCE_WORLD_LOCKED.getComponent(null)));
                        }
                    }
                    player.getInventory().close();
                }
                case 16 -> {
                    Location location = cache.getEndResourceSpawn();
                    playClickSound(player);
                    if (location != null) {
                        NamespacedKey key = NamespacedKey.minecraft("story/enter_the_end");
                        Advancement advancement = plugin.getServer().getAdvancement(key);
                        if (advancement != null) {
                            if (player.getAdvancementProgress(advancement).isDone()) {
                                player.teleportAsync(location);
                                player.sendActionBar(Lang.TELEPORT.getComponent(null));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_RESOURCE_WORLD_LOCKED.getComponent(null)));
                        }
                    }
                    player.getInventory().close();
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, fillerGlass);
        }
        for (int i = 18; i < 36; i++) {
            inventory.setItem(i, fillerGlass);
        }

        inventory.setItem(31, close);

        inventory.setItem(9, fillerGlass);
        inventory.setItem(11, fillerGlass);
        inventory.setItem(12, fillerGlass);
        inventory.setItem(14, fillerGlass);
        inventory.setItem(15, fillerGlass);
        inventory.setItem(17, fillerGlass);

        scheduleUpdateWorldItem(uuid);
    }

    private void scheduleUpdateWorldItem(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Data data = plugin.getData();
        PU pu = plugin.getPU();
        if (!data.isResourceWorldTaskActive(uuid)) {
            data.addResourceWorldTaskActive(uuid, new BukkitRunnable() {
                @Override
                public void run() {
                    String time = cache.isResourceWorldsResetReady() ? "&cNext Restart" : pu.formatSeconds(cache.getResourceWorldsTime());
                    ItemStack world = pu.getItem(MenuItems.RESOURCE_WORLD.getType(), MenuItems.RESOURCE_WORLD.getName(), Lang.MENU_RESOURCE_WORLD_LORE.getString(new String[] { time }), null);
                    ItemStack nether = pu.getItem(MenuItems.RESOURCE_NETHER.getType(), MenuItems.RESOURCE_NETHER.getName(), Lang.MENU_RESOURCE_WORLD_LORE.getString(new String[] { time }), null);
                    ItemStack end = pu.getItem(MenuItems.RESOURCE_END.getType(), MenuItems.RESOURCE_END.getName(), Lang.MENU_RESOURCE_WORLD_LORE.getString(new String[] { time }), null);
                    inventory.setItem(10, world);
                    inventory.setItem(13, nether);
                    inventory.setItem(16, end);
                }
            }.runTaskTimer(plugin, 1L, 20L));
        }
    }
}
