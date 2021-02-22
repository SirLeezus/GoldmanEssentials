package lee.code.essentials.database;

import lee.code.essentials.GoldmanEssentials;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SQLite {

    private Connection connection;
    private Statement statement;

    public void connect() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        connection = null;

        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File dbFile = new File(plugin.getDataFolder(), "database.db");
            if (!dbFile.exists()) {
                dbFile.createNewFile();
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
                "`balance` int NOT NULL," +
                "`ranked` varchar NOT NULL," +
                "`perms` varchar NOT NULL," +
                "`prefix` varchar NOT NULL," +
                "`suffix` varchar NOT NULL," +
                "`color` varchar NOT NULL" +
                ");");

        //server data table
        update("CREATE TABLE IF NOT EXISTS server (" +
                "`server` varchar PRIMARY KEY," +
                "`spawn` varchar NOT NULL" +
                ");");

    }

    //server data table


    public void setSpawn(String location) {
        try {
            ResultSet rs = getResult("SELECT * FROM server WHERE server = 'server';");
            if (!rs.next()) update("UPDATE server SET spawn ='" + location + "' WHERE server = server';");
            else update("INSERT INTO server (server, spawn) VALUES( 'server','" + location + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //player data table

    public void setPlayerData(UUID player, int balance, String ranked, String perms, String prefix, String suffix, String color) {
        update("INSERT INTO player_data (player, balance, ranked, perms, prefix, suffix, color) VALUES( '" + player + "','" + balance + "','" + ranked + "','" + perms + "','" + prefix + "','" + suffix + "','" + color + "');");
    }

    //eco

    public void deposit(UUID player, int value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + player + "';");
    }

    public void withdraw(UUID player, int value) {
        if (value < 0) value = 0;
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + player + "';");
    }

    public void setBalance(UUID player, int value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + player + "';");
    }

    public List<Integer> getBalanceTopValues() {
        try {
            ResultSet rs = getResult("SELECT * FROM player_data ORDER BY balance DESC LIMIT 15;");
            List<Integer> balances = new ArrayList<>();

            while (rs.next()) {
                int balance = rs.getInt("balance");
                balances.add(balance);
            }
            return balances;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UUID> getBalanceTopPlayers() {

        try {
            ResultSet rs = getResult("SELECT * FROM player_data ORDER BY balance DESC LIMIT 15;");

            List<UUID> players = new ArrayList<>();

            while (rs.next()) {
                String player = rs.getString("player");
                players.add(UUID.fromString(player));
            }
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBalanceTopRank(UUID player) {
        try {
            ResultSet rs = getResult(" WITH player_rank as (SELECT player, balance, RANK() OVER (ORDER BY balance DESC) AS rank FROM player_data) SELECT * FROM player_rank WHERE player = '" + player + "';");
            return rs.getInt("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setPrefix(UUID player, String prefix) {
        update("UPDATE player_data SET prefix ='" + prefix + "' WHERE player ='" + player + "';");
    }

    public void setSuffix(UUID player, String suffix) {
        update("UPDATE player_data SET suffix ='" + suffix + "' WHERE player ='" + player + "';");
    }

    public void setColor(UUID player, String color) {
        update("UPDATE player_data SET color ='" + color + "' WHERE player ='" + player + "';");
    }

    public void setRank(UUID player, String rank) {
        update("UPDATE player_data SET ranked ='" + rank + "' WHERE player ='" + player + "';");
    }

    public void addPerm(UUID player, String perms) {
        update("UPDATE player_data SET perms ='" + perms + "' WHERE player ='" + player + "';");
    }

    public void removePerm(UUID player, String perms) {
        update("UPDATE player_data SET perms ='" + perms + "' WHERE player ='" + player + "';");
    }

    public void loadPlayerData() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        try {
            ResultSet rs = getResult("SELECT * FROM player_data;");

            int count = 0;
            while(rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("player"));
                int balance = rs.getInt("balance");
                String ranked = rs.getString("ranked");
                String perms = rs.getString("perms");
                String prefix = rs.getString("prefix");
                String suffix = rs.getString("suffix");
                String color = rs.getString("color");
                cache.setPlayerData(uuid, balance, ranked, perms, prefix, suffix, color, false);
                count++;
            }
            System.out.println(plugin.getPU().format("&bPlayers Loaded: &3" + count));
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
                cache.setServerData(spawn);
                if (spawn != null) System.out.println(plugin.getPU().format("&bSpawn loaded!"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
