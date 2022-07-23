package lee.code.essentials.menusystem.menus;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class NameColorMenu extends Menu {

    public NameColorMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_COLOR_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        PU pu = plugin.getPU();

        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        if (e.getClickedInventory() == player.getInventory()) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        if (clickedItem.equals(fillerGlass)) return;

        if (colorItems.contains(clickedItem)) {
            String id = getColorID(clickedItem);
            cacheManager.setColor(uuid, id);
            pu.updateDisplayName(player, false, false);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COLOR_MENU_SELECT.getComponent(new String[] { ChatColor.valueOf(id) + BukkitUtils.parseCapitalization(id) })));
            player.getInventory().close();
        }
        playClickSound(player);
    }

    @Override
    public void setMenuItems() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, fillerGlass);
        }

        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, fillerGlass);
        }

        if (!colorItems.isEmpty()) {
            for (ItemStack colorItem : colorItems) {
                if (colorItem != null) {
                    String id = getColorID(colorItem);

                    if (cacheManager.getColor(uuid).name().equals(id)) {
                        ItemMeta itemMeta = colorItem.getItemMeta();
                        itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, false);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        colorItem.setItemMeta(itemMeta);
                    }
                    inventory.addItem(colorItem);
                }
            }
        }
    }

    private String getColorID(ItemStack item) {
        return ChatColor.stripColor(BukkitUtils.serializeComponent(item.getItemMeta().displayName()).toUpperCase().replaceAll(" ", "_"));
    }
}
