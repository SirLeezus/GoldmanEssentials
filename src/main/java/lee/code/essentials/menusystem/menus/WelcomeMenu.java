package lee.code.essentials.menusystem.menus;

import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WelcomeMenu extends Menu {

    public WelcomeMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_WELCOME_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = pmu.getOwner();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        if (e.getClickedInventory() == player.getInventory()) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        if (clickedItem.equals(fillerGlass)) return;

        int slot = e.getSlot();
        player.getInventory().close();
        switch (slot) {
            case 1 -> player.chat("/rankup");
            case 3 -> player.chat("/duel");
            case 5 -> player.chat("/chunk");
            case 7 -> player.chat("/resourceworlds");
            case 19 -> player.chat("/worth list");
            case 21 -> player.chat("/pets");
            case 23 -> player.chat("/trails");
            case 25 -> player.chat("/shop daily");
            case 37 -> player.chat("/shop spawner");
            case 39 -> player.chat("/help");
            case 41 -> player.chat("/help enchants");
            case 43 -> player.chat("/lock signhelp");
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(1, welcome1);
        inventory.setItem(3, welcome2);
        inventory.setItem(5, welcome3);
        inventory.setItem(7, welcome4);
        inventory.setItem(19, welcome5);
        inventory.setItem(21, welcome6);
        inventory.setItem(23, welcome7);
        inventory.setItem(25, welcome8);
        inventory.setItem(37, welcome9);
        inventory.setItem(39, welcome10);
        inventory.setItem(41, welcome11);
        inventory.setItem(43, welcome12);
    }
}
