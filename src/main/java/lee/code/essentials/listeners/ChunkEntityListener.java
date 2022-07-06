package lee.code.essentials.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
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
        if (!e.getReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            if (plugin.getPU().countEntitiesInChunk(chunk, e.getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Chunk chunk = e.getLocation().getChunk();
        Entity entity = e.getEntity();

        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            if (plugin.getPU().countEntitiesInChunk(chunk, entity.getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkUnloadEntity(ChunkUnloadEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Chunk chunk = e.getChunk();

        for (Entity entity : chunk.getEntities()) {
            if (!(entity instanceof Item)) if (plugin.getPU().countEntitiesInChunk(chunk, entity.getType()) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
        }
    }

    @EventHandler
    public void onEntityPlace(PlayerInteractEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        if (e.hasBlock()) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            Material type = item.getType();
            Chunk chunk = e.getPlayer().getChunk();
            Player player =  e.getPlayer();

            switch (type) {
                case SPRUCE_BOAT, OAK_BOAT, BIRCH_BOAT, DARK_OAK_BOAT, ACACIA_BOAT, JUNGLE_BOAT, MANGROVE_BOAT -> {
                    if (pu.countEntitiesInChunk(chunk, EntityType.BOAT) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                        e.setCancelled(true);
                        player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                    }
                }
                case SPRUCE_CHEST_BOAT, OAK_CHEST_BOAT, BIRCH_CHEST_BOAT, DARK_OAK_CHEST_BOAT, ACACIA_CHEST_BOAT, JUNGLE_CHEST_BOAT, MANGROVE_CHEST_BOAT -> {
                    if (pu.countEntitiesInChunk(chunk, EntityType.CHEST_BOAT) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                        e.setCancelled(true);
                        player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                    }
                }
                case ITEM_FRAME -> {
                    if (pu.countEntitiesInChunk(chunk, EntityType.ITEM_FRAME) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                        e.setCancelled(true);
                        player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                    }
                }
                case GLOW_ITEM_FRAME -> {
                    if (pu.countEntitiesInChunk(chunk, EntityType.GLOW_ITEM_FRAME) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                        e.setCancelled(true);
                        player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                    }
                }
                case ARMOR_STAND -> {
                    if (pu.countEntitiesInChunk(chunk, EntityType.ARMOR_STAND) >= Settings.MAX_ENTITY_PER_CHUNK.getValue()) {
                        e.setCancelled(true);
                        player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Settings.MAX_ENTITY_PER_CHUNK.getValue()) }));
                    }
                }
            }
        }
    }
}
