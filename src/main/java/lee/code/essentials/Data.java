package lee.code.essentials;

import lee.code.essentials.database.SQLite;
import lombok.Getter;
import org.bukkit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    @Getter private final List<String> worldNames = new ArrayList<>();
    @Getter private final List<String> chatColors = new ArrayList<>();
    @Getter private final List<String> gameSounds = new ArrayList<>();
    @Getter private final List<Location> chairLocations = new ArrayList<>();
    private final ConcurrentHashMap<UUID, UUID> playersRequestingTeleport = new ConcurrentHashMap<>();

    public boolean isPlayerRequestingTeleportForTarget(UUID player, UUID target) {
        return playersRequestingTeleport.get(player) == target;
    }
    public void setPlayerRequestingTeleport(UUID player, UUID target) {
        playersRequestingTeleport.put(player, target);
    }
    public void removePlayerRequestingTeleport(UUID player) {
        playersRequestingTeleport.remove(player);
    }
    public void addChairLocation(Location location) { chairLocations.add(location);}
    public void removeChairLocation(Location location) { chairLocations.remove(location);}

    public void cacheDatabase() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        SQL.loadPlayerData();
        SQL.loadServerData();
    }

    public void loadListData() {

        //worlds
        for (World selectedWorld : Bukkit.getWorlds()) {
            worldNames.add(selectedWorld.getName());
        }

        //chat colors
        for (ChatColor color : ChatColor.values()) {
            chatColors.add(color.name());
        }

        //sounds
        for (Sound sound : Sound.values()) {
            gameSounds.add(sound.name());
        }
    }
}
