package lee.code.essentials.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.AbstractStructure;
import com.comphenix.protocol.events.InternalStructure;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Objects;
import lee.code.core.util.bukkit.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public class AbstractBoard {
    protected PacketContainer handle;
    protected AbstractStructure structure;
    protected UUID uuid;

    protected AbstractBoard(PacketContainer handle, PacketType type, UUID uuid) {
        if (handle == null) throw new IllegalArgumentException("Packet handle cannot be NULL.");
        if (!Objects.equal(handle.getType(), type)) throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);

        this.handle = handle;
        this.structure = handle.getOptionalStructures().readSafely(0).get();
        this.uuid = uuid;
    }

    public void sendPacket(Player receiver) {
        try {
            handle.getOptionalStructures().write(0, Optional.of((InternalStructure) structure));
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, handle);
            updateHeath();
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet.", e);
        }
    }

    public void broadcastPacket() {
        handle.getOptionalStructures().write(0, Optional.of((InternalStructure) structure));
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(handle);
        updateHeath();
    }

    public void updateHeath() {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            double health = player.getAbsorptionAmount() + player.getHealth();
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHealthObjective());
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(getDisplaySlotPacket());
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(newHeathPacket(player, (int) health));
        }
    }

    private PacketContainer getHealthObjective() {
        // http://wiki.vg/Protocol#Scoreboard_Objective
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        // set name - limited to String(16)
        packet.getStrings().write(0, "health");

        // set mode - 0 to create the scoreboard. 1 to remove the scoreboard. 2 to update the display text.
        packet.getIntegers().write(0, 0);

        // set display name - Component
        packet.getChatComponents().write(0, WrappedChatComponent.fromJson(BukkitUtils.serializeColorComponentJson("\uE78E")));

        // set type - either "integer" or "hearts"
        packet.getEnumModifier(HealthDisplay.class, 2).write(0, BoardManager.HealthDisplay.HEARTS);

        return packet;
    }

    private PacketContainer getDisplaySlotPacket() {
        // http://wiki.vg/Protocol#Display_Scoreboard
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);

        //below name
        packet.getIntegers().write(0, 2);

        // set objective name - The unique name for the scoreboard to be displayed.
        packet.getStrings().write(0, "health");

        return packet;
    }

    private PacketContainer newHeathPacket(Player player, int amount) {
        // http://wiki.vg/Protocol#Update_Score
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);

        // the name of the score
        packet.getStrings().write(0, player.getName());

        // set the action - 0 to create/update an item. 1 to remove an item.
        packet.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);

        // set objective name - The name of the objective the score belongs to
        packet.getStrings().write(1, "health");

        // set value of the score- The score to be displayed next to the entry. Only sent when Action does not equal 1.
        packet.getIntegers().write(0, amount);

        return packet;
    }

    public enum HealthDisplay {
        INTEGER, HEARTS
    }
}