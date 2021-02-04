package lee.code.essentials;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Data {

    private final List<String> worldNames = new ArrayList<>();
    private final HashMap<UUID, PacketContainer> playerNameTagPacketContainers = new HashMap<>();
    public List<PacketContainer> getPlayerNameTagPacketContainers () {
        return new ArrayList<>(playerNameTagPacketContainers.values());
    }
    public void setPlayerNameTagPacketContainer(UUID player, PacketContainer packetContainer) {
        playerNameTagPacketContainers.put(player, packetContainer);
    }

    public List<String> getWorlds() {
        return worldNames;
    }

    public void loadWorldNames() {
        for (World selectedWorld : Bukkit.getWorlds()) {
            worldNames.add(selectedWorld.getName());
        }
    }
}
