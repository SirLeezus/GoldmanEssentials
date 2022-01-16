package lee.code.essentials.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class GiantListener implements Listener {

    @EventHandler
    public void onGiantSpawn(CreatureSpawnEvent e) {
        CreatureSpawnEvent.SpawnReason spawnReason = e.getSpawnReason();
        if (!spawnReason.equals(CreatureSpawnEvent.SpawnReason.CUSTOM) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.EGG)) {
            Entity entity = e.getEntity();
            World world = entity.getWorld();
            Location location = entity.getLocation();
            if (world.getName().equals("world_resource")) {
                if (entity instanceof Zombie) {
                    if ((int) (Math.random() * 20) == 1) { // 5% chance
                        world.spawnEntity(location, EntityType.GIANT);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onIllusionerSpawn(CreatureSpawnEvent e) {
        CreatureSpawnEvent.SpawnReason spawnReason = e.getSpawnReason();
        if (!spawnReason.equals(CreatureSpawnEvent.SpawnReason.CUSTOM) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) && !spawnReason.equals(CreatureSpawnEvent.SpawnReason.EGG)) {
            Entity entity = e.getEntity();
            World world = entity.getWorld();
            Location location = entity.getLocation();
            if (world.getName().equals("world_resource")) {
                if (entity instanceof Pillager) {
                    if ((int) (Math.random() * 10) == 1) { // 10% chance
                        world.spawnEntity(location, EntityType.ILLUSIONER);
                    }
                }
            }
        }
    }
}
