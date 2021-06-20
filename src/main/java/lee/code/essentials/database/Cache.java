package lee.code.essentials.database;

import jedis.Jedis;
import jedis.JedisPool;
import jedis.Pipeline;
import lee.code.essentials.GoldmanEssentials;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class Cache {

    public Location getSpawn() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        try (Jedis jedis = pool.getResource()) {
            return plugin.getPU().unFormatPlayerLocation(jedis.get("spawn"));
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

    public void deposit(UUID uuid, int amount) {
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

    public void withdraw(UUID uuid, int amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            int balance = Integer.parseInt(jedis.hget("balance", sUUID));
            int newBalance = balance - amount;
            if (newBalance < 0) newBalance = 0;
            String sNewBalance = String.valueOf(newBalance);
            jedis.hset("balance", sUUID, sNewBalance);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.withdraw(sUUID, sNewBalance));
        }
    }

    public void setBalance(UUID uuid, int amount) {
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

    public int getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return Integer.parseInt(jedis.hget("balance", sUUID));
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

    public void addBanList(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {

            if (jedis.hexists("banlist", "server")) {
                String bans = jedis.hget("banlist", "server");
                if (bans.isBlank()) {
                    jedis.hset("banlist", "server", sUUID);
                } else jedis.hset("banlist", "server", bans + "," + sUUID);
            } else jedis.hset("banlist", "server", sUUID);
        }
    }

    public void removeBanList(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String bans = jedis.hget("banlist", "server");
            String[] split = StringUtils.split(bans, ',');
            List<String> bannedPlayers = new ArrayList<>();
            for (String uuidPlayer : split) if (!uuidPlayer.equals(sUUID)) bannedPlayers.add(uuidPlayer);
            String newBannedPlayers = StringUtils.join(bannedPlayers, ",");
            jedis.hset("banlist", "server", newBannedPlayers);
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

    public List<String> getHomeNames(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            String homes = jedis.hget("homes", sUUID);

            String[] split = StringUtils.split(homes, ',');

            List<String> names = new ArrayList<>();
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

            String newHomes = StringUtils.join(homeList, ",");
            if (newHomes.isEmpty()) jedis.hset("homes", sUUID, "0");
            else jedis.hset("homes", sUUID, newHomes);
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
            return Long.parseLong(jedis.hget("tempbanned", sUUID));
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

    public void setBannedPlayer(UUID uuid, String reason, boolean isBanned) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String result; if (isBanned) result = "1"; else result = "0";
        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("banned", sUUID, result);
            jedis.hset("banreason", sUUID, reason);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setBanned(sUUID, result, reason));
        }
    }

    public void setTempBannedPlayer(UUID uuid, String reason, long time) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();
        SQLite SQL = plugin.getSqLite();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            jedis.hset("tempbanned", sUUID, String.valueOf(time));
            jedis.hset("banreason", sUUID, reason);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setTempBanned(sUUID, time, reason));
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

    public void setTopBalances(HashMap<String, String> balance) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            for (String uuid : balance.keySet()) {
                jedis.hset("topBalance", uuid, balance.get(uuid));
            }
        }
    }

    public Map<String, String> getTopBalances() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            return jedis.hgetAll("topBalance");
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

    public void setPlayerData(UUID uuid, String balance, String ranked, String perms, String prefix, String suffix, String color, String level, String prestige, String vanish, String god, String homes, boolean sql) {
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
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPlayerData(sUUID, balance, ranked, perms, prefix, suffix, color, level, prestige, vanish, god, homes));
        }
    }

    public void setPunishmentData(UUID uuid, String ip, String banned, String tempbanned, String ipbanned, String tempmuted, String muted, String banreason, String mutereason, boolean sql) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hset("ip", sUUID, ip);
            pipe.hset("banned", sUUID, banned);
            pipe.hset("tempbanned", sUUID, tempbanned);
            pipe.hset("ipbanned", sUUID, ipbanned);
            pipe.hset("tempmuted", sUUID, tempmuted);
            pipe.hset("muted", sUUID, muted);
            pipe.hset("banreason", sUUID, banreason);
            pipe.hset("mutereason", sUUID, mutereason);
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPunishmentData(sUUID, ip, banned, tempbanned, ipbanned, tempmuted, muted, banreason, mutereason));
        }
    }

    public void setServerData(String spawn) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        try (Jedis jedis = pool.getResource()) {
            jedis.set("spawn", spawn);
        }
    }
}
