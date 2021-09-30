package lee.code.essentials;

import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.CustomCraftingRecipes;
import lee.code.essentials.lists.Lang;
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

    private final ConcurrentHashMap<UUID, PlayerMU> playerMUList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playersRequestingTeleport = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> playersBackLocations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> activeArmorStands = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerPvPTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Long> playerPvPTimer = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> playerSpamTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> resourceWorldMenuTask = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<UUID>> sleepingPlayers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BukkitTask> sleepTasks = new ConcurrentHashMap<>();

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

    public boolean isPvPTaskActive(UUID player) {
        return playerPvPTask.containsKey(player);
    }
    public void addPvPTaskActive(UUID player, BukkitTask task) {
        playerPvPTask.put(player, task);
    }
    public void removePvPTaskActive(UUID player) {
        playerPvPTask.remove(player);
    }
    public BukkitTask getPvPDelayTask(UUID uuid) {
        return playerPvPTask.get(uuid);
    }

    public void setBackLocation(UUID uuid, String location) { playersBackLocations.put(uuid, location); }
    public String getBackLocation(UUID uuid) { return playersBackLocations.get(uuid); }
    public boolean hasBackLocation(UUID uuid) { return playersBackLocations.containsKey(uuid); }

    public void setPvPTimer(UUID player, long time) {
        playerPvPTimer.put(player, time);
    }
    public void removePvPTimer(UUID player) {
        playerPvPTimer.remove(player);
    }
    public long getPVPTimer(UUID player) { return playerPvPTimer.getOrDefault(player, 0L); }

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
