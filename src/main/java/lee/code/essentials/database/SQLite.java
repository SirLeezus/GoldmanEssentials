package lee.code.essentials.database;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;

public class SQLite {

    private Connection connection;
    private Statement statement;

    public void connect() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        connection = null;

        try {
            if (!plugin.getDataFolder().exists()) {
                boolean created = plugin.getDataFolder().mkdir();
            }
            File dbFile = new File(plugin.getDataFolder(), "database.db");
            if (!dbFile.exists()) {
                boolean created = dbFile.createNewFile();
            }
            String url = "jdbc:sqlite:" + dbFile.getPath();

            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

        } catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ResultSet getResult(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void loadTables() {
        //player data table
        update("CREATE TABLE IF NOT EXISTS player_data (" +
                "`player` varchar PRIMARY KEY," +
                "`balance` varchar NOT NULL," +
                "`ranked` varchar NOT NULL," +
                "`perms` varchar NOT NULL," +
                "`prefix` varchar NOT NULL," +
                "`suffix` varchar NOT NULL," +
                "`color` varchar NOT NULL," +
                "`level` varchar NOT NULL," +
                "`prestige` varchar NOT NULL," +
                "`vanish` varchar NOT NULL," +
                "`god` varchar NOT NULL," +
                "`homes` varchar NOT NULL," +
                "`flying` varchar NOT NULL" +
                ");");

        //server data table
        update("CREATE TABLE IF NOT EXISTS server (" +
                "`server` varchar PRIMARY KEY," +
                "`spawn` varchar NOT NULL," +
                "`joins` varchar NOT NULL" +
                ");");

        //punishment data table
        update("CREATE TABLE IF NOT EXISTS punishment (" +
                "`player` varchar PRIMARY KEY," +
                "`staff` varchar NOT NULL," +
                "`datebanned` varchar NOT NULL," +
                "`datemuted` varchar NOT NULL," +
                "`banned` varchar NOT NULL," +
                "`tempbanned` varchar NOT NULL," +
                "`tempmuted` varchar NOT NULL," +
                "`muted` varchar NOT NULL," +
                "`banreason` varchar NOT NULL," +
                "`mutereason` varchar NOT NULL," +
                "`bot` varchar NOT NULL" +
                ");");

        //server data table
        update("CREATE TABLE IF NOT EXISTS boosters (" +
                "`id` varchar PRIMARY KEY," +
                "`player` varchar NOT NULL," +
                "`multiplier` varchar NOT NULL," +
                "`time` varchar NOT NULL," +
                "`active` varchar NOT NULL," +
                "`duration` varchar NOT NULL" +
                ");");
    }

    //BOOSTER DATA TABLE

    public void setBoosterData(String id, String uuid, String multiplier, String time, String active, String duration) {
        update("INSERT OR REPLACE INTO boosters (id, player, multiplier, time, active, duration) VALUES( '" + id + "','" + uuid + "','" + multiplier + "','" + time + "','" + active + "','" + duration + "');");
    }

    public void setBoosterTime(String id, String time) {
        update("UPDATE boosters SET time = '" + time + "' WHERE id ='" + id + "';");
    }

    public void setBoosterActive(String id, String active) {
        update("UPDATE boosters SET active = '" + active + "' WHERE id ='" + id + "';");
    }

    public void removeBooster(String id) {
        update("DELETE FROM boosters WHERE id = '" + id + "';");
    }

    //SERVER TABLE

    public void createServerDataColumn() {
        try {
            ResultSet rs = getResult("SELECT 1 FROM server;");
            if (!rs.next()) update("INSERT OR REPLACE INTO server (server, spawn, joins) VALUES( 'server','" + 0 + "','" + 0 + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSpawn(String location) {
        update("UPDATE server SET spawn ='" + location + "' WHERE server ='" + "server" + "';");
    }

    public void setJoins(String joins) {
        update("UPDATE server SET joins = '" + joins + "' WHERE server ='" + "server" + "';");
    }

    //PUNISHMENT DATA TABLE

    public void setPunishmentData(String uuid, String staff, String datebanned, String datemuted, String banned, String tempbanned, String tempmuted, String muted, String banreason, String mutereason, String bot) {
        update("INSERT OR REPLACE INTO punishment (player, staff, datebanned, datemuted, banned, tempbanned, tempmuted, muted, banreason, mutereason, bot) VALUES( '" + uuid + "','" + staff + "','" + datebanned + "','" + datemuted + "','" + banned + "','" + tempbanned + "','" + tempmuted + "','" + muted + "','" + banreason + "','" + mutereason + "','" + bot + "');");
    }

    public void setBanned(String uuid, String value, String reason) {
        update("UPDATE punishment SET banned ='" + value + "' WHERE player ='" + uuid + "';");
        update("UPDATE punishment SET banreason ='" + reason + "' WHERE player ='" + uuid + "';");
    }

    public void setTempBanned(String uuid, long time, String reason) {
        update("UPDATE punishment SET tempbanned = '" + time + "' WHERE player ='" + uuid + "';");
        update("UPDATE punishment SET banreason = '" + reason + "' WHERE player ='" + uuid + "';");
    }

    public void setMuted(String uuid, String value, String reason) {
        update("UPDATE punishment SET muted ='" + value + "' WHERE player ='" + uuid + "';");
        update("UPDATE punishment SET mutereason ='" + reason + "' WHERE player ='" + uuid + "';");
    }

    public void setTempMuted(String uuid, long time, String reason) {
        update("UPDATE punishment SET tempmuted = '" + time + "' WHERE player ='" + uuid + "';");
        update("UPDATE punishment SET mutereason = '" + reason + "' WHERE player ='" + uuid + "';");
    }

    public void setDateBanned(String uuid, String date) {
        update("UPDATE punishment SET datebanned = '" + date + "' WHERE player ='" + uuid + "';");
    }

    public void setStaffWhoPunished(String uuid, String date) {
        update("UPDATE punishment SET staff = '" + date + "' WHERE player ='" + uuid + "';");
    }

    public void setDateMuted(String uuid, String date) {
        update("UPDATE punishment SET datemuted = '" + date + "' WHERE player ='" + uuid + "';");
    }

    public void setBotChecked(String uuid, String bot) {
        update("UPDATE punishment SET bot = '" + bot + "' WHERE player ='" + uuid + "';");
    }

    //PLAYER DATA TABLE

    public void setPlayerData(String uuid, String balance, String ranked, String perms, String prefix, String suffix, String color, String level, String prestige, String vanish, String god, String homes, String flying) {
        update("INSERT OR REPLACE INTO player_data (player, balance, ranked, perms, prefix, suffix, color, level, prestige, vanish, god, homes, flying) VALUES( '" + uuid + "','" + balance + "','" + ranked + "','" + perms + "','" + prefix + "','" + suffix + "','" + color + "','" + level + "','" + prestige + "','" + vanish + "','" + god + "','" + homes + "','" + flying + "');");
    }

    public void deposit(String uuid, String value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + uuid + "';");
    }

    public void withdraw(String uuid, String value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + uuid + "';");
    }

    public void setBalance(String uuid, String value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + uuid + "';");
    }

    public void setVanish(String uuid, String value) {
        update("UPDATE player_data SET vanish ='" + value + "' WHERE player ='" + uuid + "';");
    }

    public void setGod(String uuid, String value) {
        update("UPDATE player_data SET god ='" + value + "' WHERE player ='" + uuid + "';");
    }

    public void setPrefix(String uuid, String prefix) {
        update("UPDATE player_data SET prefix ='" + prefix + "' WHERE player ='" + uuid + "';");
    }

    public void setSuffix(String uuid, String suffix) {
        update("UPDATE player_data SET suffix ='" + suffix + "' WHERE player ='" + uuid + "';");
    }

    public void setColor(String uuid, String color) {
        update("UPDATE player_data SET color ='" + color + "' WHERE player ='" + uuid + "';");
    }

    public void setRank(String uuid, String rank) {
        update("UPDATE player_data SET ranked ='" + rank + "' WHERE player ='" + uuid + "';");
    }

    public void setPerm(String uuid, String perms) {
        update("UPDATE player_data SET perms ='" + perms + "' WHERE player ='" + uuid + "';");
    }

    public void removePerm(String uuid, String perms) {
        update("UPDATE player_data SET perms ='" + perms + "' WHERE player ='" + uuid + "';");
    }

    public void setLevel(String uuid, String level) {
        update("UPDATE player_data SET level ='" + level + "' WHERE player ='" + uuid + "';");
    }

    public void setPrestige(String uuid, String prestige) {
        update("UPDATE player_data SET prestige ='" + prestige + "' WHERE player ='" + uuid + "';");
    }

    public void setHomes(String uuid, String homes) {
        update("UPDATE player_data SET homes ='" + homes + "' WHERE player ='" + uuid + "';");
    }

    public void setFlying(String uuid, String flying) {
        update("UPDATE player_data SET flying ='" + flying + "' WHERE player ='" + uuid + "';");
    }

    public void loadPunishmentData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        try {
            ResultSet rs = getResult("SELECT * FROM punishment;");

            int count = 0;
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("player"));
                String staff = rs.getString("staff");
                String datebanned = rs.getString("datebanned");
                String datemuted = rs.getString("datemuted");
                String banned = rs.getString("banned");
                String tempbanned = rs.getString("tempbanned");
                String tempmuted = rs.getString("tempmuted");
                String muted = rs.getString("muted");
                String banreason = rs.getString("banreason");
                String mutereason = rs.getString("mutereason");
                String bot = rs.getString("bot");
                cache.setPunishmentData(uuid, staff, datebanned, datemuted, banned, tempbanned, tempmuted, muted, banreason, mutereason, bot, false);
                if (!banned.equals("0")) cache.setBanList(uuid, true);
                else if (!tempbanned.equals("0")) cache.setBanList(uuid, true);
                count++;
            }
            Bukkit.getLogger().log(Level.INFO, plugin.getPU().format("&bPlayer Punishment Data Loaded: &3" + count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        try {
            ResultSet rs = getResult("SELECT * FROM player_data;");

            int count = 0;
            while(rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("player"));
                String balance = rs.getString("balance");
                String ranked = rs.getString("ranked");
                String perms = rs.getString("perms");
                String prefix = rs.getString("prefix");
                String suffix = rs.getString("suffix");
                String color = rs.getString("color");
                String level = rs.getString("level");
                String prestige = rs.getString("prestige");
                String vanish = rs.getString("vanish");
                String god = rs.getString("god");
                String homes = rs.getString("homes");
                String flying = rs.getString("flying");
                cache.setPlayerData(uuid, balance, ranked, perms, prefix, suffix, color, level, prestige, vanish, god, homes, flying, false);
                count++;
            }
            Bukkit.getLogger().log(Level.INFO, plugin.getPU().format("&bPlayer Account Data Loaded: &3" + count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBoosterData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        try {
            ResultSet rs = getResult("SELECT * FROM boosters;");

            int count = 0;
            while (rs.next()) {

                String id = rs.getString("id");
                UUID uuid = UUID.fromString(rs.getString("player"));
                String multiplier = rs.getString("multiplier");
                String time = rs.getString("time");
                String active = rs.getString("active");
                String duration = rs.getString("duration");

                cache.setBoosterData(id, uuid, multiplier, time, active, duration, false);
                count++;
            }
            Bukkit.getLogger().log(Level.INFO, plugin.getPU().format("&bBoosters Loaded: &3" + count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadServerData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        try {
            ResultSet rs = getResult("SELECT * FROM server WHERE server = 'server';");
            if (rs.next()) {
                String spawn = rs.getString("spawn");
                String joins = rs.getString("joins");
                cache.setServerData(spawn, joins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
