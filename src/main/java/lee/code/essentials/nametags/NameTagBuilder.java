package lee.code.essentials.nametags;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class NameTagBuilder {

    PacketContainer packet;
    Player player;
    ChatColor color;
    String suffix;
    String prefix;

    public NameTagBuilder(Player player, ChatColor color) {
        this.player = player;
        this.color = color;
        this.packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        this.packet.getIntegers().write(1, 0);
        this.packet.getStrings().write(0, name);
        this.packet.getChatComponents().write(0, WrappedChatComponent.fromText(player.toString()));
        this.packet.getSpecificModifier(Collection.class).write(0, Collections.singletonList(player.getName()));
        this.packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
    }

    public NameTagBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        this.packet.getChatComponents().write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', prefix + " ")));
        return this;
    }

    public NameTagBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        this.packet.getChatComponents().write(2, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', " " + suffix)));
        return this;
    }

    public void build() {
        updateChatAndTabList();
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet " + packet, e);
            }
        }
    }

    private void updateChatAndTabList() {
        player.setDisplayName(ChatColor.translateAlternateColorCodes('&',prefix + " " + color + player.getName() + " " + suffix));
        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',prefix + " " + color + player.getName() + " " + suffix));
    }
}
