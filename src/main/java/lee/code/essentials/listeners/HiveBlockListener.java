package lee.code.essentials.listeners;

import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HiveBlockListener implements Listener {

    @EventHandler
    public void onPlayerHiveInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasBlock()) {
            if (player.isSneaking()) {
                ItemStack handItem = player.getInventory().getItemInMainHand();
                if (handItem.getType().equals(Material.AIR)) {
                    Block block = e.getClickedBlock();
                    if (block != null) {
                        if (block.getState() instanceof Beehive beehive) {
                            int bees = beehive.getEntityCount();
                            player.sendActionBar(Lang.BEEHIVE.getComponent(new String[] { String.valueOf(bees) }));
                        }
                    }
                }
            }
        }
    }
}
