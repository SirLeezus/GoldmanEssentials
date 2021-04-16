package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.EntityHeads;
import lee.code.essentials.lists.Settings;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadDropListener implements Listener {

    @EventHandler
    public void onHeadDrop(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                headMeta.setOwningPlayer(player);
                head.setItemMeta(headMeta);
                player.getWorld().dropItemNaturally(player.getLocation(), head);
            } else if (e.getEntity() instanceof EnderDragon) {
                ItemStack dragonHead = new ItemStack(Material.DRAGON_HEAD);
                if (!e.getDrops().contains(dragonHead)) e.getDrops().add(dragonHead);
            } else {
                boolean creative = false;
                if (e.getEntity().getKiller() != null) {
                    Player killer = e.getEntity().getKiller();
                    if (killer.getGameMode().equals(GameMode.CREATIVE)) creative = true;
                }
                if (plugin.getPU().rng() >= Settings.HEAD_DROP_RNG.getValue() || creative) {
                    String type = e.getEntityType().name();
                    if (e.getEntity() instanceof Sheep) {
                        Sheep sheep = (Sheep) e.getEntity();
                        DyeColor color = sheep.getColor();
                        if (color != null) type = sheep.getColor().name() + "_" + type;
                        else type = "WHITE_" + type;
                    }
                    if (plugin.getPU().getEntityHeads().contains(type)) {
                        ItemStack head = EntityHeads.valueOf(type).getHead();
                        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), head);
                    }
                }
            }
        }
    }
}
