package lee.code.essentials.database;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.tables.BoosterTable;
import lee.code.essentials.database.tables.PlayerTable;
import lee.code.essentials.database.tables.PunishmentTable;
import lee.code.essentials.database.tables.ServerTable;
import lee.code.essentials.lists.Lang;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CacheManager {

    private final String serverKey = "server";

    @Getter
    private final Cache<UUID, PlayerTable> playerCache = CacheBuilder
            .newBuilder()
            .initialCapacity(5000)
            .recordStats()
            .build();

    @Getter
    private final Cache<String, ServerTable> serverCache = CacheBuilder
            .newBuilder()
            .initialCapacity(5000)
            .recordStats()
            .build();

    @Getter
    private final Cache<UUID, PunishmentTable> punishmentCache = CacheBuilder
            .newBuilder()
            .initialCapacity(5000)
            .recordStats()
            .build();

    @Getter
    private final Cache<Integer, BoosterTable> boosterCache = CacheBuilder
            .newBuilder()
            .initialCapacity(5000)
            .recordStats()
            .build();

    //server data

    private ServerTable getServerTable() {
        return getServerCache().getIfPresent(serverKey);
    }

    public void updateServerTable(ServerTable serverTable) {
        getServerCache().put(serverTable.getServer(), serverTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().updateServerTable(serverTable);
    }

    public void createServerData() {
        if (getServerCache().asMap().isEmpty()) {
            ServerTable serverTable = new ServerTable(serverKey);
            getServerCache().put(serverTable.getServer(), serverTable);
            GoldmanEssentials.getPlugin().getDatabaseManager().createServerTable(serverTable);
        }
    }

    public void setServerData(ServerTable serverTable) {
        getServerCache().put(serverTable.getServer(), serverTable);
    }

    public boolean isBotCheckEnabled() {
        return getServerTable().isBotCheckEnabled();
    }

    public Location getSpawn() {
        String spawn = getServerTable().getSpawn();
        if (!spawn.equals("0")) return BukkitUtils.parseLocation(spawn);
        else return null;
    }

    public void setSpawn(Location location) {
        ServerTable serverTable = getServerTable();
        String sLocation = BukkitUtils.serializeLocation(location);
        serverTable.setSpawn(sLocation);
        updateServerTable(serverTable);
    }

    public int getPlayerCounter() {
        return getServerTable().getJoins();
    }

    public void addPlayerCounter() {
        ServerTable serverTable = getServerTable();
        serverTable.setJoins(serverTable.getJoins() + 1);
        updateServerTable(serverTable);
    }

    public Location getWorldResourceSpawn() {
        ServerTable serverTable = getServerTable();
        if (serverTable.getWorldResourceSpawn().equals("0")) return null;
        else return BukkitUtils.parseLocation(serverTable.getWorldResourceSpawn());
    }

    public Location getNetherResourceSpawn() {
        ServerTable serverTable = getServerTable();
        if (serverTable.getNetherResourceSpawn().equals("0")) return null;
        else return BukkitUtils.parseLocation(serverTable.getNetherResourceSpawn());
    }

    public Location getEndResourceSpawn() {
        ServerTable serverTable = getServerTable();
        if (serverTable.getEndResourceSpawn().equals("0")) return null;
        else return BukkitUtils.parseLocation(serverTable.getEndResourceSpawn());
    }

    public void setWorldResourceSpawn(Location location) {
        ServerTable serverTable = getServerTable();
        serverTable.setWorldResourceSpawn(BukkitUtils.serializeLocation(location));
        updateServerTable(serverTable);
    }

    public void setNetherResourceSpawn(Location location) {
        ServerTable serverTable = getServerTable();
        serverTable.setNetherResourceSpawn(BukkitUtils.serializeLocation(location));
        updateServerTable(serverTable);
    }

    public void setEndResourceSpawn(Location location) {
        ServerTable serverTable = getServerTable();
        serverTable.setEndResourceSpawn(BukkitUtils.serializeLocation(location));
        updateServerTable(serverTable);
    }

    public void setResourceResetTime(int duration) {
        ServerTable serverTable = getServerTable();
        serverTable.setResourceResetTime(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + duration);
        updateServerTable(serverTable);
    }

    public long getResourceResetTime() {
        return getServerTable().getResourceResetTime() - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public boolean isResourceResetReady() {
        return getServerTable().getResourceResetTime() <= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    //player data

    public boolean hasPlayerData(UUID uuid) {
        return getPlayerCache().getIfPresent(uuid) != null;
    }

    public void createPlayerData(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().createPlayerTable(playerTable);
    }

    public void setPlayerData(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
    }

    public void createDefaultColumns(UUID uuid) {
        createPlayerData(new PlayerTable(uuid));
        createPunishmentData(new PunishmentTable(uuid));
    }

    private PlayerTable getPlayerTable(UUID player) {
        return getPlayerCache().getIfPresent(player);
    }

    private void updatePlayerTable(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().updatePlayerTable(playerTable);
    }

    public void deposit(UUID uuid, long amount) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setBalance(playerTable.getBalance() + amount);
        updatePlayerTable(playerTable);
    }

    public void withdraw(UUID uuid, long amount) {
        PlayerTable playerTable = getPlayerTable(uuid);
        long newBalance = (playerTable.getBalance() - amount < 0) ? 0 : (playerTable.getBalance() - amount);
        playerTable.setBalance(newBalance);
        updatePlayerTable(playerTable);
    }

    public void setBalance(UUID uuid, long amount) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setBalance(amount);
        updatePlayerTable(playerTable);
    }

    public long getBalance(UUID uuid) {
        return getPlayerTable(uuid).getBalance();
    }

    public void setPrefix(UUID uuid, String prefix) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setPrefix(prefix);
        updatePlayerTable(playerTable);
    }

    public String getPrefix(UUID uuid) {
        String prefix = getPlayerTable(uuid).getPrefix();
        return prefix.equals("0") ? "" : prefix;
    }

    public void setSuffix(UUID uuid, String suffix) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setSuffix(suffix);
        updatePlayerTable(playerTable);
    }

    public String getSuffix(UUID uuid) {
        String suffix = getPlayerTable(uuid).getSuffix();
        return suffix.equals("0") ? "" : suffix;
    }

    public void setColor(UUID uuid, String color) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setColor(color);
        updatePlayerTable(playerTable);
    }

    public ChatColor getColor(UUID uuid) {
        return ChatColor.valueOf(getPlayerTable(uuid).getColor());
    }

    public void setRank(UUID uuid, String rank) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setRank(rank);
        updatePlayerTable(playerTable);
    }

    public String getRank(UUID uuid) {
        return getPlayerTable(uuid).getRank();
    }

    public int getLevel(UUID uuid) {
        return getPlayerTable(uuid).getLevel();
    }

    public void setLevel(UUID uuid, int level) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setLevel(level);
        updatePlayerTable(playerTable);
    }

    public void addLevel(UUID uuid) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setLevel(playerTable.getLevel() + 1);
        updatePlayerTable(playerTable);
    }

    public void subtractLevel(UUID uuid) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setLevel(playerTable.getLevel() - 1);
        updatePlayerTable(playerTable);
    }

    public int getPrestige(UUID uuid) {
        return getPlayerTable(uuid).getPrestige();
    }

    public void addPrestige(UUID uuid) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setPrestige(playerTable.getPrestige() + 1);
        updatePlayerTable(playerTable);
    }

    public void addPerm(UUID uuid, String perm) {
        PlayerTable playerTable = getPlayerTable(uuid);
        StringBuilder sPerms = new StringBuilder(playerTable.getPerms());
        sPerms = sPerms.toString().equals("0") ? new StringBuilder(perm) : sPerms.append(",").append(perm);
        playerTable.setPerms(sPerms.toString());
        updatePlayerTable(playerTable);
    }

    public void addPermList(UUID uuid, List<String> newPerms) {
        PlayerTable playerTable = getPlayerTable(uuid);
        StringBuilder sPerms = new StringBuilder(playerTable.getPerms());
        if (sPerms.toString().equals("0")) sPerms = new StringBuilder();
        for (String sPerm : newPerms) {
            if (sPerms.isEmpty()) new StringBuilder(sPerm);
            else sPerms.append(",").append(sPerm);
        }
        playerTable.setPerms(sPerms.toString());
        updatePlayerTable(playerTable);
    }

    public void removePerm(UUID uuid, String perm) {
        PlayerTable playerTable = getPlayerTable(uuid);
        String sPerms = playerTable.getPerms();
        List<String> permList = new ArrayList<>(Arrays.asList(StringUtils.split(sPerms, ',')));
        permList.remove(perm);
        String newPerms = StringUtils.join(permList, ",");
        if (newPerms.isBlank()) newPerms = "0";
        playerTable.setPerms(newPerms);
        updatePlayerTable(playerTable);
    }

    public List<String> getPerms(UUID uuid) {
        return new ArrayList<>(Arrays.asList(StringUtils.split(getPlayerTable(uuid).getPerms(), ',')));
    }

    public boolean hasPerm(UUID uuid, String perm) {
        return getPlayerTable(uuid).getPerms().contains(perm);
    }

    public boolean isFlying(UUID uuid) {
        return getPlayerTable(uuid).isFlying();
    }

    public void setFlying(UUID uuid, boolean isFlying) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setFlying(isFlying);
        updatePlayerTable(playerTable);
    }

    public boolean isAlreadyHome(UUID uuid, String name) {
        PlayerTable playerTable = getPlayerTable(uuid);
        String homes = playerTable.getHomes();
        if (homes.equals("0")) return false;
        String[] split = StringUtils.split(homes, ',');

        for (String home : split) {
            int index = home.indexOf("+");
            String homeName = home.substring(0 , index);
            if (homeName.equals(name)) return true;
        }
        return false;
    }

    public boolean hasHome(UUID uuid) {
        return !getPlayerTable(uuid).getHomes().equals("0");
    }

    public List<String> getHomes(UUID uuid) {
        return new ArrayList<>(Arrays.asList(StringUtils.split(getPlayerTable(uuid).getHomes(), ',')));
    }

    public Location getHome(UUID uuid, String name) {
        PU pu = GoldmanEssentials.getPlugin().getPU();
        PlayerTable playerTable = getPlayerTable(uuid);
        String homes = playerTable.getHomes();
        String[] split = StringUtils.split(homes, ',');
        for (String home : split) {
            if (pu.parseHomeName(home).equals(name)) {
                return pu.parseHomeLocation(home);
            }
        }
        return null;
    }

    public List<String> getHomeNames(UUID uuid) {
        PlayerTable playerTable = getPlayerTable(uuid);
        String homes = playerTable.getHomes();
        if (homes.equals("0")) return Collections.singletonList("bed");
        String[] split = StringUtils.split(homes, ',');
        List<String> names = new ArrayList<>();
        names.add("bed");
        for (String home : split) names.add(GoldmanEssentials.getPlugin().getPU().parseHomeName(home));
        return names;
    }

    public void addHome(UUID uuid, String homeLocation) {
        PlayerTable playerTable = getPlayerTable(uuid);
        StringBuilder homes = new StringBuilder(playerTable.getHomes());
        homes = homes.toString().equals("0") ? new StringBuilder(homeLocation) : homes.append(",").append(homeLocation);
        playerTable.setHomes(homes.toString());
        updatePlayerTable(playerTable);
    }

    public void removeHome(UUID uuid, String name) {
        PlayerTable playerTable = getPlayerTable(uuid);
        String homes = playerTable.getHomes();

        List<String> homeList = new ArrayList<>();
        String[] split = StringUtils.split(homes, ',');
        for (String home : split) {
            int index = home.indexOf("+");
            String homeName = home.substring(0 , index);
            if (!homeName.equals(name)) homeList.add(home);
        }

        String newHomes = homeList.isEmpty() ? "0" : StringUtils.join(homeList, ",");
        playerTable.setHomes(newHomes);
        updatePlayerTable(playerTable);
    }

    public void setGodPlayer(UUID uuid, boolean isGod) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setGod(isGod);
        updatePlayerTable(playerTable);
    }

    public boolean isGodPlayer(UUID uuid) {
        return getPlayerTable(uuid).isGod();
    }

    public void setVanishPlayer(UUID uuid, boolean isVanish) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setVanish(isVanish);
        updatePlayerTable(playerTable);
    }

    public boolean isVanishPlayer(UUID uuid) {
        return getPlayerTable(uuid).isVanish();
    }

    public Map<UUID, Long> getTopBalances() {
        return getPlayerCache().asMap().values().stream().collect(Collectors.toMap(PlayerTable::getPlayer, PlayerTable::getBalance));
    }

    public Map<UUID, Long> getTopPlayTime() {
        return getPlayerCache().asMap().values().stream().collect(Collectors.toMap(PlayerTable::getPlayer, PlayerTable::getPlaytime));
    }

    public void setPlayTime(UUID uuid, long time) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setPlaytime(time);
        updatePlayerTable(playerTable);
    }

    public Map<UUID, Integer> getTopVotes() {
        return getPlayerCache().asMap().values().stream().collect(Collectors.toMap(PlayerTable::getPlayer, PlayerTable::getVotes));
    }

    public int getVotes(UUID uuid) {
        return getPlayerTable(uuid).getVotes();
    }

    public void addVote(UUID uuid) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setVotes(playerTable.getVotes() + 1);
        updatePlayerTable(playerTable);
    }

    public List<UUID> getUserList() {
        return new ArrayList<>(getPlayerCache().asMap().keySet());
    }

    //punishment data

    public boolean hasPunishmentData(UUID uuid) {
        return getPunishmentCache().getIfPresent(uuid) != null;
    }

    public void createPunishmentData(PunishmentTable punishmentTable) {
        getPunishmentCache().put(punishmentTable.getPlayer(), punishmentTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().createPunishmentTable(punishmentTable);
    }

    public void setPunishmentData(PunishmentTable punishmentTable) {
        getPunishmentCache().put(punishmentTable.getPlayer(), punishmentTable);
    }

    private PunishmentTable getPunishmentTable(UUID player) {
        return getPunishmentCache().getIfPresent(player);
    }

    private void updatePunishmentTable(PunishmentTable punishmentTable) {
        getPunishmentCache().put(punishmentTable.getPlayer(), punishmentTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().updatePunishmentTable(punishmentTable);
    }

    public Map<UUID, Long> getPunishList() {
        Map<UUID, Long> punMap = new HashMap<>();
        for (PunishmentTable punishmentTable : getPunishmentCache().asMap().values()) {
            if (punishmentTable.isBanned()) punMap.put(punishmentTable.getPlayer(), punishmentTable.getDateBanned());
            else if (punishmentTable.getTempBanned() > 0) punMap.put(punishmentTable.getPlayer(), punishmentTable.getDateBanned());
            else if (punishmentTable.isMuted()) punMap.put(punishmentTable.getPlayer(), punishmentTable.getDateMuted());
            else if (punishmentTable.getTempMuted() > 0) punMap.put(punishmentTable.getPlayer(), punishmentTable.getDateMuted());
        }
        return punMap;
    }

    public String getBanReason(UUID uuid) {
        return getPunishmentTable(uuid).getBanReason();
    }

    public long getTempBanTime(UUID uuid) {
        return getPunishmentTable(uuid).getTempBanned() - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public long getTempMuteTime(UUID uuid) {
        return getPunishmentTable(uuid).getTempMuted() - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public boolean isBanned(UUID uuid) {
        return getPunishmentTable(uuid).isBanned();
    }

    public boolean isTempBanned(UUID uuid) {
        return getPunishmentTable(uuid).getTempBanned() != 0;
    }

    public boolean isTempMuted(UUID uuid) {
        return getPunishmentTable(uuid).getTempMuted() != 0;
    }

    public Component shouldMute(UUID uuid) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        if (punishmentTable.isMuted()) {
            return Lang.PREFIX.getComponent(null).append(Lang.MUTED.getComponent(new String[]{ punishmentTable.getMuteReason() }));
        } else if (punishmentTable.getTempMuted() != 0) {
            long time = punishmentTable.getTempMuted() - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            if (time > 0) {
                return Lang.PREFIX.getComponent(null).append(Lang.TEMPMUTED.getComponent(new String[]{ BukkitUtils.parseSeconds(time), punishmentTable.getMuteReason() }));
            } else {
                setTempMutedPlayer(uuid, null, "", 0);
                return Lang.PREFIX.getComponent(null).append(Lang.TEMPUNMUTED.getComponent(null));
            }
        } else if (isBotCheckEnabled() && punishmentTable.isBot()) {
            return Lang.PREFIX.getComponent(null).append(Lang.BOTMUTED.getComponent(null));
        } else return null;
    }

    public boolean isMuted(UUID uuid) {
        return getPunishmentTable(uuid).isMuted();
    }

    public String getMuteReason(UUID uuid) {
        return getPunishmentTable(uuid).getMuteReason();
    }

    public long getBanDate(UUID uuid) {
        return getPunishmentTable(uuid).getDateBanned();
    }

    public void setBanDate(UUID uuid, long time) {
         PunishmentTable punishmentTable = getPunishmentTable(uuid);
         punishmentTable.setDateBanned(time);
         updatePunishmentTable(punishmentTable);
    }

    public String getBanStaffName(UUID uuid) {
        OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(UUID.fromString(getPunishmentTable(uuid).getBanStaff()));
        if (oPlayer.getName() != null) return oPlayer.getName();
        else return "Console";
    }

    public String getMuteStaffName(UUID uuid) {
        OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(UUID.fromString(getPunishmentTable(uuid).getMuteStaff()));
        if (oPlayer.getName() != null) return oPlayer.getName();
        else return "Console";
    }

    public void setMutedPlayer(UUID uuid, UUID staff, String reason, boolean isMuted) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        punishmentTable.setMuted(isMuted);
        punishmentTable.setMuteReason(reason);
        punishmentTable.setMuteStaff(String.valueOf(staff));
        punishmentTable.setDateMuted(System.currentTimeMillis());
        updatePunishmentTable(punishmentTable);
    }

    public void setBannedPlayer(UUID uuid, UUID staff, String reason, boolean isBanned) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        punishmentTable.setBanned(isBanned);
        punishmentTable.setBanStaff(staff == null ? "0" : String.valueOf(staff));
        punishmentTable.setBanReason(reason);
        punishmentTable.setDateBanned(System.currentTimeMillis());
        updatePunishmentTable(punishmentTable);
    }

    public void setTempBannedPlayer(UUID uuid, UUID staff, String reason, long time) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        punishmentTable.setTempBanned(time);
        punishmentTable.setBanReason(reason);
        punishmentTable.setBanStaff(staff == null ? "0" : String.valueOf(staff));
        punishmentTable.setDateBanned(System.currentTimeMillis());
        updatePunishmentTable(punishmentTable);
    }

    public void setTempMutedPlayer(UUID uuid, UUID staff, String reason, long time) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        punishmentTable.setTempMuted(time);
        punishmentTable.setMuteReason(reason);
        punishmentTable.setMuteStaff(staff == null ? "0" : String.valueOf(staff));
        punishmentTable.setDateMuted(System.currentTimeMillis());
        updatePunishmentTable(punishmentTable);
    }

    public boolean isBot(UUID uuid) {
        return getPunishmentTable(uuid).isBot();
    }

    public void setBotStatus(UUID uuid, boolean isBot) {
        PunishmentTable punishmentTable = getPunishmentTable(uuid);
        punishmentTable.setBot(isBot);
        updatePunishmentTable(punishmentTable);
    }

    //booster data

    public void setBoosterData(BoosterTable boosterTable) {
        getBoosterCache().put(boosterTable.getId(), boosterTable);
    }

    private BoosterTable getBoosterTable(int id) {
        return getBoosterCache().getIfPresent(id);
    }

    private void updateBoosterTable(BoosterTable boosterTable) {
        getBoosterCache().put(boosterTable.getId(), boosterTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().updateBoosterTable(boosterTable);
    }

    public void createBoosterData(int id, UUID uuid, int multiplier, long time, boolean active, long duration) {
        BoosterTable boosterTable = new BoosterTable(id, uuid, multiplier, time, active, duration);
        getBoosterCache().put(boosterTable.getId(), boosterTable);
        GoldmanEssentials.getPlugin().getDatabaseManager().createBoosterTable(boosterTable);
    }

    public void queueBooster(UUID uuid, int multiplier, long duration) {
        createBoosterData(getNextBoosterID(), uuid, multiplier, 0, false, duration);
    }

    private int getNextBoosterID() {
        if (getBoosterCache().asMap().isEmpty()) return 1;
        else return Collections.max(getBoosterCache().asMap().keySet()) + 1;
    }

    public boolean areBoosters() {
        return !getBoosterCache().asMap().isEmpty();
    }

    public boolean isBoosterActive() {
        return getBoosterCache().asMap().values().stream().anyMatch(BoosterTable::isActive);
    }

    public int getNextBoosterQueueID() {
        return Collections.min(getBoosterCache().asMap().keySet());
    }

    public List<Integer> getBoosterIDList() {
        return new ArrayList<>(getBoosterCache().asMap().keySet());
    }

    public List<String> getBoosterIDStringList() {
        return getBoosterCache().asMap().keySet().stream().map(String::valueOf).sorted().collect(Collectors.toList());
    }

    public long getBoosterTime(int id) {
        return getBoosterTable(id).getTime() - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public int getBoosterMultiplier(int id) {
        return getBoosterTable(id).getMultiplier();
    }

    public long getBoosterDuration(int id) {
        return getBoosterTable(id).getDuration();
    }

    public String getBoosterPlayerName(int id) {
        return Bukkit.getOfflinePlayer(getBoosterTable(id).getPlayer()).getName();
    }

    public void setBoosterActive(int id, boolean isActive) {
        BoosterTable boosterTable = getBoosterTable(id);
        boosterTable.setActive(isActive);
        boosterTable.setTime(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + boosterTable.getDuration());
        updateBoosterTable(boosterTable);
    }

    public void removeBooster(int id) {
        BoosterTable boosterTable = getBoosterTable(id);
        GoldmanEssentials.getPlugin().getDatabaseManager().deleteBoosterTable(boosterTable);
        getBoosterCache().invalidate(id);
    }

    public int getActiveBoosterID() {
        List<BoosterTable> booster = getBoosterCache().asMap().values().stream().filter(BoosterTable::isActive).toList();
        if (booster.isEmpty()) return 0;
        else return booster.get(0).getId();
    }
}
