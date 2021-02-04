package lee.code.essentials.builders;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lee.code.essentials.GoldmanEssentials;
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

    public NameTagBuilder(Player player) {
        this.player = player;
        this.packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        this.packet.getIntegers().write(1, 0);
        this.packet.getStrings().write(0, name);
        this.packet.getChatComponents().write(0, WrappedChatComponent.fromText(player.toString()));
        this.packet.getSpecificModifier(Collection.class).write(0, Collections.singletonList(player.getName()));
    }

    public NameTagBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        this.packet.getChatComponents().write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', prefix)));
        return this;
    }

    public NameTagBuilder setColor(ChatColor color) {
        this.color = color;
        this.packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
        return this;
    }

    public NameTagBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        this.packet.getChatComponents().write(2, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', suffix)));
        return this;
    }

    public void build() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        UUID uuid = player.getUniqueId();

        updateChatAndTabList();

        plugin.getData().setPlayerNameTagPacketContainer(uuid, packet);

        //send new packet to all online players
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);

        //send all saved packets to new player
        for (PacketContainer savedPlayerPackets : plugin.getData().getPlayerNameTagPacketContainers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, savedPlayerPackets);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet " + savedPlayerPackets, e);
            }
        }
    }

    private void updateChatAndTabList() {
        player.setDisplayName(ChatColor.translateAlternateColorCodes('&',prefix + color + player.getName() + suffix));
        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',prefix + color + player.getName() + suffix));
    }
}
