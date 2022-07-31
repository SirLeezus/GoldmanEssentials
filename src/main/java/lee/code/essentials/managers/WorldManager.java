package lee.code.essentials.managers;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Setting;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.util.logging.Level;

public class WorldManager {

    public void resourceWorldResets() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        long worldSeed = -561772;
        long netherSeed = -3589987965699307043L;
        long endSeed = -3589987965699307043L;

        if (cacheManager.isResourceResetReady()) {
            cacheManager.setResourceResetTime(Setting.RESOURCE_WORLD_RESET.getValue());
            copyWorld("./world_resource_golden", World.Environment.NORMAL, worldSeed, "world_resource");
            copyWorld("./nether_resource_golden", World.Environment.NETHER, netherSeed, "nether_resource");
            copyWorld("./end_resource_golden", World.Environment.THE_END, endSeed, "end_resource");

        } else {
            WorldCreator wcWorld = new WorldCreator("world_resource");
            wcWorld.environment(World.Environment.NORMAL);
            wcWorld.seed(worldSeed);
            wcWorld.createWorld();
            Bukkit.getLogger().log(Level.INFO, BukkitUtils.parseColorString("&aWorld Loaded: &6" + wcWorld.name()));

            WorldCreator wcNether = new WorldCreator("nether_resource");
            wcNether.environment(World.Environment.NETHER);
            wcNether.seed(netherSeed);
            wcNether.createWorld();
            Bukkit.getLogger().log(Level.INFO, BukkitUtils.parseColorString("&aWorld Loaded: &6" + wcNether.name()));

            WorldCreator wcEnd = new WorldCreator("end_resource");
            wcEnd.environment(World.Environment.THE_END);
            wcEnd.seed(endSeed);
            wcEnd.createWorld();
            Bukkit.getLogger().log(Level.INFO, BukkitUtils.parseColorString("&aWorld Loaded: &6" + wcEnd.name()));
        }
    }

    private void copyWorld(String folder, World.Environment environment, long seed, String newWorldName) {
        copyWorldFolder(new File(folder), new File(Bukkit.getWorldContainer(), newWorldName));
        WorldCreator wcWorld = new WorldCreator(newWorldName);
        wcWorld.environment(environment);
        wcWorld.seed(seed);
        wcWorld.createWorld();
        Bukkit.getLogger().log(Level.INFO, BukkitUtils.parseColorString("&2World Created: &6" + newWorldName));
    }

    private void copyWorldFolder(File source, File target) {
        try {
            FileUtils.deleteDirectory(target);
            FileUtils.copyDirectory(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
