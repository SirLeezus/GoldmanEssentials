package lee.code.essentials.menusystem.menus;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BotCheckerMenu extends Menu {

    public BotCheckerMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_BOT_CHECKER_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = pmu.getOwner();
        UUID uuid = player.getUniqueId();
        Cache cache = plugin.getCache();

        if (!(e.getClickedInventory() == player.getInventory())) {
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem != null) {
                if (clickedItem.equals(botChecker)) {
                    cache.setBotChecked(uuid);
                    player.closeInventory();
                    playClickSound(player);
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        int upper = 54;
        int lower = 0;
        int slot = (int) (Math.random() * (upper - lower)) + lower;
        inventory.setItem(slot, botChecker);
    }
}