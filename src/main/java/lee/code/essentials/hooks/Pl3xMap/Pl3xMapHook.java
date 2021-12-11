package lee.code.essentials.hooks.Pl3xMap;

import lee.code.essentials.GoldmanEssentials;
import net.pl3x.map.api.Key;
import net.pl3x.map.api.Pl3xMapProvider;
import net.pl3x.map.api.SimpleLayerProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pl3xMapHook {
    private final Map<UUID, Pl3xMapTask> provider = new HashMap<>();

    public Pl3xMapHook() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Pl3xMapProvider.get().mapWorlds().forEach(world -> {
            SimpleLayerProvider provider = SimpleLayerProvider
                    .builder("Chunks")
                    .showControls(true)
                    .defaultHidden(false)
                    .build();
            world.layerRegistry().register(Key.of("chunks_" + world.uuid()), provider);
            Pl3xMapTask task = new Pl3xMapTask(world, provider);
            task.runTaskTimerAsynchronously(plugin, 20, 20L * 300);
            this.provider.put(world.uuid(), task);
        });
    }

    public void disable() {
        provider.values().forEach(Pl3xMapTask::disable);
        provider.clear();
    }
}