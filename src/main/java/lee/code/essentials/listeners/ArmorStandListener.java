package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.menus.ArmorStandMenu;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class ArmorStandListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onArmorStandInteract(PlayerInteractAtEntityEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        UUID targetUUID = e.getRightClicked().getUniqueId();

        if (!e.isCancelled()) {
            if (e.getRightClicked() instanceof ArmorStand && e.getPlayer().isSneaking()) {
                e.setCancelled(true);
                if (plugin.getData().isArmorStandActive(targetUUID)) {
                    player.sendActionBar(Lang.ERROR_ARMOR_STAND_EDIT.getComponent(null));
                } else {
                    plugin.getData().setArmorStandActive(uuid, targetUUID);
                    new ArmorStandMenu(plugin.getData().getPlayerMU(e.getPlayer().getUniqueId()), (ArmorStand) e.getRightClicked()).open();
                    player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
                }
            } else if (plugin.getData().isArmorStandActive(targetUUID)) {
                player.sendActionBar(Lang.ERROR_ARMOR_STAND_EDIT.getComponent(null));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmorStandMenuClose(InventoryCloseEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) plugin.getData().removeArmorStandActive(e.getPlayer().getUniqueId());
    }
}
