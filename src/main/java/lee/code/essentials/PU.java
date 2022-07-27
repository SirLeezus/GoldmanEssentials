package lee.code.essentials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Strings;
import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.enchants.EnchantsAPI;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.*;
import lee.code.essentials.managers.BoardManager;
import lee.code.essentials.managers.CountdownTimer;
import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PU {

    private final String lRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    private final String iRegex = "(?i)\\[item]";
    private final String eRegex = "(?<=\\:)(.*?)(?=\\:)";
    private final Pattern itemRegex = Pattern.compile(iRegex, Pattern.DOTALL);
    private final Pattern linkRegex = Pattern.compile(lRegex, Pattern.DOTALL);
    private final Pattern emojiRegex = Pattern.compile(eRegex, Pattern.DOTALL);
    private final Random random = new Random();
    @Getter private BossBar boosterBar;

    public void rtpPlayer(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        UUID uuid = player.getUniqueId();
        final int maxAttempts = 10;
        if (data.getRTPAttempt(uuid) < maxAttempts) {
            World world = player.getWorld();

            WorldBorder border = world.getWorldBorder();
            Location center = border.getCenter();
            double radius = border.getSize() / 2d;
            double xMin = center.getX() - radius;
            double xMax = center.getX() + radius;
            double zMin = center.getZ() - radius;
            double zMax = center.getZ() + radius;

            double x = xMin + random.nextDouble(xMax + xMax);
            double y = 200;
            double z = zMin + random.nextDouble(zMax + zMax);

            Location location = new Location(player.getWorld(), x, y, z);
            Material[] blackList = new Material[] { Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.WATER, Material.LAVA, Material.BEDROCK };

            world.getChunkAtAsync(location, true).thenAccept(result -> {
                if (world.getWorldBorder().isInside(location)) {
                    for (double i = location.getY(); i > 50; i--) {
                        Location loc = new Location(player.getWorld(), location.getX(), i, location.getZ());
                        if (loc.getBlock().getType() == Material.AIR) {
                            Location ground = loc.subtract(0, 1, 0);
                            Block block = ground.getBlock();
                            Material groundType = block.getType();
                            Vector box = block.getBoundingBox().getCenter();
                            if (!box.equals(new Vector(0, 0, 0)) && !Arrays.asList(blackList).contains(groundType) ) {
                                double bX = box.getX();
                                double bY = box.getY() + 0.5;
                                double bZ = box.getZ();
                                Location teleportLocation = new Location(block.getWorld(), bX, bY, bZ);
                                if (!plugin.getChunkAPI().isClaimed(teleportLocation.getChunk())) {
                                    addRandomTeleportDelay(uuid);
                                    data.clearRTPAttempts(uuid);
                                    data.removeRandomTeleporting(uuid);
                                    player.teleportAsync(teleportLocation).thenAccept(result2 -> {
                                        if (result2) player.sendActionBar(Lang.TELEPORT.getComponent(null));
                                    });
                                    return;
                                }
                            }
                        }
                    }
                }
                data.addRTPAttempt(uuid, 1);
                rtpPlayer(player);
            });
        } else {
            data.clearRTPAttempts(uuid);
            data.removeRandomTeleporting(uuid);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOMTELEPORT_LOCATION_NOT_FOUND.getComponent(null)));
        }
    }

    public Component parseVariables(Player player, Component message) {
        Component newComponent = parseChatItem(player, message);
        return parseVariables(newComponent);
    }

    public Component parseVariables(Component message) {
        String text = PlainTextComponentSerializer.plainText().serialize(message);
        Component newMessage = message;

        //emoji parse
        Matcher emojiMatcher = emojiRegex.matcher(text);
        HashMap<String, TextReplacementConfig> replaceConfigs = new HashMap<>();
        while (emojiMatcher.find()) {
            String match = emojiMatcher.group();
            if (GoldmanEssentials.getPlugin().getData().getEmojiKeys().contains(match.toUpperCase())) {
                TextReplacementConfig textReplacementConfig = TextReplacementConfig.builder()
                        .matchLiteral(":" + match + ":")
                        .replacement(Component.text(Emoji.valueOf(match.toUpperCase()).getUnicode()).hoverEvent(BukkitUtils.parseColorComponent("&6:" + match.toLowerCase() + ":")))
                        .build();
                replaceConfigs.put(match, textReplacementConfig);
            }
        }
        for (TextReplacementConfig config : replaceConfigs.values()) {
            newMessage = newMessage.replaceText(config);
        }

        //link matcher
        Matcher linkMatcher = linkRegex.matcher(text);
        while (linkMatcher.find()) {
            String match = linkMatcher.group();
            Component newInfo = BukkitUtils.parseColorComponent("&6[&cLINK&6]").hoverEvent(BukkitUtils.parseColorComponent("&6Click to preview link!")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, match));
            TextReplacementConfig replace = TextReplacementConfig.builder().matchLiteral(match).replacement(newInfo).build();
            newMessage = newMessage.replaceText(replace);
        }

        return newMessage;
    }

    private Component parseChatItem(Player player, Component message) {
        EnchantsAPI enchantsAPI = GoldmanEssentials.getPlugin().getEnchantsAPI();

        String text = PlainTextComponentSerializer.plainText().serialize(message);
        ItemStack item = player.getInventory().getItemInMainHand();
        String materialName = BukkitUtils.parseCapitalization(item.getType().name());
        Component itemName = Component.text(materialName).color(NamedTextColor.WHITE);
        Component lore = Component.empty();
        Component newMessage = message;

        Matcher itemMatcher = itemRegex.matcher(text);
        if (itemMatcher.find()) {
            if (item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();

                //hat check
                if (itemMeta.hasCustomModelData()) materialName = "Magic";

                //display name
                if (itemMeta.hasDisplayName()) itemName = itemMeta.displayName();
                else if (itemMeta instanceof SkullMeta skullMeta && skullMeta.getOwningPlayer() != null) { itemName = BukkitUtils.parseColorComponent("&e" + skullMeta.getOwningPlayer().getName() + "'s Head"); }
                else if (itemMeta.hasEnchants()) itemName = itemName.color(NamedTextColor.AQUA);
                else if (itemMeta instanceof EnchantmentStorageMeta) itemName = itemName.color(NamedTextColor.YELLOW);

                //enchants
                if (itemMeta.hasEnchants() || itemMeta instanceof EnchantmentStorageMeta) {
                    Map<Enchantment, Integer> enchants = itemMeta instanceof EnchantmentStorageMeta book ? book.getStoredEnchants() : itemMeta.getEnchants();
                    Component downSpacer = BukkitUtils.parseColorComponent("\n");
                    Component spacer = Component.text(" ");

                    for (Map.Entry<Enchantment, Integer> enchant : enchants.entrySet()) {
                        String key = enchant.getKey().getKey().getKey().toUpperCase();
                        if (!enchantsAPI.isCustomEnchant(key)) {
                            Component enchantment = BukkitUtils.parseColorComponent(BukkitUtils.parseCapitalization(key));
                            Component enchantmentLevel = enchant.getKey().getMaxLevel() > 1 ? BukkitUtils.parseColorComponent(BukkitUtils.getRomanNumber(enchant.getValue())) : Component.empty();
                            Component newLoreLine = downSpacer.append(enchantment.append(spacer).append(enchantmentLevel)).color(enchant.getKey().isCursed() ? NamedTextColor.RED : NamedTextColor.GRAY);
                            lore = lore.append(newLoreLine);
                        }
                    }
                }
                //lore
                if (itemMeta.hasLore()) for (Component loreLine : Objects.requireNonNull(itemMeta.lore())) lore = lore.append(BukkitUtils.parseColorComponent("\n&5&o")).append(loreLine);

            }

            if (itemName != null) {
                String match = itemMatcher.group();
                Component newInfo = BukkitUtils.parseColorComponent("&6[&f").append(itemName).append(BukkitUtils.parseColorComponent("&6]")).hoverEvent(itemName.append(BukkitUtils.parseColorComponent(" &7(&f" + materialName + "&7)")).append(lore));
                TextReplacementConfig replace = TextReplacementConfig.builder().matchLiteral(match).replacement(newInfo).build();
                newMessage = newMessage.replaceText(replace);
            }
        }
        return newMessage;
    }

    public int headDropRNG() {
        return random.nextInt(1000);
    }

    public String serializeHomeLocation(String name, Location location) {
        return name + "+" + location.getWorld().getName() + "+" + location.getX() + "+" + location.getY() + "+" + location.getZ() + "+" + location.getYaw() + "+" + location.getPitch();
    }

    public Location parseHomeLocation(String home) {
        String[] split = home.split("\\+", 7);
        return new Location(Bukkit.getWorld(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]), (float) Double.parseDouble(split[6]));
    }

    public String parseHomeName(String home) {
        String[] split = home.split("\\+", 7);
        return split[0];
    }

    public String parseHomeWorld(String home) {
        String[] split = home.split("\\+", 7);
        return split[1];
    }

    public void teleportTimeoutTimer(Player player, Player target) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        UUID pUUID = player.getUniqueId();
        UUID tUUID = player.getUniqueId();
        scheduler.runTaskLater(plugin, () -> {
            if (data.isPlayerRequestingTeleportForTarget(pUUID, tUUID)) {
                if (player.isOnline()) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED.getComponent(new String[] { target.getName() })));
                data.removePlayerRequestingTeleport(pUUID);
            }
        },1200L);
    }

    public void tradeRequestTimeoutTimer(Player owner, Player trader) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        UUID ownerUUID = owner.getUniqueId();
        UUID traderUUID = trader.getUniqueId();
        data.setTradeRequestTask(ownerUUID, scheduler.runTaskLater(plugin, () -> {
            if (data.isTradeRequestingPlayer(ownerUUID, traderUUID)) {
                if (owner.isOnline()) owner.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_REQUEST_EXPIRED.getComponent(new String[] { trader.getName() })));
                data.removeTradeRequesting(ownerUUID);
            }
        },1200L));
    }

    public void pvpRequestTimeoutTimer(Player owner, Player target) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        UUID ownerUUID = owner.getUniqueId();
        UUID targetUUID = target.getUniqueId();
        data.setDuelRequestTask(ownerUUID, scheduler.runTaskLater(plugin, () -> {
            if (data.isDuelRequestingPlayer(ownerUUID, targetUUID)) {
                if (owner.isOnline()) owner.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_REQUEST_EXPIRED.getComponent(new String[] { target.getName() })));
                data.removeDuelRequesting(ownerUUID);
            }
        },1200L));
    }

    public void addRandomTeleportDelay(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (data.isRTPTaskActive(uuid)) {
            BukkitTask task = data.getRTPDelayTask(uuid);
            task.cancel();
        }

        long milliseconds = System.currentTimeMillis();
        long time = TimeUnit.MILLISECONDS.toSeconds(milliseconds) + Setting.RTP_DELAY.getValue();
        data.setRTPTimer(uuid, time);

        data.addRTPTaskActive(uuid, new BukkitRunnable() {
            @Override
            public void run() {
                data.removeRTPTimer(uuid);
                data.removeRTPTaskActive(uuid);
            }

        }.runTaskLater(plugin, Setting.RTP_DELAY.getValue() * 20L));
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

        }.runTaskLater(plugin, 10));
    }

    public void kickOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kick(Component.text(Lang.SERVER_RESTART.getString(null)));
        }
    }

    public void registerTamedEntityFix() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        plugin.getProtocolManagerAPI().addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.ENTITY_METADATA) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                    PacketContainer packet = event.getPacket();
                    List<WrappedWatchableObject> watchableCollection = packet.getWatchableCollectionModifier().read(0);
                    for (WrappedWatchableObject object : watchableCollection) {
                        if (object != null && object.getIndex() == 18) {
                            String value = object.getValue().toString();
                            if (value.startsWith("Optional[") && object.getValue() != Optional.empty()) {
                                object.setValue(Optional.empty());
                            }
                        }
                    }
                }
            }
        });
    }

    public void scheduleTabListUpdater() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[] { String.valueOf( BukkitUtils.getOnlinePlayers().size()) }));
            }
        }, 10, 40);
    }

    public String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public void updateDisplayName(Player player, boolean afk, boolean delayed) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        Data data = plugin.getData();
        UUID uuid = player.getUniqueId();
        BoardManager boardManager = new BoardManager(uuid);

        String rank = cacheManager.getRank(uuid);
        String priority = Rank.valueOf(rank).getPriority();

        String name = priority + data.getTeamNumber();
        data.setTeamNumber(data.getTeamNumber() + 1);

        String prefix = cacheManager.getPrefix(uuid) + " ";
        String suffix = cacheManager.getSuffix(uuid);
        ChatColor chatColor = cacheManager.getColor(uuid);
        String colorChar = "&" + chatColor.getChar();
        int prestigeLevel = cacheManager.getPrestige(uuid);
        String levelColor = "&l" + getPrestigeColor(prestigeLevel);
        String prestige = prestigeLevel != 0 ? "&#FFBA40[" + levelColor + prestigeLevel + "&#FFBA40] " : "";
        suffix = afk ? suffix + " &c&lAFK" : suffix;


        boardManager.setTeamName(name);
        boardManager.setPlayers(Collections.singletonList(player.getName()));
        boardManager.setColor(chatColor);
        boardManager.setPrefix(WrappedChatComponent.fromJson(BukkitUtils.serializeColorComponentJson(prefix + prestige)));
        boardManager.setSuffix(WrappedChatComponent.fromJson(BukkitUtils.serializeColorComponentJson(suffix)));

        player.displayName(BukkitUtils.parseColorComponent(prefix + prestige + colorChar + player.getName() + suffix.replace(" &c&lAFK", "")));
        player.playerListName(BukkitUtils.parseColorComponent(prefix + prestige + colorChar + player.getName() + suffix));
        data.setBoardPacket(uuid, boardManager);
        if (delayed) Bukkit.getServer().getScheduler().runTaskLater(plugin, boardManager::broadcastPacket, 20L);
        else boardManager.broadcastPacket();
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
                                //if (entity.getType().equals(EntityType.DROPPED_ITEM) && countEntitiesInChunk(chunk, entity.getType()) > Setting.MAX_DROP_ITEM_PER_CHUNK.getValue()) entity.remove();
                                if (!entity.getType().equals(EntityType.DROPPED_ITEM)) {
                                    if (entity.customName() == null && countEntitiesInChunk(chunk, entity.getType()) > Setting.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
                                }
                            }
                        });
                    }
                }
            }
        }), 0L, 20L * 30);
    }

    public void scheduleHeathChecker() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    double health = player.getAbsorptionAmount() + player.getHealth();
                    if (data.getHeathTracker(uuid) != health && data.hasBoard(uuid)) {
                        data.setHeathTracker(uuid, health);
                        plugin.getData().getBoardPacket(uuid).updateHeath();
                    }
                }
            }
        }), 1L, 1L);
    }

    public void scheduleAutoRestart() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat s = new SimpleDateFormat("ss");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        String restartTime = "12:00 AM";
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Date date = new Date(System.currentTimeMillis());
            String time = sdf.format(date);
            int sec = Integer.parseInt(s.format(date));
            if (time.equals(restartTime) && sec > 0 && sec < 5) {
                if (!data.isAutoRestarting()) {
                    data.setAutoRestarting(true);
                    CountdownTimer timer = new CountdownTimer(plugin,
                            30,
                            () -> Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_START.getComponent(null))),
                            () -> {
                                Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_END.getComponent(null)));
                                Bukkit.getServer().savePlayers();
                                for (World world : Bukkit.getWorlds()) world.save();
                                Bukkit.getServer().shutdown();
                            },
                            (t) -> Bukkit.getServer().sendActionBar(Lang.AUTO_RESTART_TIME.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) })));
                    timer.scheduleTimer();
                }
            }
        }), 0L, 20L);
    }

    public void scheduleBoosterChecker() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (cacheManager.areBoosters()) {
                int id = cacheManager.getActiveBoosterID() != 0 ? cacheManager.getActiveBoosterID() : cacheManager.getNextBoosterQueueID();
                String name = cacheManager.getBoosterPlayerName(id);
                int multiplier = cacheManager.getBoosterMultiplier(id);
                long time = cacheManager.getBoosterTime(id);
                long duration = cacheManager.getBoosterDuration(id);
                float barProgress = (float) time / duration * 1 < 0 ? 0 : (float) time / duration * 1;

                if (boosterBar == null) boosterBar = BossBar.bossBar(Lang.BOOSTER_TITLE.getComponent(new String[] { String.valueOf(multiplier), BukkitUtils.parseSeconds(time) }), barProgress, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);

                if (cacheManager.isBoosterActive()) {
                    if (time > 0) {
                        boosterBar.name(Lang.BOOSTER_TITLE.getComponent(new String[] { String.valueOf(multiplier), BukkitUtils.parseSeconds(time) }));
                        boosterBar.progress(barProgress);
                    } else {
                        cacheManager.removeBooster(id);
                        Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BOOSTER_ENDED.getComponent(new String[] { String.valueOf(multiplier), name })));
                        Bukkit.getServer().hideBossBar(boosterBar);
                    }
                } else if (id != 0) {
                    cacheManager.setBoosterActive(id, true);
                    time = cacheManager.getBoosterTime(id);
                    boosterBar.name(Lang.BOOSTER_TITLE.getComponent(new String[] { String.valueOf(multiplier), BukkitUtils.parseSeconds(time) }));
                    boosterBar.progress(1);
                    Bukkit.getServer().sendMessage(Lang.ANNOUNCEMENT.getComponent(null).append(Lang.BROADCAST_BOOSTER_STARTED.getComponent(new String[] { String.valueOf(multiplier), name })));
                    Bukkit.getServer().showBossBar(boosterBar);
                }
            }
        }), 0L, 20L);
    }

    public void scheduleAFKChecker() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                if (!data.isAFK(uuid)) {
                    long time = data.getPlayerLastMovedTime(uuid);
                    long milliseconds = System.currentTimeMillis();
                    long currentTimeDifferance =  TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MILLISECONDS.toSeconds(time);
                    if (currentTimeDifferance > Setting.AFK_TIME.getValue()) {
                        data.addAFK(uuid);
                        updateDisplayName(player, true, false);
                    }
                }
            }
        }), 0L, 100L);
    }

    public void schedulePlayTimeChecker() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                cacheManager.setPlayTime(uuid, player.getStatistic(Statistic.PLAY_ONE_MINUTE));
            }
        }), 0L, 1200L);
    }

    public ItemStack createSpawner(EntityType type) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
        if (spawnerMeta != null) {
            CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
            spawnerCS.setSpawnedType(type);
            spawnerMeta.setBlockState(spawnerCS);
            spawnerMeta.displayName(Lang.SPAWNER_NAME.getComponent(new String[] { BukkitUtils.parseCapitalization(type.name()) }));
            spawner.setItemMeta(spawnerMeta);
        }
        return spawner;
    }

    public int getMaxHomes(Player player) {
        int defaultHomeAmount = Setting.DEFAULT_PLAYER_HOMES.getValue();
        int homeAmountGiven = Setting.ACCRUED_HOME_AMOUNT_GIVEN.getValue();
        int maxAccruedClaims = Setting.ACCRUED_HOME_MAX.getValue();
        int baseTimeRequired = Setting.ACCRUED_HOME_BASE_TIME_REQUIRED.getValue();

        int time = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
        int accruedHomes = time / baseTimeRequired * homeAmountGiven;
        if (accruedHomes > maxAccruedClaims) accruedHomes = maxAccruedClaims;

        return accruedHomes + defaultHomeAmount;
    }

    public HashMap<UUID, Long> sortByLong(Map<UUID, Long> hm) {
        List<Map.Entry<UUID, Long>> list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        HashMap<UUID, Long> temp = new LinkedHashMap<>();
        for (Map.Entry<UUID, Long> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }

    public HashMap<UUID, Integer> sortByInteger(Map<UUID, Integer> hm) {
        List<Map.Entry<UUID, Integer>> list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        HashMap<UUID, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<UUID, Integer> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }

    public ItemStack getEntityHead(Entity entity, int rng) {
        if (entity instanceof Player || entity.getType().equals(EntityType.ENDER_DRAGON) || entity.getType().equals(EntityType.WARDEN)) {
            return getEntityHead(entity);
        } else if (rng >= Setting.HEAD_DROP_RNG.getValue()) {
            return getEntityHead(entity);
        }
        return null;
    }

    public ItemStack getEntityHead(Entity entity) {
        Data data = GoldmanEssentials.getPlugin().getData();
        if (entity instanceof Player player) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setOwningPlayer(player);
            head.setItemMeta(headMeta);
            return head;
        } else if (entity instanceof EnderDragon) {
            return new ItemStack(Material.DRAGON_HEAD);
        } else if (entity instanceof Creeper creeper) {
            if (creeper.isPowered()) return EntityHead.valueOf("CHARGED_" + entity.getType().name()).getHead();
            else return new ItemStack(Material.CREEPER_HEAD);
        } else if (entity instanceof WitherSkeleton) {
            return new ItemStack(Material.WITHER_SKELETON_SKULL);
        } else if (entity instanceof Skeleton) {
            return new ItemStack(Material.SKELETON_SKULL);
        } else if (entity instanceof Zombie) {
            return new ItemStack(Material.ZOMBIE_HEAD);
        } else if (entity instanceof Warden) {
            return EntityHead.valueOf(entity.getType().name()).getHead();
        } else {
            String type = entity.getType().name();
            if (entity instanceof Sheep sheep) {
                if (sheep.name().equals(Component.text("jeb_"))) {
                    type = "RAINBOW_" + type;
                } else {
                    DyeColor color = sheep.getColor();
                    if (color != null) type = color.name() + "_" + type;
                    else type = "WHITE_" + type;
                }
            } else if (entity instanceof Axolotl axolotl) {
                Axolotl.Variant variant = axolotl.getVariant();
                type = variant.name() + "_" + type;
            } else if (entity instanceof Parrot parrot) {
                Parrot.Variant variant = parrot.getVariant();
                type = variant.name() + "_" + type;
            } else if (entity instanceof Llama llama) {
                if (entity instanceof TraderLlama traderLlama) {
                    Llama.Color color = traderLlama.getColor();
                    type = color.name() + "_" + type;
                } else {
                    Llama.Color color = llama.getColor();
                    type = color.name() + "_" + type;
                }
            } else if (entity instanceof Villager villager) {
                Villager.Profession villagerProfession = villager.getProfession();
                Villager.Type villagerType = villager.getVillagerType();
                if (villagerProfession != Villager.Profession.NONE) {
                    type = villagerType.name() + "_" + villagerProfession.name() + "_" + type;
                } else type = villagerType.name() + "_" + type;
            } else if (entity instanceof MushroomCow mushroomCow) {
                MushroomCow.Variant variant = mushroomCow.getVariant();
                type = variant.name() + "_" + type;
            } else if (entity instanceof Frog frog) {
                Frog.Variant variant = frog.getVariant();
                type = variant.name() + "_" + type;
            } else if (entity instanceof Horse horse) {
                Horse.Color color = horse.getColor();
                type = color.name() + "_" + type;
            } else if (entity instanceof Rabbit rabbit) {
                Rabbit.Type variant = rabbit.getRabbitType();
                type = variant.name() + "_" + type;
            }
            if (data.getEntityHeadKeys().contains(type)) return EntityHead.valueOf(type).getHead();
        }
        return null;
    }

    private String getPrestigeColor(int prestige) {
        if (prestige < 5) return "&a";
        else if (prestige < 10) return "&e";
        else if (prestige < 15) return "&6";
        else if (prestige < 20) return "&d";
        else if (prestige < 25) return "&5";
        else if (prestige < 30) return "&3";
        else if (prestige < 35) return "&9";
        else if (prestige < 40) return "&b";
        else if (prestige < 45) return "&c";
        else if (prestige < 50) return "&#1CFF11";
        else if (prestige < 55) return "&#F7FF00";
        else if (prestige < 60) return "&#FFB407";
        else if (prestige < 65) return "&#FF07F4";
        else if (prestige < 70) return "&#CB28FF";
        else if (prestige < 75) return "&#0FCCFF";
        else if (prestige < 80) return "&#0FFBFF";
        else if (prestige < 85) return "&#21FFC2";
        else if (prestige < 90) return "&#F9FF71";
        else if (prestige < 100) return "&#FF6262";
        else return "&#FF0000";
    }
}
