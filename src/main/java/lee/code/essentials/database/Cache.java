package lee.code.essentials.database;

import lee.code.cache.jedis.Jedis;
import lee.code.cache.jedis.JedisPool;
import lee.code.cache.jedis.Pipeline;
import lee.code.essentials.GoldmanEssentials;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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
            jedis.hset("rank", sUUID, rank);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setRank(sUUID, rank));
        }
    }

    public String getRank(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hget("rank", sUUID);
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

    public boolean hasPlayerData(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);

        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists("ranked", sUUID);
        }
    }

    public void setPlayerData(UUID uuid, int balance, String ranked, String perms, String prefix, String suffix, String color, boolean sql) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        JedisPool pool = plugin.getCacheAPI().getEssentialsPool();

        String sUUID = String.valueOf(uuid);
        String sBalance = String.valueOf(balance);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipe = jedis.pipelined();
            pipe.hset("balance", sUUID, sBalance);
            pipe.hset("ranked", sUUID, ranked);
            pipe.hset("perms", sUUID, perms);
            pipe.hset("prefix", sUUID, prefix);
            pipe.hset("suffix", sUUID, suffix);
            pipe.hset("color", sUUID, color);
            pipe.sync();
            if (sql) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> SQL.setPlayerData(sUUID, sBalance, ranked, prefix, suffix, suffix, color));
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
