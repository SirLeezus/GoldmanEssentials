package lee.code.essentials.database;

import jedis.Jedis;
import jedis.JedisPool;
import jedis.Pipeline;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.RankList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Cache {

    public Location getSpawn() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.get("spawn");
            if (!result.equals("0")) return plugin.getPU().unFormatPlayerLocation(result);
            else return null;
        }
    }

    public void setSpawn(Location location) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sLocation = plugin.getPU().formatPlayerLocation(location);

        try (Jedis jedis = pool.getResource()) {
            jedis.set("spawn", sLocation);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setSpawn(sLocation));
        }
    }

    public void deposit(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            int balance = Integer.parseInt(jedis.hget("balance", sUUID));
            String sNewBalance = String.valueOf(balance + amount);
            jedis.hset("balance", sUUID, sNewBalance);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.deposit(sUUID, sNewBalance));
        }
    }

    public void withdraw(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            long balance = Long.parseLong(jedis.hget("balance", sUUID));
            long newBalance = balance - amount;
            if (newBalance < 0) newBalance = 0;
            String sNewBalance = String.valueOf(newBalance);
            jedis.hset("balance", sUUID, sNewBalance);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.withdraw(sUUID, sNewBalance));
        }
    }

    public void setBalance(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String sNewBalance = String.valueOf(amount);
            jedis.hset("balance", sUUID, sNewBalance);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBalance(sUUID, sNewBalance));
        }
    }

    public long getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return Long.parseLong(jedis.hget("balance", sUUID));
        }
    }

    public void setPrefix(UUID uuid, String prefix) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("prefix", sUUID, prefix);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPrefix(sUUID, prefix));
        }
    }

    public String getPrefix(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String prefix = jedis.hget("prefix", sUUID);
            if (prefix.equals("n")) return "";
            else return prefix;
        }
    }

    public void setSuffix(UUID uuid, String suffix) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("suffix", sUUID, suffix);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setSuffix(sUUID, suffix));
        }
    }

    public String getSuffix(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String suffix = jedis.hget("suffix", sUUID);
            if (suffix.equals("n")) return "";
            else return suffix;
        }
    }

    public void setColor(UUID uuid, String color) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("color", sUUID, color);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setColor(sUUID, color));
        }
    }

    public String getColor(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("color", sUUID);
        }
    }

    public void setRank(UUID uuid, String rank) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("ranked", sUUID, rank);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setRank(sUUID, rank));
        }
    }

    public String getRank(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("ranked", sUUID);
        }
    }

    public int getLevel(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.hget("level", sUUID));
        }
    }

    public void setLevel(UUID uuid, String level) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("level", sUUID, level);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setLevel(sUUID, level));
        }
    }

    public void addLevel(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            int level = Integer.parseInt(jedis.hget("level", sUUID)) + 1;
            String sLevel = String.valueOf(level);
            jedis.hset("level", sUUID, sLevel);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setLevel(sUUID, sLevel));
        }
    }

    public void removeLevel(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            int level = Integer.parseInt(jedis.hget("level", sUUID)) - 1;
            if (level < 0) level = 0;
            String sLevel = String.valueOf(level);
            jedis.hset("level", sUUID, sLevel);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setLevel(sUUID, sLevel));
        }
    }

    public int getPrestige(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.hget("prestige", sUUID));
        }
    }

    public void addPrestige(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            int prestige = Integer.parseInt(jedis.hget("prestige", sUUID)) + 1;
            String sPrestige = String.valueOf(prestige);
            jedis.hset("prestige", sUUID, sPrestige);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPrestige(sUUID, sPrestige));
        }
    }

    public void addPerm(UUID uuid, String perm) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String perms = jedis.hget("perms", sUUID);
            String[] split = StringUtils.split(perms, ',');
            List<String> playerPerms = new ArrayList<>(Arrays.asList(split));
            if (!playerPerms.contains(perm)) {
                playerPerms.add(perm);
                String newPerms = StringUtils.join(playerPerms, ",");
                jedis.hset("perms", sUUID, newPerms);
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPerm(sUUID, newPerms));
            }
        }
    }

    public void addPermList(UUID uuid, List<String> newPerms) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String perms = jedis.hget("perms", sUUID);
            String[] split = StringUtils.split(perms, ',');
            List<String> playerPerms = new ArrayList<>(Arrays.asList(split));
            for (String perm : newPerms) if (!playerPerms.contains(perm)) playerPerms.add(perm);
            String newPermString = StringUtils.join(playerPerms, ",");
            jedis.hset("perms", sUUID, newPermString);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPerm(sUUID, newPermString));
        }
    }

    public void removePerm(UUID uuid, String perm) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String perms = jedis.hget("perms", sUUID);

            String[] split = StringUtils.split(perms, ',');
            List<String> permList = new ArrayList<>();
            for (String permission : split) if (!permission.equals(perm)) permList.add(permission);
            String newPerms = StringUtils.join(permList, ",");

            jedis.hset("perms", sUUID, newPerms);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.removePerm(sUUID, newPerms));
        }
    }

    public List<String> getPerms(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String perms = jedis.hget("perms", sUUID);

            String[] split = StringUtils.split(perms, ',');
            return new ArrayList<>(Arrays.asList(split));
        }
    }

    public boolean hasPerm(UUID uuid, String perm) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String perms = jedis.hget("perms", sUUID);

            String[] split = StringUtils.split(perms, ',');
            return new ArrayList<>(Arrays.asList(split)).contains(perm);
        }
    }

    public List<String> getBanList() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.hexists("banlist", "server")) {
                String bans = jedis.hget("banlist", "server");

                String[] split = StringUtils.split(bans, ',');
                return new ArrayList<>(Arrays.asList(split));
            } else return new ArrayList<>();
        }
    }

    public void setBanList(UUID uuid, boolean isBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            if (isBanned) {
                if (jedis.hexists("banlist", "server")) {
                    String bans = jedis.hget("banlist", "server");
                    if (bans.isBlank()) {
                        jedis.hset("banlist", "server", sUUID);
                    } else jedis.hset("banlist", "server", bans + "," + sUUID);
                } else jedis.hset("banlist", "server", sUUID);
            } else {
                String bans = jedis.hget("banlist", "server");
                String[] split = StringUtils.split(bans, ',');
                List<String> bannedPlayers = new ArrayList<>();
                for (String uuidPlayer : split) if (!uuidPlayer.equals(sUUID)) bannedPlayers.add(uuidPlayer);
                String newBannedPlayers = StringUtils.join(bannedPlayers, ",");
                jedis.hset("banlist", "server", newBannedPlayers);
            }
        }
    }

    public boolean isAlreadyHome(UUID uuid, String name) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);
            if (homes.equals("0")) return false;
            String[] split = StringUtils.split(homes, ',');

            for (String home : split) {
                int index = home.indexOf("+");
                String homeName = home.substring(0 , index);
                if (homeName.equals(name)) return true;
            }
            return false;
        }
    }

    public boolean hasHome(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);
            return !homes.equals("0");
        }
    }

    public List<String> getHomes(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            String[] split = StringUtils.split(homes, ',');
            return new ArrayList<>(Arrays.asList(split));
        }
    }

    public Location getHome(UUID uuid, String name) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            String[] split = StringUtils.split(homes, ',');
            for (String home : split) {
                if (pu.unFormatPlayerHomeName(home).equals(name)) {
                    return pu.unFormatPlayerHomeLocation(home);
                }
            }
            return null;
        }
    }

    public List<String> getHomeNames(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            if (homes.equals("0")) return Collections.singletonList("bed");

            String[] split = StringUtils.split(homes, ',');

            List<String> names = new ArrayList<>();
            names.add("bed");
            for (String home : split) names.add(plugin.getPU().unFormatPlayerHomeName(home));
            return names;
        }
    }

    public void addHome(UUID uuid, String homeLocation) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            String newHomes;
            if (!homes.equals("0")) newHomes = homes + "," + homeLocation;
            else newHomes = homeLocation;
            jedis.hset("homes", sUUID, newHomes);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setHomes(sUUID, newHomes));
        }
    }

    public void removeHome(UUID uuid, String name) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            List<String> homeList = new ArrayList<>();
            String[] split = StringUtils.split(homes, ',');

            for (String home : split) {
                int index = home.indexOf("+");
                String homeName = home.substring(0 , index);
                if (!homeName.equals(name)) homeList.add(home);
            }

            String newHomes = homeList.isEmpty() ? "0" : StringUtils.join(homeList, ",");
            jedis.hset("homes", sUUID, newHomes);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setHomes(sUUID, newHomes));
        }
    }

    public boolean hasLastReplied(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists("lastReplied", sUUID);
        }
    }

    public void setLastReplied(UUID uuid, UUID target) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);
        String sTarget = String.valueOf(target);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("lastReplied", sUUID, sTarget);
        }
    }

    public UUID getLastReplied(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return UUID.fromString(jedis.hget("lastReplied", sUUID));
        }
    }

    public void setGodPlayer(UUID uuid, boolean isGod) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isGod) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("god", sUUID, result);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setGod(sUUID, result));
        }
    }

    public String getBanReason(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("banreason", sUUID);
        }
    }


    public long getTempBanTime(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            long time = Long.parseLong(jedis.hget("tempbanned", sUUID));
            long milliseconds = System.currentTimeMillis();
            return time - TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        }
    }

    public long getTempMuteTime(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            long time = Long.parseLong(jedis.hget("tempmuted", sUUID));
            long milliseconds = System.currentTimeMillis();
            return time - TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        }
    }

    public boolean isBanned(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return !jedis.hget("banned", sUUID).equals("0");
        }
    }

    public boolean isTempBanned(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return !jedis.hget("tempbanned", sUUID).equals("0");
        }
    }

    public boolean isTempMuted(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return !jedis.hget("tempmuted", sUUID).equals("0");
        }
    }

    public boolean isMuted(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return !jedis.hget("muted", sUUID).equals("0");
        }
    }

    public String getMuteReason(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("mutereason", sUUID);
        }
    }

    public String getBanDate(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("datebanned", sUUID);
        }
    }

    public void setBanDate(UUID uuid, boolean isBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String date = plugin.getPU().getDate();
        String result; if (isBanned) result = date; else result = "0";

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("datebanned", sUUID, result);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setDateBanned(sUUID, result));
        }
    }

    public UUID getStaffWhoPunished(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return UUID.fromString(jedis.hget("staffpunisher", sUUID));
        }
    }

    public void setStaffWhoPunished(UUID uuid, UUID staff, boolean isBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isBanned) result = String.valueOf(staff); else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("staffpunisher", sUUID, result);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setStaffWhoPunished(sUUID, result));
        }
    }

    public void setMutedPlayer(UUID uuid, String reason, boolean isMuted) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isMuted) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("muted", sUUID, result);
            jedis.hset("mutereason", sUUID, reason);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setMuted(sUUID, result, reason));
        }
    }

    public void setBannedPlayer(UUID uuid, UUID staff, String reason, boolean isBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isBanned) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("banned", sUUID, result);
            jedis.hset("banreason", sUUID, reason);

            setBanList(uuid, isBanned);
            setBanDate(uuid, isBanned);
            setStaffWhoPunished(uuid, staff, isBanned);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBanned(sUUID, result, reason));
        }
    }

    public void setTempBannedPlayer(UUID uuid, UUID staff, String reason, long time, boolean isTempBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("tempbanned", sUUID, String.valueOf(time));
            jedis.hset("banreason", sUUID, reason);

            setBanList(uuid, isTempBanned);
            setBanDate(uuid, isTempBanned);
            setStaffWhoPunished(uuid, staff, isTempBanned);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setTempBanned(sUUID, time, reason));
        }
    }

    public void setTempMutedPlayer(UUID uuid, String reason, long time, boolean isTempMuted) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("tempmuted", sUUID, String.valueOf(time));
            jedis.hset("mutereason", sUUID, reason);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setTempMuted(sUUID, time, reason));
        }
    }

    public boolean isGodPlayer(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String isGod = jedis.hget("god", sUUID);
            return !isGod.equals("0");
        }
    }

    public void setVanishPlayer(UUID uuid, boolean isVanish) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isVanish) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("vanish", sUUID, result);

            if (result.equals("1")) plugin.getData().addVanishedPlayer(uuid);
            else plugin.getData().removeVanishedPlayer(uuid);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setVanish(sUUID, result));
        }
    }

    public boolean isVanishPlayer(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String isVanish = jedis.hget("vanish", sUUID);
            return !isVanish.equals("0");
        }
    }

    public int getPlayerCounter() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.get("joins"));
        }
    }

    public void addPlayerCounter() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        try (Jedis jedis = pool.getResource()) {
            String joins = String.valueOf(Integer.parseInt(jedis.get("joins")) + 1);
            jedis.set("joins", joins);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setJoins(joins));
        }
    }

    public Map<String, String> getTopBalances() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return jedis.hgetAll("balance");
        }
    }

    public boolean hasBeenBotChecked(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String isChecked = jedis.hget("bot", sUUID);
            return !isChecked.equals("0");
        }
    }

    public boolean isFlying(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String isFlying = jedis.hget("flying", sUUID);
            return !isFlying.equals("0");
        }
    }

    public void setFlying(UUID uuid, boolean isFlying) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isFlying) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("flying", sUUID, result);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setFlying(sUUID, result));
        }
    }


    public void setBotChecked(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("bot", sUUID, "1");

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBotChecked(sUUID, "1"));
        }
    }

    public void queueBooster(UUID uuid, String multiplier, long duration) {
        setBoosterData(getNextBoosterID(), uuid, multiplier, "0", "0", String.valueOf(duration), true);
    }

    public boolean areBoosters() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return jedis.exists("boosterActive");
        }
    }

    public boolean isBoosterActive() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            List<String> activeList = jedis.hgetAll("boosterActive").values().stream().map(String::valueOf).collect(Collectors.toList());
            return activeList.contains("1");
        }
    }

    public String getNextBoosterQueueID() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("boosterPlayer")) {
                List<Integer> intList = jedis.hgetAll("boosterPlayer").keySet().stream().map(Integer::valueOf).collect(Collectors.toList());
                return String.valueOf(Collections.min(intList));
            } else return null;
        }
    }

    public List<Integer> getBoosterIDIntegerList() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("boosterPlayer")) {
                return jedis.hgetAll("boosterPlayer").keySet().stream().map(Integer::valueOf).sorted().collect(Collectors.toList());
            } else return null;
        }
    }

    public List<String> getBoosterIDStringList() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("boosterPlayer")) {
                return jedis.hgetAll("boosterPlayer").keySet().stream().map(String::valueOf).sorted().collect(Collectors.toList());
            } else return null;
        }
    }

    public long getBoosterTime(String id) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            long time = Long.parseLong(jedis.hget("boosterTime", id));
            long milliseconds = System.currentTimeMillis();
            return time - TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        }
    }

    public String getBoosterMultiplier(String id) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("boosterMultiplier", id);
        }
    }

    public long getBoosterDuration(String id) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return Long.parseLong(jedis.hget("boosterDuration", id));
        }
    }

    public String getBoosterPlayerName(String id) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            UUID uuid = UUID.fromString(jedis.hget("boosterPlayer", id));
            OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(uuid);
            return oPlayer.getName();
        }
    }

    public void setBoosterActive(String id, boolean isActive) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isActive) result = "1"; else result = "0";

        try (Jedis jedis = pool.getResource()) {
            String time = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + Long.parseLong(jedis.hget("boosterDuration", id)));
            jedis.hset("boosterActive", id, result);
            jedis.hset("boosterTime", id, time);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBoosterActive(id, result));
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBoosterTime(id, time));
        }
    }

    public void removeBooster(String id) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hdel("boosterPlayer", id);
            pipe.hdel("boosterMultiplier", id);
            pipe.hdel("boosterTime", id);
            pipe.hdel("boosterActive", id);
            pipe.hdel("boosterDuration", id);
            pipe.sync();

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.removeBooster(id));
        }
    }

    public String getActiveBoosterID() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            for (String id : jedis.hgetAll("boosterActive").keySet()) {
                if (jedis.hget("boosterActive", id).equals("1")) {
                    return id;
                }
            }
        }
        return null;
    }

    private String getNextBoosterID() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("boosterPlayer")) {
                List<Integer> intList = jedis.hgetAll("boosterPlayer").keySet().stream().map(Integer::valueOf).collect(Collectors.toList());
                int newID = Collections.max(intList) + 1;
                return String.valueOf(newID);
            } else return "1";
        }
    }

    public Location getWorldResourceSpawn() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.get("world_resource_spawn");
            if (!result.equals("0")) return plugin.getPU().unFormatPlayerLocation(result);
            else return null;
        }
    }

    public Location getNetherResourceSpawn() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.get("nether_resource_spawn");
            if (!result.equals("0")) return plugin.getPU().unFormatPlayerLocation(result);
            else return null;
        }
    }

    public Location getEndResourceSpawn() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.get("end_resource_spawn");
            if (!result.equals("0")) return plugin.getPU().unFormatPlayerLocation(result);
            else return null;
        }
    }

    public void setWorldResourceSpawn(Location location) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sLocation = plugin.getPU().formatPlayerLocation(location);

        try (Jedis jedis = pool.getResource()) {
            jedis.set("world_resource_spawn", sLocation);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setWorldResourceSpawn(sLocation));
        }
    }

    public void setNetherResourceSpawn(Location location) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sLocation = plugin.getPU().formatPlayerLocation(location);

        try (Jedis jedis = pool.getResource()) {
            jedis.set("nether_resource_spawn", sLocation);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setNetherResourceSpawn(sLocation));
        }
    }

    public void setEndResourceSpawn(Location location) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sLocation = plugin.getPU().formatPlayerLocation(location);

        try (Jedis jedis = pool.getResource()) {
            jedis.set("end_resource_spawn", sLocation);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setEndResourceSpawn(sLocation));
        }
    }

    public void setResourceWorldsTime(int duration) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        try (Jedis jedis = pool.getResource()) {
            long time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + duration;
            String newTime = String.valueOf(time);
            jedis.set("world_resource_time", newTime);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setResourceWorldsTime(newTime));
        }
    }

    public long getResourceWorldsTime() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            long time = Long.parseLong(jedis.get("world_resource_time"));
            long milliseconds = System.currentTimeMillis();
            return time - TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        }
    }

    public boolean isResourceWorldsResetReady() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            long time = Long.parseLong(jedis.get("world_resource_time"));
            long milliseconds = System.currentTimeMillis();
            return time <= TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        }
    }

    public boolean hasPlayerData(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists("ranked", sUUID);
        }
    }

    public boolean hasPunishmentData(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists("banned", sUUID);
        }
    }

    public int getPlayTime(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);
        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.hget("playtime", sUUID));
        }
    }

    public Map<String, String> getTopPlayTime() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return jedis.hgetAll("playtime");
        }
    }

    public void setPlayTime(UUID uuid, int time) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);
        String sTime = String.valueOf(time);
        try (Jedis jedis = pool.getResource()) {
            jedis.hset("playtime", sUUID, sTime);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPlayTime(sUUID, sTime));
        }
    }

    public int getTotalVotes(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        String sUUID = String.valueOf(uuid);
        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.hget("votes", sUUID));
        }
    }

    public void addVote(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);
        try (Jedis jedis = pool.getResource()) {
            String votes = String.valueOf(Integer.parseInt(jedis.hget("votes", sUUID)) + 1);
            jedis.hset("votes", sUUID, votes);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setVotes(sUUID, votes));
        }
    }

    private void addPlayerToUserList(String uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getChunksPool();

        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("userList")) {
                String list = jedis.get("userList");
                jedis.set("userList", list + "," + uuid);
            } else jedis.set("userList", uuid);
        }
    }

    public List<UUID> getUserList() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getChunksPool();

        try (Jedis jedis = pool.getResource()) {
            List<UUID> players = new ArrayList<>();
            if (jedis.exists("userList")) {
                String users = jedis.get("userList");
                String[] split = StringUtils.split(users, ',');
                for (String player : split) players.add(UUID.fromString(player));
                return players;
            } return players;
        }
    }

    public void createDefaultColumns(UUID uuid) {
        setPlayerData(uuid, "0", "NOMAD", "n", RankList.NOMAD.getPrefix(), "n", "YELLOW", "0", "0", "0", "0", "0", "0", "0", "0", true);
        setPunishmentData(uuid, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", true);
    }

    public void setPlayerData(UUID uuid, String balance, String ranked, String perms, String prefix, String suffix, String color, String level, String prestige, String vanish, String god, String homes, String flying, String votes, String playtime, boolean sql) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hset("balance", sUUID, balance);
            pipe.hset("ranked", sUUID, ranked);
            pipe.hset("perms", sUUID, perms);
            pipe.hset("prefix", sUUID, prefix);
            pipe.hset("suffix", sUUID, suffix);
            pipe.hset("color", sUUID, color);
            pipe.hset("level", sUUID, level);
            pipe.hset("prestige", sUUID, prestige);
            pipe.hset("vanish", sUUID, vanish);
            pipe.hset("god", sUUID, god);
            pipe.hset("homes", sUUID, homes);
            pipe.hset("flying", sUUID, flying);
            pipe.hset("votes", sUUID, votes);
            pipe.hset("playtime", sUUID, playtime);
            pipe.sync();
            addPlayerToUserList(sUUID);
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPlayerData(sUUID, balance, ranked, perms, prefix, suffix, color, level, prestige, vanish, god, homes, flying, votes, playtime));
        }
    }

    public void setBoosterData(String id, UUID uuid, String multiplier, String time, String active, String duration, boolean sql) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hset("boosterPlayer", id, sUUID);
            pipe.hset("boosterMultiplier", id, multiplier);
            pipe.hset("boosterTime", id, time);
            pipe.hset("boosterActive", id, active);
            pipe.hset("boosterDuration", id, duration);
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBoosterData(id, sUUID, multiplier, time, active, duration));
        }
    }

    public void setPunishmentData(UUID uuid, String staff, String datebanned, String datemuted, String banned, String tempbanned, String tempmuted, String muted, String banreason, String mutereason, String bot, boolean sql) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hset("staffpunisher", sUUID, staff);
            pipe.hset("datebanned", sUUID, datebanned);
            pipe.hset("datemuted", sUUID, datemuted);
            pipe.hset("banned", sUUID, banned);
            pipe.hset("tempbanned", sUUID, tempbanned);
            pipe.hset("tempmuted", sUUID, tempmuted);
            pipe.hset("muted", sUUID, muted);
            pipe.hset("banreason", sUUID, banreason);
            pipe.hset("mutereason", sUUID, mutereason);
            pipe.hset("bot", sUUID, bot);
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPunishmentData(sUUID, staff, datebanned, datemuted, banned, tempbanned, tempmuted, muted, banreason, mutereason, bot));
        }
    }

    public void setServerData(String spawn, String joins, String worldResourceTime, String worldResourceSpawn, String netherResourceSpawn, String endResourceSpawn) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            jedis.set("spawn", spawn);
            jedis.set("joins", joins);
            jedis.set("world_resource_time", worldResourceTime);
            jedis.set("world_resource_spawn", worldResourceSpawn);
            jedis.set("nether_resource_spawn", netherResourceSpawn);
            jedis.set("end_resource_spawn", endResourceSpawn);
        }
    }
}
