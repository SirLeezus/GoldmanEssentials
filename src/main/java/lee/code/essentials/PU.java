package lee.code.essentials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Strings;
import lee.code.essentials.lists.Lang;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PU {

    private final Pattern HEX_REGEX = Pattern.compile("#[a-fA-F0-9]{6}");

    public String format(String message) {
        Matcher matcher = HEX_REGEX.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = HEX_REGEX.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String format(String msg, String fromHex, String toHex, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean magic) {
        int length = msg.length();
        Color fromRGB = Color.decode(fromHex);
        Color toRGB = Color.decode(toHex);
        double rStep = Math.abs((double) (fromRGB.getRed() - toRGB.getRed()) / length);
        double gStep = Math.abs((double) (fromRGB.getGreen() - toRGB.getGreen()) / length);
        double bStep = Math.abs((double) (fromRGB.getBlue() - toRGB.getBlue()) / length);
        if (fromRGB.getRed() > toRGB.getRed()) rStep = -rStep;
        if (fromRGB.getGreen() > toRGB.getGreen()) gStep = -gStep;
        if (fromRGB.getBlue() > toRGB.getBlue()) bStep = -bStep;
        Color finalColor = new Color(fromRGB.getRGB());
        String GradientHexRegex = "<\\$#[A-Fa-f0-9]{6}>";
        msg = msg.replaceAll(GradientHexRegex, "");
        msg = msg.replace("", "<$>");
        for (int index = 0; index <= length; index++) {
            int red = (int) Math.round(finalColor.getRed() + rStep);
            int green = (int) Math.round(finalColor.getGreen() + gStep);
            int blue = (int) Math.round(finalColor.getBlue() + bStep);
            if (red > 255) red = 255; if (red < 0) red = 0;
            if (green > 255) green = 255; if (green < 0) green = 0;
            if (blue > 255) blue = 255; if (blue < 0) blue = 0;
            finalColor = new Color(red, green, blue);
            String hex = "#" + Integer.toHexString(finalColor.getRGB()).substring(2);
            String formats = "";
            if (bold) formats += ChatColor.BOLD;
            if (italic) formats += ChatColor.ITALIC;
            if (underlined) formats += ChatColor.UNDERLINE;
            if (strikethrough) formats += ChatColor.STRIKETHROUGH;
            if (magic) formats += ChatColor.MAGIC;
            msg = msg.replaceFirst("<\\$>", "" + ChatColor.of(hex) + formats);
        }
        return msg;
    }

    public String legacyToJson(String legacyString) {
        if (legacyString == null) return "";
        return ComponentSerializer.toString(TextComponent.fromLegacyText(legacyString));
    }

    public String formatAmount(int value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

    public String formatAmount(double value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

    public String buildStringFromArgs(String[] args, int start) {
        StringBuilder w = new StringBuilder();
        for(int i = start; i < args.length; i++) {
            w.append(args[i]).append(" ");
        }
        w = new StringBuilder(w.substring(0, w.length() - 1));
        return w.toString();
    }

    public String formatPlayerLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public String formatBlockLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }

    public Location unFormatPlayerLocation(String location) {
        String[] split = location.split(",", 6);
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), (float) Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]));
    }

    public Location unFormatBlockLocation(String location) {
        String[] split = location.split(",", 4);
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public List<String> getOnlinePlayers() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    public void teleportTimer(Player player, Player target) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        scheduler.runTaskLater(plugin, () -> {

            if (Bukkit.getOnlinePlayers().contains(player) && plugin.getData().isPlayerRequestingTeleportForTarget(player.getUniqueId(), target.getUniqueId())) {
                plugin.getData().removePlayerRequestingTeleport(player.getUniqueId());
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED.getString(new String[] { target.getName() }));
            }

        },1200L);
    }

    public void kickOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(Lang.SERVER_RESTART.getString(null));
        }
    }

    public void registerTamedEntityFix() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        plugin.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                    PacketContainer packet = event.getPacket();

                    List<WrappedWatchableObject> watchableCollection = packet.getWatchableCollectionModifier().read(0);

                    for (WrappedWatchableObject object : watchableCollection) {
                        if (object.getIndex() == 17) {
                            String value = object.getValue().toString();
                            if (value.startsWith("Optional")) {
                                object.setValue(Optional.of(UUID.randomUUID()));
                            }
                        }
                    }
                }
            }
        });
    }

    public String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}
