package lee.code.essentials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PU {

    private final Pattern HEX_REGEX = Pattern.compile("#[a-fA-F0-9]{6}");

    public String format(String message) {
        if (message == null) return "";
        Matcher matcher = HEX_REGEX.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = HEX_REGEX.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message).replaceAll("&", "");
    }

    public Component formatC(String message) {
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
        return Component.empty().decoration(TextDecoration.ITALIC, false).append(serializer.deserialize(message));
    }

    public String legacyToJson(String legacyString) {
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

    public int rng() {
        SecureRandom sr = new SecureRandom();
        return sr.nextInt(1000);
    }

    public String shortenDouble(double value) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return formatter.format(value);
    }

    public List<String> getRanks() {
        return EnumSet.allOf(RankList.class).stream().map(RankList::name).collect(Collectors.toList());
    }

    public List<String> getPremiumRanks() {
        return EnumSet.allOf(PremiumRankList.class).stream().map(PremiumRankList::name).collect(Collectors.toList());
    }

    public List<String> getEntityHeads() {
        return EnumSet.allOf(EntityHeads.class).stream().map(EntityHeads::name).collect(Collectors.toList());
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

    public Location unFormatPlayerLocation(String location) {
        String[] split = location.split(",", 6);
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), (float) Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]));
    }

    public List<String> getOnlinePlayers() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) if (!plugin.getData().getVanishedPlayers().contains(player.getUniqueId())) players.add(player.getName());
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
            player.kick(Component.text(Lang.SERVER_RESTART.getString(null)));
        }
    }

    public void registerTamedEntityFix() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        plugin.getProtocolManagerAPI().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                    PacketContainer packet = event.getPacket();

                    List<WrappedWatchableObject> watchableCollection = packet.getWatchableCollectionModifier().read(0);

                    for (WrappedWatchableObject object : watchableCollection) {
                        if (object.getIndex() == 17) {
                            String value = object.getValue().toString();
                            if (value.startsWith("Optional")) event.setCancelled(true);
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

    @SuppressWarnings("deprecation")
    public void updateDisplayName(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        UUID uuid = player.getUniqueId();

        ScoreboardManager boardManager = Bukkit.getScoreboardManager();
        Scoreboard board = boardManager.getMainScoreboard();

        Objective health = board.getObjective("health");

        if (health == null) {
            Objective o = board.registerNewObjective("health", "health", Component.text(format("&c❤")), RenderType.HEARTS);
            o.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }

        if (board.getTeam(player.getName()) == null) {
            board.registerNewTeam(player.getName());
        }

        Team team = board.getTeam(player.getName());

        if (team != null) {
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            team.addEntry(player.getName());

            String prefix = cache.getPrefix(uuid) + " ";
            String suffix = cache.getSuffix(uuid);
            org.bukkit.ChatColor color = org.bukkit.ChatColor.valueOf(cache.getColor(uuid));
            String prestige = "";
            int prestigeLevel = cache.getPrestige(uuid);
            if (prestigeLevel != 0) prestige = " &7[&a&l" + prestigeLevel + "&7]";
            team.setColor(color);
            team.setSuffix(format(prestige + suffix));
            team.setPrefix(format(prefix));

            player.setDisplayName(format(prefix + color + player.getName() + prestige + suffix));
            player.setPlayerListName(format(prefix + color + player.getName() + prestige + suffix));
        }
    }

    public int countEntitiesInChunk(Chunk chunk, EntityType type) {
        int count = 0;
        for (Entity e : chunk.getEntities()) if (type.equals(e.getType()) && !e.isDead() && !(e instanceof Player)) count++;
        return count;
    }

    public void scheduleEntityChunkCleaner() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (World world : Bukkit.getWorlds()) {
                    for (Chunk chunk : world.getLoadedChunks()) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            for (Entity entity : chunk.getEntities()) {
                                if (entity.getCustomName() == null && countEntitiesInChunk(chunk, entity.getType()) > Settings.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
                            }
                        });
                    }
                }
            }
        }), 0L, 20L * 30);
    }

    public String formatTime(long time) {
        long hours = time / 1000 + 6;
        long minutes = (time % 1000) * 60 / 1000;
        String ampm = "AM";
        if (hours >= 12) {
            hours -= 12;
            ampm = "PM";
        }
        if (hours >= 12) {
            hours -= 12;
            ampm = "AM";
        }
        if (hours == 0) hours = 12;
        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2);
        return hours + ":" + mm + " " + ampm;
    }

    public void addPlayerClickDelay(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        plugin.getData().addPlayerClickDelay(uuid);
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () -> plugin.getData().removePlayerClickDelay(uuid), Settings.CLICK_DELAY.getValue());
    }

    public void applyHeadSkin(ItemStack head, String base64, UUID uuid) {
        try {
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            GameProfile profile = new GameProfile(uuid, null);
            profile.getProperties().put("textures", new Property("textures", base64));
            if (skullMeta != null) {
                Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                mtd.setAccessible(true);
                mtd.invoke(skullMeta, profile);
            }
            head.setItemMeta(skullMeta);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }
}
