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
import lee.code.essentials.managers.CountdownTimer;
import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PU {

    private final Pattern hexRegex = Pattern.compile("\\&#[a-fA-F0-9]{6}");
    private final Pattern itemRegex = Pattern.compile("(?i).*\\[item\\].*");
    public final Random random = new Random();
    @Getter private BossBar boosterBar;

    public String format(String message) {
        if (message == null) return "";
        Matcher matcher = hexRegex.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end()).replaceAll("&", "");
            message = message.replace("&" + color, ChatColor.of(color) + "");
            matcher = hexRegex.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public Component formatC(String message) {
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
        return Component.empty().decoration(TextDecoration.ITALIC, false).append(serializer.deserialize(message));
    }

    public String unFormatC(Component message) {
        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
        return serializer.serialize(message);
    }

    public String formatAmount(int value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

    public String formatAmount(double value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

    public String formatCapitalization(String type) {
        String format = type.toLowerCase().replaceAll("_", " ");
        return WordUtils.capitalize(format);
    }

    public void rtpPlayer(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Bukkit.getScheduler().runTask(plugin, () -> {
            int x = -10000 + random.nextInt(20000);
            int y = 200;
            int z = -10000 + random.nextInt(20000);

            World world = player.getWorld();
            Location location = new Location(player.getWorld(), x, y, z);

            if (world.getWorldBorder().isInside(location)) {
                world.loadChunk(location.getChunk());
                for (int i = y; i > 0; i--) {
                    Location loc = new Location(player.getWorld(), x, i, z);
                    if (loc.getBlock().getType() == Material.AIR) {
                        Location ground = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());
                        Material groundType = ground.getBlock().getType();
                        if (groundType != Material.AIR && groundType != Material.LAVA && groundType != Material.WATER) {
                            player.teleportAsync(loc);
                            player.sendActionBar(Lang.TELEPORT.getComponent(null));
                            return;
                        }
                    }
                }
            }
            rtpPlayer(player);
        });
    }

    public String getDate() {
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        Date resultDate = new Date(milliseconds);
        return sdf.format(resultDate);
    }

    @SuppressWarnings("deprecation")
    public Component parseChatVariables(Player player, Component message) {
        String text = PlainTextComponentSerializer.plainText().serialize(message);

        if (itemRegex.matcher(text).matches()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            String materialName = formatCapitalization(item.getType().name());
            String itemName = materialName;
            StringBuilder lore = new StringBuilder();
            if (item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasDisplayName()) itemName = itemMeta.getDisplayName();
                if (itemMeta.hasEnchants()) {
                    for (Map.Entry<Enchantment, Integer> enchant : itemMeta.getEnchants().entrySet()) {
                        String enchantColor = "&7";
                        if (enchant.getKey().equals(Enchantment.VANISHING_CURSE) || enchant.getKey().equals(Enchantment.BINDING_CURSE)) enchantColor = "&c";
                        lore.append("\n").append(enchantColor).append(formatCapitalization(enchant.getKey().getKey().getKey())).append(" ").append(getRomanNumber(enchant.getValue()));
                    }
                }
                if (itemMeta.hasLore() && itemMeta.getLore() != null) for (String loreLine : itemMeta.getLore()) lore.append("\n&5&o").append(loreLine);
            }
            return formatC("&b[&f" + itemName + "&b]").hoverEvent(formatC(itemName + " &7(&f" + materialName + "&7)" + lore));
        } else return message;
    }

    public String getRomanNumber(int number) {
        switch (number) {
            case 1 -> { return "I"; }
            case 2 -> { return "II"; }
            case 3 -> { return "III"; }
            case 4 -> { return "IV"; }
            case 5 -> { return "V"; }
            case 6 -> { return "VI"; }
            case 7 -> { return "VII"; }
            case 8 -> { return "VIII"; }
            case 9 -> { return "IX"; }
            case 10 -> { return "X"; }
            default -> { return ""; }
        }
    }

    public int rng() {
        return random.nextInt(1000);
    }

    public String shortenDouble(double value) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return formatter.format(value);
    }

    public List<String> getRanks() {
        return EnumSet.allOf(RankList.class).stream().map(RankList::name).collect(Collectors.toList());
    }

    public List<String> getCustomCraftingRecipes() {
        return EnumSet.allOf(CustomCraftingRecipes.class).stream().map(CustomCraftingRecipes::name).collect(Collectors.toList());
    }

    public List<String> getPremiumRanks() {
        return EnumSet.allOf(PremiumRankList.class).stream().map(PremiumRankList::name).collect(Collectors.toList());
    }

    public List<String> getEntityHeads() {
        return EnumSet.allOf(EntityHeads.class).stream().map(EntityHeads::name).collect(Collectors.toList());
    }

    public List<ItemStack> getSellableItems() {
        return EnumSet.allOf(ItemSellValues.class).stream().map(ItemSellValues::getItem).collect(Collectors.toList());
    }

    public List<ItemStack> getNameColorItems() {
        return EnumSet.allOf(NameColorList.class).stream().map(NameColorList::getItem).collect(Collectors.toList());
    }

    public List<Material> getBoosterDropBlocks() {
        return EnumSet.allOf(BoosterDropBlocks.class).stream().map(BoosterDropBlocks::getBlock).collect(Collectors.toList());
    }

    public int getItemAmount(Player player, ItemStack item) {
        if (item == null) return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(item)) continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    public void takeItems(Player player, ItemStack item, int count) {
        Material mat = item.getType();
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values()) found += stack.getAmount();
        if (count > found) return;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            if (stack.isSimilar(item)) {
                int removed = Math.min(count, stack.getAmount());
                count -= removed;

                if (stack.getAmount() == removed) player.getInventory().setItem(index, null);
                else stack.setAmount(stack.getAmount() - removed);

                if (count <= 0) break;
            }
        }
        player.updateInventory();
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

    public String formatPlayerHomeLocation(String name, Location location) {
        return name + "+" + location.getWorld().getName() + "+" + location.getX() + "+" + location.getY() + "+" + location.getZ() + "+" + location.getYaw() + "+" + location.getPitch();
    }

    public Location unFormatPlayerHomeLocation(String home) {
        String[] split = home.split("\\+", 7);
        return new Location(Bukkit.getWorld(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]), (float) Double.parseDouble(split[6]));
    }

    public String unFormatPlayerHomeName(String home) {
        String[] split = home.split("\\+", 7);
        return split[0];
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
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED.getComponent(new String[] { target.getName() })));
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
                        if (object.getIndex() == 18) {
                            String value = object.getValue().toString();
                            if (value.startsWith("Optional")) object.setValue(Optional.of(UUID.randomUUID()));
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
                                if (!(entity instanceof Item) && entity.getCustomName() == null && countEntitiesInChunk(chunk, entity.getType()) > Settings.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
                            }
                        });
                    }
                }
            }
        }), 0L, 20L * 30);
    }

    public void scheduleBoosterChecker() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (cache.areBoosters()) {
                String id = cache.getActiveBoosterID() != null ? cache.getActiveBoosterID() : cache.getNextBoosterQueueID();
                String name = cache.getBoosterPlayerName(id);
                String multiplier = cache.getBoosterMultiplier(id);
                long time = cache.getBoosterTime(id);
                long duration = cache.getBoosterDuration(id);
                float barProgress = (float) time / duration * 1 < 0 ? 0 : (float) time / duration * 1;

                if (boosterBar == null) boosterBar = BossBar.bossBar(Lang.BOOSTER_TITLE.getComponent(new String[] { multiplier, formatSeconds(time) }), barProgress, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);

                if (cache.isBoosterActive()) {
                    if (time > 0) {
                        boosterBar.name(Lang.BOOSTER_TITLE.getComponent(new String[] { multiplier, formatSeconds(time) }));
                        boosterBar.progress(barProgress);
                    } else {
                        cache.removeBooster(id);
                        Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BOOSTER_ENDED.getComponent(new String[] { multiplier, name })));
                        Bukkit.getServer().hideBossBar(boosterBar);
                    }
                } else if (id != null) {
                    cache.setBoosterActive(id, true);
                    time = cache.getBoosterTime(id);
                    boosterBar.name(Lang.BOOSTER_TITLE.getComponent(new String[] { multiplier, formatSeconds(time) }));
                    boosterBar.progress(1);
                    Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BOOSTER_STARTED.getComponent(new String[] { multiplier, name })));
                    Bukkit.getServer().showBossBar(boosterBar);
                }
            }
        }), 0L, 20L);
    }

    public void scheduleAutoRestart() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            CountdownTimer timer = new CountdownTimer(plugin,
                    30,
                    () -> Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_START.getComponent(null))),
                    () -> {
                        Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_END.getComponent(null)));
                        Bukkit.getServer().shutdown();
                    },
                    (t) -> Bukkit.getServer().sendActionBar(Lang.AUTO_RESTART_TIME.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) })));
            timer.scheduleTimer();
        }, Settings.AUTO_RESTART.getValue());
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

    public String formatSeconds(long time) {
        long days = TimeUnit.SECONDS.toDays(time);
        long hours = (TimeUnit.SECONDS.toHours(time) - TimeUnit.DAYS.toHours(days));
        long minutes = (TimeUnit.SECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
        long seconds = (TimeUnit.SECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));

        if (days != 0) return "&e" + days + "&6d&e, " + hours + "&6h&e, " + minutes + "&6m&e, " + seconds + "&6s";
        else if (hours != 0) return "&e" + hours + "&6h&e, " + minutes + "&6m&e, " + seconds + "&6s";
        else if (minutes != 0) return "&e" + minutes + "&6m&e, " + seconds + "&6s";
        else return "&e" + seconds + "&6s";
    }

    public long unFormatSeconds(String time) {
        String numberOnly = time.replaceAll("[^0-9]", "");
        Scanner numberScanner = new Scanner(numberOnly);
        if (numberScanner.hasNextInt()) {
            int value = Integer.parseInt(numberOnly);
            String letter = time.replaceAll("[^A-Za-z]+", "").toLowerCase();
            switch (letter) {
                case "w": return value * 604800L;
                case "d": return value * 86400L;
                case "h": return value * 3600L;
                case "m": return value * 60L;
                case "s": return value;
            }
        }
        return 0;
    }

    public void addPlayerClickDelay(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        plugin.getData().addPlayerClickDelay(uuid);
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () -> plugin.getData().removePlayerClickDelay(uuid), Settings.CLICK_DELAY.getValue());
    }

    public void addPlayerPvPDelay(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (data.isPvPTaskActive(uuid)) {
            BukkitTask task = data.getPvPDelayTask(uuid);
            task.cancel();
        }

        long milliseconds = System.currentTimeMillis();
        long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + Settings.PVP_DELAY.getValue();
        data.setPvPTimer(uuid, time);

        data.addPvPTaskActive(uuid, new BukkitRunnable() {
            @Override
            public void run() {
                data.removePvPTimer(uuid);
                data.removePvPTaskActive(uuid);
            }

        }.runTaskLater(plugin, Settings.PVP_DELAY.getValue() * 20L));
    }

    public void addSpamDelay(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (data.isSpamTaskActive(uuid)) {
            BukkitTask task = data.getSpamDelayTask(uuid);
            task.cancel();
        }

        data.addSpamTaskActive(uuid, new BukkitRunnable() {
            @Override
            public void run() {
                data.removeSpamTaskActive(uuid);
            }

        }.runTaskLater(plugin, Settings.SPAM_DELAY.getValue() * 20L));
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

    public ItemStack createSpawner(EntityType type) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
        if (spawnerMeta != null) {
            CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
            spawnerCS.setSpawnedType(type);
            spawnerMeta.setBlockState(spawnerCS);
            spawnerMeta.displayName(Lang.SPAWNER_NAME.getComponent(new String[] { formatCapitalization(type.name()) }));
            spawner.setItemMeta(spawnerMeta);
        }
        return spawner;
    }

    public ItemStack getItem(Material type, String name, String lore, String skin) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        ItemStack item = new ItemStack(type);
        if (skin != null) plugin.getPU().applyHeadSkin(item, skin, UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"));
        ItemMeta itemMeta = item.getItemMeta();
        if (name != null) itemMeta.displayName(plugin.getPU().formatC(name));
        if (lore != null) {
            String[] split = StringUtils.split(lore, "\n");
            List<Component> lines = new ArrayList<>();
            for (String line : split) lines.add(plugin.getPU().formatC(line));
            itemMeta.lore(lines);
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}
