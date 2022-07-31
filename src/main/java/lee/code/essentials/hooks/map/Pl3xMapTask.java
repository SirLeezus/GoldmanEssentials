package lee.code.essentials.hooks.map;

import lee.code.chunks.database.tables.AdminChunkTable;
import lee.code.chunks.database.tables.ChunkTable;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
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
import java.util.*;
import java.util.List;

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
        if (stop) cancel();
        updateClaims();
    }

    void updateClaims() {
        //player chunks
        List<ChunkTable> chunks = GoldmanEssentials.getPlugin().getChunkAPI().getAllChunkData();
        List<Claim> claims = chunks.stream()
                .filter(c -> isCurrentWorld(c.getChunk()))
                .map(dataChunk -> new Claim(
                        getChunkX(dataChunk.getChunk()),
                        getChunkZ(dataChunk.getChunk()),
                        dataChunk.getOwner(),
                        false
                )).toList();
        claims.forEach(this::drawChunk);

        //admin chunks
        List<AdminChunkTable> adminChunks = GoldmanEssentials.getPlugin().getChunkAPI().getAllAdminChunkData();
        List<Claim> adminClaims = adminChunks.stream()
                .filter(c -> isCurrentWorld(c.getChunk()))
                .map(dataChunk -> new Claim(
                        getChunkX(dataChunk.getChunk()),
                        getChunkZ(dataChunk.getChunk()),
                        UUID.fromString(Lang.SERVER_UUID.getString(null)),
                        true
                )).toList();
        adminClaims.forEach(this::drawChunk);
    }

    public void drawChunk(Claim claim) {
        int minX = claim.getX() << 4;
        int maxX = (claim.getX() + 1) << 4;
        int minZ = claim.getZ() << 4;
        int maxZ = (claim.getZ() + 1) << 4;

        Rectangle rect = Marker.rectangle(Point.of(minX, minZ), Point.of(maxX, maxZ));
        MarkerOptions.Builder options = options(claim.getOwner(), claim.isAdminChunk());
        rect.markerOptions(options);

        String markerID = "chunks_" + world.name() + "_chunk_" + minX + "_" + minZ;
        if (provider.hasMarker(Key.of(markerID))) {
            provider.removeMarker(Key.of(markerID));
            provider.addMarker(Key.of(markerID), rect);
        } else {
            provider.addMarker(Key.of(markerID), rect);
        }
    }

    public void removeChunk(Claim claim) {
        int minX = claim.getX() << 4;
        int minZ = claim.getZ() << 4;
        String markerID = "chunks_" + world.name() + "_chunk_" + minX + "_" + minZ;
        if (provider.hasMarker(Key.of(markerID))) provider.removeMarker(Key.of(markerID));
    }

    private MarkerOptions.Builder options(UUID owner, boolean isAdminChunk) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        Color color = isAdminChunk ? Color.RED : ChunkColors.valueOf(cacheManager.getColor(owner).name()).getColor();
        OfflinePlayer player = Bukkit.getOfflinePlayer(owner);
        String ownerName = player.getName() == null ? "Unknown" : player.getName();
        String clickMessage = isAdminChunk ? "Admin Chunk" : "{owner}'s Chunk".replace("{owner}", ownerName);
        return MarkerOptions.builder()
                .strokeColor(color)
                .strokeWeight(2)
                .strokeOpacity(0.75D)
                .fillColor(color)
                .fillOpacity(0.2D)
                .clickTooltip(clickMessage)
                ;
    }

    public void disable() {
        cancel();
        this.stop = true;
        this.provider.clearMarkers();
    }

    public int getChunkX(String chunk) {
        String[] split = chunk.split(",", 3);
        return Integer.parseInt(split[1]);
    }

    public int getChunkZ(String chunk) {
        String[] split = chunk.split(",", 3);
        return Integer.parseInt(split[2]);
    }

    private boolean isCurrentWorld(String chunk) {
        String[] split = chunk.split(",", 3);
        return split[0].equals(world.name());
    }
}