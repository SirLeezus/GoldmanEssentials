package lee.code.essentials;

import lee.code.essentials.database.SQLite;
import lee.code.essentials.menusystem.PlayerMU;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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
    @Getter private final List<UUID> sleepingPlayers = new ArrayList<>();
    @Getter private final List<UUID> playerClickDelay = new ArrayList<>();
    @Getter private final List<NamespacedKey> recipeKeys = new ArrayList<>();
    @Getter @Setter private BukkitTask sleepTask = null;

    private final ConcurrentHashMap<UUID, PlayerMU> playerMUList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> playersRequestingTeleport = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, UUID> activeArmorStands = new ConcurrentHashMap<>();

    public boolean isPlayerRequestingTeleportForTarget(UUID player, UUID target) {
        return playersRequestingTeleport.get(player) == target;
    }
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

    public void addVanishedPlayer(UUID uuid) {
        vanishedPlayers.add(uuid);
    }
    public void removeVanishedPlayer(UUID uuid) {
        vanishedPlayers.remove(uuid);
    }
    public boolean arePlayersVanished() {
        return !vanishedPlayers.isEmpty();
    }

    public void addSleepingPlayer(UUID uuid) {
        sleepingPlayers.add(uuid);
    }
    public void removeSleepingPlayer(UUID uuid) {
        sleepingPlayers.remove(uuid);
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

    public void cacheDatabase() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        SQL.loadPlayerData();
        SQL.loadServerData();
        SQL.loadBalanceTopPlayers();
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

    public void loadListData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        //worlds
        for (World selectedWorld : Bukkit.getWorlds()) {
            worldNames.add(selectedWorld.getName());
        }

        //chat colors
        for (ChatColor color : ChatColor.values()) {
            colorNames.add(color.name());
        }

        //sounds
        for (Sound sound : Sound.values()) {
            soundNames.add(sound.name().toLowerCase());
        }

        //entity names
        for (EntityType entity : EntityType.values()) {
            entityNames.add(entity.name().toLowerCase());
        }

        //enchants
        for (Enchantment enchantment : Enchantment.values()) {
            enchantNames.add(enchantment.getKey().value());
        }

        //materials
        for (Material material : Material.values()) {
            materialNames.add(material.name().toLowerCase());
        }

        //recipes
        Iterator<Recipe> ita = Bukkit.getServer().recipeIterator();
        ita.forEachRemaining(recipe -> {
            if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                recipeKeys.add(shapelessRecipe.getKey());
            } else if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof BlastingRecipe) {
                BlastingRecipe shapedRecipe = (BlastingRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof CampfireRecipe) {
                CampfireRecipe shapedRecipe = (CampfireRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof FurnaceRecipe) {
                FurnaceRecipe shapedRecipe = (FurnaceRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof SmithingRecipe) {
                SmithingRecipe shapedRecipe = (SmithingRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof SmokingRecipe) {
                SmokingRecipe shapedRecipe = (SmokingRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            } else if (recipe instanceof StonecuttingRecipe) {
                StonecuttingRecipe shapedRecipe = (StonecuttingRecipe) recipe;
                recipeKeys.add(shapedRecipe.getKey());
            }
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
