package lee.code.essentials.database;

import lee.code.essentials.TheEssentials;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SQLite {

    private Connection connection;
    private Statement statement;

    public void connect() {
        TheEssentials plugin = TheEssentials.getPlugin();
        connection = null;

        try {
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
            statement.execute(sql);
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
        //chunk table
        update("CREATE TABLE IF NOT EXISTS player_data (" +
                "`player` varchar PRIMARY KEY," +
                "`balance` int NOT NULL" +
                ");");
    }

    public void createPlayerProfile(UUID player, int value) {
        update("INSERT INTO player_data (player, balance) VALUES( '" + player + "','" + value + "');");
    }

    @SneakyThrows
    public boolean hasPlayerProfile(UUID player) {
        ResultSet rs = getResult("SELECT player FROM player_data WHERE player = '" + player + "';");
        return rs.next();
    }

    @SneakyThrows
    public int getBalance(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        return rs.getInt("balance");
    }

    @SneakyThrows
    public void deposit(UUID player, int value) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        int balance = rs.getInt("balance");
        balance = balance + value;

        update("UPDATE player_data SET balance ='" + balance + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public void withdraw(UUID player, int value) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        int balance = rs.getInt("balance");
        balance = balance - value;
        if (balance < 0) balance = 0;
        update("UPDATE player_data SET balance ='" + balance + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public List<Integer> getBalanceTopValues() {
        ResultSet rs = getResult("SELECT * FROM player_data ORDER BY balance DESC LIMIT 15;");

        List<Integer> balances = new ArrayList<>();

        while (rs.next()) {
            int balance = rs.getInt("balance");
            balances.add(balance);
        }

        return balances;
    }

    @SneakyThrows
    public List<UUID> getBalanceTopPlayers() {
        ResultSet rs = getResult("SELECT * FROM player_data ORDER BY balance DESC LIMIT 15;");

        List<UUID> players = new ArrayList<>();

        while (rs.next()) {
            String player = rs.getString("player");
            players.add(UUID.fromString(player));
        }

        return players;
    }

    @SneakyThrows
    public int getBalanceTopRank(UUID player) {
        ResultSet rs = getResult(" WITH player_rank as (SELECT player, balance, RANK() OVER (ORDER BY balance DESC) AS rank FROM player_data) SELECT * FROM player_rank WHERE player = '" + player + "';");
        return rs.getInt("rank");
    }
}
