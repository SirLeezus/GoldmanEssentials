package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BottleEXPListener implements Listener {

    @EventHandler
    public void onShiftClickBottle(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Player player = e.getPlayer();
        if (player.isSneaking()) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.getType().equals(Material.GLASS_BOTTLE)) {
                int amount = handItem.getAmount();
                int exp = plugin.getPU().getPlayerExp(player);
                int requiredEXP = 8;
                if (exp > requiredEXP) {
                    e.setCancelled(true);
                    int convertEXPBottleAmount = exp / requiredEXP;
                    if (convertEXPBottleAmount > amount) convertEXPBottleAmount = amount;

                    int glassBottleAmount = amount - convertEXPBottleAmount;

                    if (glassBottleAmount > 0) player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE, glassBottleAmount));
                    else player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

                    ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
                    int space = plugin.getPU().getFreeSpace(player, expBottle);
                    expBottle.setAmount(convertEXPBottleAmount);

                    if (space > convertEXPBottleAmount) player.getInventory().addItem(expBottle);
                    else player.getLocation().getWorld().dropItemNaturally(player.getLocation(), expBottle);

                    player.setExp(0);
                    player.setLevel(0);
                    player.giveExp(exp - requiredEXP * convertEXPBottleAmount);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
                } else player.sendActionBar(Lang.ERROR_EXP_BOTTLE_POINTS.getComponent(null));
            }
        }
    }
}
