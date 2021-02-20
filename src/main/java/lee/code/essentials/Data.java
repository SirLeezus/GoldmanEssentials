package lee.code.essentials;

import com.comphenix.protocol.events.PacketContainer;
import lee.code.essentials.database.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final List<String> worldNames = new ArrayList<>();
    private final List<String> chatColors = new ArrayList<>();
    private final List<String> gameSounds = new ArrayList<>();
    private final ConcurrentHashMap<UUID, PacketContainer> playerNameTagPacketContainers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playersRequestingTeleport = new ConcurrentHashMap<>();

    public List<PacketContainer> getPlayerNameTagPacketContainers () {
        return new ArrayList<>(playerNameTagPacketContainers.values());
    }
    public void setPlayerNameTagPacketContainer(UUID player, PacketContainer packetContainer) {
        playerNameTagPacketContainers.put(player, packetContainer);
    }
    public boolean isPlayerRequestingTeleportForTarget(UUID player, UUID target) {
        return playersRequestingTeleport.get(player) == target;
    }
    public void setPlayerRequestingTeleport(UUID player, UUID target) {
        playersRequestingTeleport.put(player, target);
    }
    public void removePlayerRequestingTeleport(UUID player) {
        playersRequestingTeleport.remove(player);
    }
    public List<String> getWorlds() {
        return worldNames;
    }
    public List<String> getChatColors() {
        return chatColors;
    }
    public List<String> getGameSounds() {
        return gameSounds;
    }

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
