package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Settings;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.logging.Level;

public class WorldManager {

    public void resourceWorldResets() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        long worldSeed = 7882078983362791734L;
        long netherSeed = -3441507048707832380L;
        long endSeed = 7691349409794918337L;

        if (cache.isResourceWorldsResetReady()) {
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
                cache.setResourceWorldsTime(Settings.RESOURCE_WORLD_RESET.getValue());
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
            Bukkit.getLogger().log(Level.INFO, pu.format("&aWorld Loaded: &6" + wcWorld.name()));

            WorldCreator wcNether = new WorldCreator("nether_resource");
            wcNether.environment(World.Environment.NETHER);
            wcNether.seed(netherSeed);
            wcNether.createWorld();
            Bukkit.getLogger().log(Level.INFO, pu.format("&aWorld Loaded: &6" + wcNether.name()));

            WorldCreator wcEnd = new WorldCreator("end_resource");
            wcEnd.environment(World.Environment.THE_END);
            wcEnd.seed(endSeed);
            wcEnd.createWorld();
            Bukkit.getLogger().log(Level.INFO, pu.format("&aWorld Loaded: &6" + wcEnd.name()));
        }
    }

    private void copyWorld(World originalWorld, String newWorldName) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        copyWorldFolder(originalWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
        WorldCreator wcWorld = new WorldCreator(newWorldName);
        wcWorld.environment(originalWorld.getEnvironment());
        wcWorld.seed(originalWorld.getSeed());
        wcWorld.createWorld();
        Bukkit.getLogger().log(Level.INFO, pu.format("&2World Created: &6" + newWorldName));
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
