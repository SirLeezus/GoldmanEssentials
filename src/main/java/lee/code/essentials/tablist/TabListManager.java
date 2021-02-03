package lee.code.essentials.tablist;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lee.code.essentials.TheEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TabListManager {

    public void updatePlayerHeaderFooter(Player player) {

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', "&e-= &2&lJourney Survival &e=-"))).write( 1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', "&a&lOnline: &b&l" + Bukkit.getOnlinePlayers().size())));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet " + packet, e);
        }
    }

    public void scheduleTabListUpdater() {
        TheEssentials plugin = TheEssentials.getPlugin();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
                packet.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', "&e-= &2&lJourney Survival &e=-"))).write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', "&a&lOnline: &b&l" + Bukkit.getOnlinePlayers().size())));
                ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
            }
        }, 10, 40);
    }
}
