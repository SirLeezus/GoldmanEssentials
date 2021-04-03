package lee.code.essentials.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

public class ChunkEntityListener implements Listener {

    @EventHandler
    public void onEntityPreSpawn(PreCreatureSpawnEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Chunk chunk = e.getSpawnLocation().getChunk();
        if (plugin.getPU().countEntitiesInChunk(chunk, e.getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Chunk chunk = e.getLocation().getChunk();
        if (plugin.getPU().countEntitiesInChunk(chunk, e.getEntity().getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
    }

    @EventHandler
    public void onChunkUnloadEntity(ChunkUnloadEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Chunk chunk = e.getChunk();

        for (Entity entity : chunk.getEntities()) {
            if (plugin.getPU().countEntitiesInChunk(chunk, entity.getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
        }
    }

    @EventHandler
    public void onEntityPlace(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        if (e.hasBlock()) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            Chunk chunk = e.getPlayer().getChunk();
            Player player =  e.getPlayer();

            if (item.getType().name().contains("BOAT")) {
                if (plugin.getPU().countEntitiesInChunk(chunk, EntityType.BOAT) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                    e.setCancelled(true);
                    player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                }
            } else if (item.getType().equals(Material.ITEM_FRAME)) {
                if (plugin.getPU().countEntitiesInChunk(chunk, EntityType.ITEM_FRAME) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                    e.setCancelled(true);
                    player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                }
            } else if (item.getType().equals(Material.ARMOR_STAND)) {
                if (plugin.getPU().countEntitiesInChunk(chunk, EntityType.ARMOR_STAND) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                    e.setCancelled(true);
                    player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                }
            }
        }
    }
}
