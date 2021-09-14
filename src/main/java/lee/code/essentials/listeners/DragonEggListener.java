package lee.code.essentials.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonEggListener implements Listener {

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof EnderDragon) {
            World world = entity.getWorld();
            if (world.getEnvironment().equals(World.Environment.THE_END)) {
                Location eggLoc = new Location(world, 0, 67, 0);
                Block eggBlock = eggLoc.getBlock();
                eggBlock.setType(Material.DRAGON_EGG);
            }
        }
    }
}
