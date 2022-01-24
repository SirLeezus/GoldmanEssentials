package lee.code.essentials.menusystem.menus;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
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
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Player player = pmu.getOwner();

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        if (e.getClickedInventory() == player.getInventory()) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        if (clickedItem.equals(fillerGlass)) return;

        int slot = e.getSlot();

        switch (slot) {
            case 28 -> {
                Location location = cache.getWorldResourceSpawn();
                playClickSound(player);
                if (location != null) {
                    player.teleportAsync(location);
                    player.sendActionBar(Lang.TELEPORT.getComponent(null));
                }
                player.getInventory().close();
            }
            case 31 -> {
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
            case 34 -> {
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
        Cache cache = plugin.getCache();
        Data data = plugin.getData();
        PU pu = plugin.getPU();
        if (!data.isResourceWorldTaskActive(uuid)) {
            data.addResourceWorldTaskActive(uuid, new BukkitRunnable() {
                @Override
                public void run() {
                    long seconds = cache.getResourceWorldsTime();
                    String time = seconds <= 0 ? "&cNext Restart" : pu.formatSeconds(seconds);
                    ItemStack clock = pu.getItem(Material.CLOCK, Lang.MENU_RESOURCE_WORLD_CLOCK_NAME.getString(new String[] { time }), null, null);
                    inventory.setItem(13, clock);
                }
            }.runTaskTimer(plugin, 1L, 20L));
        }
    }
}
