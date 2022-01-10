package lee.code.essentials;

import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.CustomCraftingRecipes;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import lee.code.essentials.menusystem.PlayerMU;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    @Getter private final List<String> worldNames = new ArrayList<>();
    @Getter private final List<String> enchantNames = new ArrayList<>();
    @Getter private final List<String> entityNames = new ArrayList<>();
    @Getter private final List<String> colorNames = new ArrayList<>();
    @Getter private final List<String> soundNames = new ArrayList<>();
    @Getter private final List<String> advancementNames = new ArrayList<>();
    @Getter private final List<String> materialNames = new ArrayList<>();
    @Getter private final List<UUID> vanishedPlayers = new ArrayList<>();
    @Getter private final List<UUID> playerClickDelay = new ArrayList<>();
    @Getter private final List<NamespacedKey> recipeKeys = new ArrayList<>();
    @Getter private final List<Component> serverMOTD = new ArrayList<>();
    @Getter private final List<String> pluginCommands = new ArrayList<>();
    @Getter private final List<UUID> staffChat = new ArrayList<>();
    @Getter private final List<UUID> afkPlayers = new ArrayList<>();
    @Getter private final List<String> whitelistedWorlds = new ArrayList<>();
    @Getter @Setter private int teamNumber = 0;
    @Getter @Setter private int lastBroadcast = 0;

    private final ConcurrentHashMap<UUID, PlayerMU> playerMUList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playersRequestingTeleport = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> playersBackLocations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> activeArmorStands = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerRTPTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Long> playerRTPTimer = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Integer> playerRTPAttempts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerSpamTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> resourceWorldMenuTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<UUID>> sleepingPlayers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BukkitTask> sleepTasks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Long> playerLastMovedTimer = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playerRequestingTrade = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerRequestTradeTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playerRequestingDuel = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerRequestDuelTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playerCurrentlyDueling = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Integer> playerSpamLoggerCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Component> playerSpamLoggerString = new ConcurrentHashMap<>();

    public boolean addSpamLoggerViolationCount(UUID uuid) {
        if (!playerSpamLoggerCount.containsKey(uuid)) playerSpamLoggerCount.put(uuid, 1);
        else playerSpamLoggerCount.put(uuid, playerSpamLoggerCount.get(uuid) + 1);
        return playerSpamLoggerCount.get(uuid) >= Settings.SPAM_ATTEMPTS.getValue();
    }
    public boolean isSpamLoggerViolation(UUID uuid, Component component) {
        if (!playerSpamLoggerString.containsKey(uuid)) return false;
        else return playerSpamLoggerString.get(uuid).equals(component);
    }
    public void resetSpamLogger(UUID uuid, Component component) {
        playerSpamLoggerString.put(uuid, component);
        playerSpamLoggerCount.put(uuid, 1);
    }

    public boolean isDuelRequestingPlayer(UUID owner, UUID target) {
        if (!playerRequestingDuel.containsKey(owner)) return false;
        else return playerRequestingDuel.get(owner).equals(target);
    }
    public void removeDuelRequesting(UUID uuid) {
        playerRequestingDuel.remove(uuid);
        getDuelRequestTask(uuid).cancel();
        removeDuelRequestTask(uuid);
    }
    public boolean isDuelingPlayer(UUID uuid, UUID target) {
        if (playerCurrentlyDueling.contains(uuid)) return playerCurrentlyDueling.get(uuid).equals(target);
        else if (playerCurrentlyDueling.contains(target)) return playerCurrentlyDueling.get(target).equals(uuid);
        else return false;
    }
    public void removeDuelingPlayer(UUID uuid, UUID target) {
        playerCurrentlyDueling.remove(uuid);
        playerCurrentlyDueling.remove(target);
    }
    public void setDuelingPlayer(UUID uuid, UUID target) {
        playerCurrentlyDueling.put(uuid, target);
        playerCurrentlyDueling.put(target, uuid);
    }
    public void setDuelRequesting(UUID owner, UUID target) { playerRequestingDuel.put(owner, target); }
    private BukkitTask getDuelRequestTask(UUID uuid) { return playerRequestDuelTask.get(uuid); }
    private void removeDuelRequestTask(UUID uuid) { playerRequestDuelTask.remove(uuid); }
    public void setDuelRequestTask(UUID uuid, BukkitTask task) { playerRequestDuelTask.put(uuid, task); }
    public boolean isDuelCurrent(UUID uuid) { return playerCurrentlyDueling.contains(uuid); }
    public UUID getDuelCurrentPlayer(UUID uuid) { return playerCurrentlyDueling.get(uuid); }

    public void setTradeRequesting(UUID owner, UUID trader) { playerRequestingTrade.put(owner, trader); }
    public void removeTradeRequesting(UUID uuid) {
        playerRequestingTrade.remove(uuid);
        getTradeRequestTask(uuid).cancel();
        removeTradeRequestTask(uuid);
    }
    public boolean isTradeRequestingPlayer(UUID owner, UUID trader) {
        if (!playerRequestingTrade.containsKey(owner)) return false;
        else return playerRequestingTrade.get(owner).equals(trader);
    }
    public void setTradeRequestTask(UUID uuid, BukkitTask task) { playerRequestTradeTask.put(uuid, task); }
    private void removeTradeRequestTask(UUID uuid) { playerRequestTradeTask.remove(uuid); }
    private BukkitTask getTradeRequestTask(UUID uuid) { return playerRequestTradeTask.get(uuid); }

    public boolean isAFK(UUID uuid) { return afkPlayers.contains(uuid); }
    public void addAFK(UUID uuid) { afkPlayers.add(uuid); }
    public void removeAFK(UUID uuid) { afkPlayers.remove(uuid); }

    public void addStaffChat(UUID uuid) { staffChat.add(uuid); }
    public void removeStaffChat(UUID uuid) { staffChat.remove(uuid); }
    public boolean isStaffChatting(UUID uuid) {return staffChat.contains(uuid); }

    public boolean isPlayerRequestingTeleportForTarget(UUID player, UUID target) { return playersRequestingTeleport.get(player) == target; }
    public void setPlayerRequestingTeleport(UUID player, UUID target) {
        playersRequestingTeleport.put(player, target);
    }
    public void removePlayerRequestingTeleport(UUID player) {
        playersRequestingTeleport.remove(player);
    }

    public boolean isArmorStandActive(UUID target) {
        return activeArmorStands.containsValue(target);
    }
    public void setArmorStandActive(UUID player, UUID target) {
        activeArmorStands.put(player, target);
    }
    public void removeArmorStandActive(UUID player) {
        activeArmorStands.remove(player);
    }

    public int getRTPAttempt(UUID player) {
        return playerRTPAttempts.getOrDefault(player, 0);
    }
    public void clearRTPAttempts(UUID player) { playerRTPAttempts.remove(player); }
    public void addRTPAttempt(UUID player, int number) { playerRTPAttempts.put(player, getRTPAttempt(player) + number); }

    public boolean isRTPTaskActive(UUID player) {
        return playerRTPTask.containsKey(player);
    }
    public void addRTPTaskActive(UUID player, BukkitTask task) {
        playerRTPTask.put(player, task);
    }
    public void removeRTPTaskActive(UUID player) {
        playerRTPTask.remove(player);
    }
    public BukkitTask getRTPDelayTask(UUID uuid) {
        return playerRTPTask.get(uuid);
    }

    public void setBackLocation(UUID uuid, String location) { playersBackLocations.put(uuid, location); }
    public String getBackLocation(UUID uuid) { return playersBackLocations.get(uuid); }
    public boolean hasBackLocation(UUID uuid) { return playersBackLocations.containsKey(uuid); }

    public void setRTPTimer(UUID player, long time) {
        playerRTPTimer.put(player, time);
    }
    public void removeRTPTimer(UUID player) {
        playerRTPTimer.remove(player);
    }
    public long getRTPTimer(UUID player) { return playerRTPTimer.getOrDefault(player, 0L); }

    public boolean isResourceWorldTaskActive(UUID player) {
        return resourceWorldMenuTask.containsKey(player);
    }
    public void addResourceWorldTaskActive(UUID player, BukkitTask task) {
        resourceWorldMenuTask.put(player, task);
    }
    public void removeResourceWorldTaskActive(UUID player) {
        resourceWorldMenuTask.remove(player);
    }
    public BukkitTask getResourceWorldTask(UUID uuid) {
        return resourceWorldMenuTask.get(uuid);
    }

    public boolean isSpamTaskActive(UUID player) {
        return playerSpamTask.containsKey(player);
    }
    public void addSpamTaskActive(UUID player, BukkitTask task) {
        playerSpamTask.put(player, task);
    }
    public void removeSpamTaskActive(UUID player) {
        playerSpamTask.remove(player);
    }
    public BukkitTask getSpamDelayTask(UUID uuid) {
        return playerSpamTask.get(uuid);
    }

    public void addVanishedPlayer(UUID uuid) {
        vanishedPlayers.add(uuid);
    }
    public void removeVanishedPlayer(UUID uuid) {
        vanishedPlayers.remove(uuid);
    }
    public boolean arePlayersVanished() {
        return !vanishedPlayers.isEmpty();
    }

    public void setPlayerLastMovedTime(UUID uuid, long time) {
        playerLastMovedTimer.put(uuid, time);
    }
    public long getPlayerLastMovedTime(UUID uuid) { return playerLastMovedTimer.get(uuid); }

    public boolean hasPlayerClickDelay(UUID uuid) {
        return playerClickDelay.contains(uuid);
    }
    public void addPlayerClickDelay(UUID uuid) {
        playerClickDelay.add(uuid);
    }
    public void removePlayerClickDelay(UUID uuid) {
        playerClickDelay.remove(uuid);
    }

    public void addSleepingPlayer(String world, UUID uuid) {
        List<UUID> newList = new ArrayList<>(sleepingPlayers.getOrDefault(world, new ArrayList<>()));
        newList.add(uuid);
        sleepingPlayers.put(world, newList);
    }

    public void removeSleepingPlayer(String world, UUID uuid) {
        List<UUID> newList = new ArrayList<>(sleepingPlayers.get(world));
        newList.remove(uuid);
        sleepingPlayers.put(world, newList);
    }

    public BukkitTask getSleepTask(String world) { return sleepTasks.getOrDefault(world, null); }
    public void addSleepTask(String world, BukkitTask task) { sleepTasks.put(world, task); }
    public void removeSleepTask(String world) { sleepTasks.remove(world); }

    public void clearSleepingPlayers(String world) { sleepingPlayers.put(world, new ArrayList<>()); }
    public boolean isSleepingPlayer(String world, UUID uuid) { return sleepingPlayers.getOrDefault(world, new ArrayList<>()).contains(uuid); }
    public int getSleepingPlayersSize(String world) { return sleepingPlayers.get(world).size(); }
    public List<UUID> getSleepingPlayers(String world) { return sleepingPlayers.get(world); }

    public void cacheDatabase() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        SQL.createServerDataColumn();
        SQL.loadPlayerData();
        SQL.loadBoosterData();
        SQL.loadServerData();
        SQL.loadPunishmentData();
    }

    public PlayerMU getPlayerMU(UUID uuid) {
        if (playerMUList.containsKey(uuid)) {
            return playerMUList.get(uuid);
        } else {
            PlayerMU pmu = new PlayerMU(uuid);
            playerMUList.put(uuid, pmu);
            return pmu;
        }
    }

    public void loadMOTDFile() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        String line;
        serverMOTD.clear();
        try {
            File file = new File(plugin.getDataFolder(), "motd.txt");
            if (!file.exists()) {
                boolean created = file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                if (line.contains("{store}")) {
                    serverMOTD.add(plugin.getPU().formatC(line.replace("{store}", Lang.STORE.getString())).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.STORE.getString())));
                } else if (line.contains("{discord}")) {
                    serverMOTD.add(plugin.getPU().formatC(line.replace("{discord}", Lang.DISCORD.getString())).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.DISCORD.getString())));
                } else if (line.contains("{map}")) {
                    serverMOTD.add(plugin.getPU().formatC(line.replace("{map}", Lang.MAP.getString())).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.MAP.getString())));
                } else serverMOTD.add(plugin.getPU().formatC(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadListData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        //plugin commands
        for (Command command : PluginCommandYamlParser.parse(plugin)) pluginCommands.add(command.getName());

        //worlds
        for (World selectedWorld : Bukkit.getWorlds()) worldNames.add(selectedWorld.getName());

        //chat colors
        for (ChatColor color : ChatColor.values()) colorNames.add(color.name());

        //sounds
        for (Sound sound : Sound.values()) soundNames.add(sound.name().toLowerCase());

        //entity names
        for (EntityType entity : EntityType.values()) if (entity != EntityType.UNKNOWN && entity != EntityType.PLAYER) entityNames.add(entity.name().toLowerCase());

        //enchants
        for (Enchantment enchantment : Enchantment.values()) enchantNames.add(enchantment.getKey().value());

        //materials
        for (Material material : Material.values()) materialNames.add(material.name().toLowerCase());

        //whitelisted worlds
        whitelistedWorlds.add("world");
        whitelistedWorlds.add("world_nether");
        whitelistedWorlds.add("world_the_end");

        //custom recipes
        for (String recipe : plugin.getPU().getCustomCraftingRecipes()) {
            CustomCraftingRecipes.valueOf(recipe).registerRecipe();
        }

        //recipes
        Iterator<Recipe> ita = Bukkit.getServer().recipeIterator();
        ita.forEachRemaining(recipe -> {
            if (recipe instanceof ShapelessRecipe shapelessRecipe) recipeKeys.add(shapelessRecipe.getKey());
            else if (recipe instanceof ShapedRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof BlastingRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof CampfireRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof FurnaceRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof SmithingRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof SmokingRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
            else if (recipe instanceof StonecuttingRecipe shapedRecipe) recipeKeys.add(shapedRecipe.getKey());
        });

        //advancements
        Iterator<Advancement> it = plugin.getServer().advancementIterator();
        while (it.hasNext()) {
            String key = it.next().getKey().getKey();
            if (!key.contains("/root")) {
                if (key.contains("story/") || key.contains("nether/") || key.contains("end/") || key.contains("adventure/") || key.contains("husbandry/")) advancementNames.add(key);
            }
        }
    }
}
