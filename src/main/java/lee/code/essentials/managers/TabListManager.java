package lee.code.essentials.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Bukkit;

public class TabListManager {

    public void scheduleTabListUpdater() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
                packet.getChatComponents().write(0, WrappedChatComponent.fromJson(plugin.getPU().legacyToJson(plugin.getPU().format(
                        "#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n#4dc462&lJourney Survival\n#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")))).write(
                                1, WrappedChatComponent.fromJson(plugin.getPU().legacyToJson(plugin.getPU().format("\n#228B22&lOnline&7: #4dc462" + (Bukkit.getOnlinePlayers().size() - plugin.getData().getVanishedPlayers().size())  + "\n"))));
                ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
            }
        }, 10, 40);
    }
}
