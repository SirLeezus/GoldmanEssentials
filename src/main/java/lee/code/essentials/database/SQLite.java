package lee.code.essentials.database;

import lee.code.essentials.GoldmanEssentials;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;

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
                "`balance` int NOT NULL," +
                "`ranked` varchar NOT NULL," +
                "`perms` varchar NOT NULL," +
                "`prefix` varchar NOT NULL," +
                "`suffix` varchar NOT NULL," +
                "`color` varchar NOT NULL" +
                ");");
    }

    //table

    public void createPlayerProfile(UUID player, int balance, String ranked, String perms, String prefix, String suffix, String color) {
        update("INSERT INTO player_data (player, balance, ranked, perms, prefix, suffix, color) VALUES( '" + player + "','" + balance + "','" + ranked + "','" + perms + "','" + prefix + "','" + suffix + "','" + color + "');");
    }

    @SneakyThrows
    public boolean hasPlayerProfile(UUID player) {
        ResultSet rs = getResult("SELECT player FROM player_data WHERE player = '" + player + "';");
        return rs.next();
    }

    //eco

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

    public void setBalance(UUID player, int value) {
        update("UPDATE player_data SET balance ='" + value + "' WHERE player ='" + player + "';");
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

    @SneakyThrows
    public String getPrefix(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        String prefix = rs.getString("prefix");
        if (!prefix.equals("n")) return prefix;
        else return "";
    }

    public void setPrefix(UUID player, String prefix) {
        update("UPDATE player_data SET prefix ='" + prefix + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public String getSuffix(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        String suffix = rs.getString("suffix");
        if (!suffix.equals("n")) return suffix;
        else return "";
    }

    public void setSuffix(UUID player, String suffix) {
        update("UPDATE player_data SET suffix ='" + suffix + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public String getColor(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        return rs.getString("color");
    }

    public void setColor(UUID player, String color) {
        update("UPDATE player_data SET color ='" + color + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public String getRank(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        return rs.getString("ranked");
    }

    public void setRank(UUID player, String rank) {
        update("UPDATE player_data SET ranked ='" + rank + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public List<String> getPerms(UUID player) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");
        String perms = rs.getString("perms");
        String[] split = StringUtils.split(perms, ',');
        return new ArrayList<>(Arrays.asList(split));
    }

    @SneakyThrows
    public void addPerm(UUID player, String perm) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");

        if (!rs.getString("perms").equals("n")) {
            String perms = rs.getString("perms");
            String[] split = StringUtils.split(perms, ',');
            List<String> playerPerms = new ArrayList<>(Arrays.asList(split));
            if (!playerPerms.contains(perm)) {
                playerPerms.add(perm);
                String updatedPerms = StringUtils.join(playerPerms, ",");
                update("UPDATE player_data SET perms ='" + updatedPerms + "' WHERE player ='" + player + "';");
            }

        } else update("UPDATE player_data SET perms ='" + perm + "' WHERE player ='" + player + "';");
    }

    @SneakyThrows
    public void removePerm(UUID player, String perm) {
        ResultSet rs = getResult("SELECT * FROM player_data WHERE player = '" + player + "';");

        String perms = rs.getString("perms");

        if (!perms.equals("n")) {

            String[] split = StringUtils.split(perms, ',');
            List<String> permList = new ArrayList<>();
            for (String permission : split) if (!permission.equals(perm)) permList.add(permission);
            String playerPerms = StringUtils.join(permList, ",");

            update("UPDATE player_data SET perms ='" + playerPerms + "' WHERE player ='" + player + "';");
        }
    }

}
