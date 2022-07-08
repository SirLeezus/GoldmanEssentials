package lee.code.essentials.managers;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Setting;
import net.pl3x.map.plugin.configuration.WorldConfig;
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
            WorldCreator wcWorld = new WorldCreator("world_resource_golden");
            wcWorld.environment(World.Environment.NORMAL);
            wcWorld.seed(worldSeed);
            World world = wcWorld.createWorld();

            WorldCreator wcNether = new WorldCreator("nether_resource_golden");
            wcNether.environment(World.Environment.NETHER);
            wcNether.seed(netherSeed);
            World nether = wcNether.createWorld();

            WorldCreator wcEnd = new WorldCreator("end_resource_golden");
            wcEnd.environment(World.Environment.THE_END);
            wcEnd.seed(endSeed);
            World end = wcEnd.createWorld();

            if (world != null && nether != null && end != null) {
                cacheManager.setResourceResetTime(Setting.RESOURCE_WORLD_RESET.getValue());
                Bukkit.getServer().unloadWorld(world, false);
                Bukkit.getServer().unloadWorld(nether, false);
                Bukkit.getServer().unloadWorld(end, false);
                copyWorld(world, "world_resource");
                copyWorld(nether, "nether_resource");
                copyWorld(end, "end_resource");
            }
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

    private void copyWorld(World originalWorld, String newWorldName) {
        copyWorldFolder(originalWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
        WorldCreator wcWorld = new WorldCreator(newWorldName);
        wcWorld.environment(originalWorld.getEnvironment());
        wcWorld.seed(originalWorld.getSeed());
        disablePl3xMapWorld(wcWorld.createWorld());
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

    private void disablePl3xMapWorld(World world) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        if (plugin.isPl3xMapInstalled()) {
            WorldConfig worldConfig = WorldConfig.get(world);
            worldConfig.MAP_ENABLED = false;
        }
    }
}
