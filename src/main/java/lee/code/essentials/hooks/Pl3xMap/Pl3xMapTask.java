package lee.code.essentials.hooks.Pl3xMap;

import lee.code.chunks.ChunkAPI;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import net.pl3x.map.api.Key;
import net.pl3x.map.api.MapWorld;
import net.pl3x.map.api.Point;
import net.pl3x.map.api.SimpleLayerProvider;
import net.pl3x.map.api.marker.Marker;
import net.pl3x.map.api.marker.MarkerOptions;
import net.pl3x.map.api.marker.Rectangle;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class Pl3xMapTask extends BukkitRunnable {
    private final MapWorld world;
    private final SimpleLayerProvider provider;
    private boolean stop;

    public Pl3xMapTask(MapWorld world, SimpleLayerProvider provider) {
        this.world = world;
        this.provider = provider;
    }

    @Override
    public void run() {
        if (stop) {
            cancel();
        }
        updateClaims();
    }

    void updateClaims() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        ChunkAPI chunkAPI = plugin.getChunkAPI();
        provider.clearMarkers(); // TODO track markers instead of clearing them
        for (UUID uuid : chunkAPI.getUserList()) {
            List<String> chunks = chunkAPI.getChunks(uuid);
            if (!chunks.get(0).equals("")) {
                for (String chunk : chunks) {
                    if (isCurrentWorld(chunk)) drawChunk(chunk, uuid);
                }
            }
        }
    }

    private void drawChunk(String chunk, UUID uuid) {
        int minX = getChunkX(chunk) << 4;
        int maxX = (getChunkX(chunk) + 1) << 4;
        int minZ = getChunkZ(chunk) << 4;
        int maxZ = (getChunkZ(chunk) + 1) << 4;

        Rectangle rect = Marker.rectangle(Point.of(minX, minZ), Point.of(maxX, maxZ));
        MarkerOptions.Builder options = options(uuid);
        rect.markerOptions(options);

        String markerID = "chunks_" + world.name() + "_chunk_" + minX + "_" + minZ;
        this.provider.addMarker(Key.of(markerID), rect);
    }

    private MarkerOptions.Builder options(UUID owner) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        Color color = ChunkColors.valueOf(cache.getColor(owner)).getColor();
        OfflinePlayer player = Bukkit.getOfflinePlayer(owner);
        String ownerName = player.getName() == null ? "unknown" : player.getName();
        return MarkerOptions.builder()
                .strokeColor(color)
                .strokeWeight(2)
                .strokeOpacity(0.75D)
                .fillColor(color)
                .fillOpacity(0.2D)
                .clickTooltip("{owner}'s Chunk".replace("{owner}", ownerName)
                );
    }

    public void disable() {
        cancel();
        this.stop = true;
        this.provider.clearMarkers();
    }

    private int getChunkX(String chunk) {
        String[] split = chunk.split(",", 3);
        return Integer.parseInt(split[1]);
    }

    private int getChunkZ(String chunk) {
        String[] split = chunk.split(",", 3);
        return Integer.parseInt(split[2]);
    }

    private boolean isCurrentWorld(String chunk) {
        String[] split = chunk.split(",", 3);
        return split[0].equals(world.name());
    }
}