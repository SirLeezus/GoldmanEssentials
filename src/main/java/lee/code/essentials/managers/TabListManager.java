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
                        "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\nJourney Survival\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "#5CFF42", "#FFF700", true, false, false, false, false)))).write(1, WrappedChatComponent.fromJson(plugin.getPU().legacyToJson(plugin.getPU().format("\n#FF7800&lOnline&7: #FFEC2A" + Bukkit.getOnlinePlayers().size() + "\n"))));
                ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
            }
        }, 10, 40);
    }
}
