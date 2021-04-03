package lee.code.essentials.database;

import lee.code.cache.jedis.Jedis;
import lee.code.cache.jedis.JedisPool;
import lee.code.cache.jedis.Pipeline;
import lee.code.essentials.GoldmanEssentials;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
                jedis.hset(perms, sUUID, newPerms);
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.addPerm(sUUID, newPerms));
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

    public boolean hasPlayerData(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists("ranked", sUUID);
        }
    }

    public void setPlayerData(UUID uuid, String balance, String ranked, String perms, String prefix, String suffix, String color, String level, String prestige, String vanish, String god, boolean sql) {
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
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPlayerData(sUUID, balance, ranked, perms, prefix, suffix, color, level, prestige, vanish, god));
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
