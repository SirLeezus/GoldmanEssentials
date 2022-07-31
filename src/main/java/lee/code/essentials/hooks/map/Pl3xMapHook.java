package lee.code.essentials.hooks.map;

import lee.code.essentials.GoldmanEssentials;
import net.pl3x.map.api.Key;
import net.pl3x.map.api.MapWorld;
import net.pl3x.map.api.Pl3xMapProvider;
import net.pl3x.map.api.SimpleLayerProvider;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pl3xMapHook {
    private final Map<UUID, Pl3xMapTask> providerMap = new HashMap<>();

    public Pl3xMapHook() {
        Pl3xMapProvider.get().mapWorlds().forEach(world -> {
            SimpleLayerProvider layerProvider = SimpleLayerProvider.builder("Claimed Chunks")
                    .showControls(true)
                    .defaultHidden(false)
                    .build();
            world.layerRegistry().register(Key.of("chunks_" + world.uuid()), layerProvider);
            Pl3xMapTask task = new Pl3xMapTask(world, layerProvider);
            task.runTaskTimerAsynchronously(GoldmanEssentials.getPlugin(), 20, 20L * 300);
            providerMap.put(world.uuid(), task);
        });
    }

    public void disable() {
        providerMap.values().forEach(Pl3xMapTask::disable);
        providerMap.clear();
    }

    public void drawChunk(String chunk, UUID uuid, boolean adminChunk) {
        Pl3xMapProvider.get().mapWorlds().forEach(world -> {
            if (isChunkWorld(world, chunk)) {
                Pl3xMapTask task = providerMap.get(world.uuid());
                Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () ->
                        task.drawChunk(new Claim(task.getChunkX(chunk), task.getChunkZ(chunk), uuid, adminChunk)));
            }
        });
    }

    public void removeChunk(String chunk, UUID uuid, boolean adminChunk) {
        Pl3xMapProvider.get().mapWorlds().forEach(world -> {
            if (isChunkWorld(world, chunk)) {
                Pl3xMapTask task = providerMap.get(world.uuid());
                Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () ->
                        task.removeChunk(new Claim(task.getChunkX(chunk), task.getChunkZ(chunk), uuid, adminChunk)));
            }
        });
    }

    private boolean isChunkWorld(MapWorld world, String chunk) {
        String[] split = chunk.split(",", 3);
        return split[0].equals(world.name());
    }
}