package lee.code.essentials.listeners;

import lee.code.essentials.lists.Lang;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class EntityListener implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {

        if (e.hasBlock()) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ARMOR_STAND)) {
                if (checkChunk(e.getPlayer().getLocation().getChunk(), Material.ARMOR_STAND)) {

                    TextComponent message = new TextComponent(Lang.ERROR_CHUNK_MAX_ENTITIES.getString(new String[] { String.valueOf(5) }));
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);

                    e.setCancelled(true);
                }

            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ITEM_FRAME)) {

                if (checkChunk(e.getPlayer().getLocation().getChunk(), Material.ITEM_FRAME)) {

                    TextComponent message = new TextComponent(Lang.ERROR_CHUNK_MAX_ENTITIES.getString(new String[] { String.valueOf(5) }));
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);

                    e.setCancelled(true);
                }

            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ACACIA_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.OAK_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BIRCH_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DARK_OAK_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SPRUCE_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.JUNGLE_BOAT)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.MINECART)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CHEST_MINECART)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TNT_MINECART)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.FURNACE_MINECART)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.HOPPER_MINECART)) {
                scanChunk(e.getPlayer().getLocation().getChunk());
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        scanChunk(e.getLocation().getChunk());
    }

    @EventHandler
    public void onSpawnerEntitySpawn(SpawnerSpawnEvent e) {
        scanChunk(e.getLocation().getChunk());
    }

    private boolean checkChunk(Chunk chunk, Material mat) {

        HashMap<String, Integer> entities = new HashMap<>();

        for (Entity entity : chunk.getEntities()) {
            if (!entities.containsKey(entity.getType().name())) {
                entities.put(entity.getType().name(), 1);

            } else {

                int amount = entities.get(entity.getType().name());

                if (amount >= 4) {
                    if (entity.getType().name().equals(mat.name())) {
                        return true;
                    }

                } else {
                    amount++;
                    entities.put(entity.getType().name(), amount);
                }
            }
        }
        return false;
    }

    private void scanChunk(Chunk chunk) {

        HashMap<String, Integer> entities = new HashMap<>();

        for (Entity entity : chunk.getEntities()) {
            if (!entities.containsKey(entity.getName())) {
                entities.put(entity.getName(), 1);

            } else {

                int amount = entities.get(entity.getName());

                if (amount >= 5) {

                    String customName = entity.getCustomName();
                    if (customName == null) {
                        entity.remove();
                    }

                } else {
                    amount++;
                    entities.put(entity.getName(), amount);
                }
            }
        }
    }
}
