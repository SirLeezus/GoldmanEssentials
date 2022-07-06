package lee.code.essentials.database;

import lee.code.core.ormlite.dao.Dao;
import lee.code.core.ormlite.dao.DaoManager;
import lee.code.core.ormlite.jdbc.JdbcConnectionSource;
import lee.code.core.ormlite.jdbc.db.DatabaseTypeUtils;
import lee.code.core.ormlite.logger.LogBackendType;
import lee.code.core.ormlite.logger.LoggerFactory;
import lee.code.core.ormlite.support.ConnectionSource;
import lee.code.core.ormlite.table.TableUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.tables.BoosterTable;
import lee.code.essentials.database.tables.PlayerTable;
import lee.code.essentials.database.tables.PunishmentTable;
import lee.code.essentials.database.tables.ServerTable;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {

    private Dao<PlayerTable, UUID> playerDao;
    private Dao<ServerTable, UUID> serverDao;
    private Dao<PunishmentTable, UUID> punishmentDao;
    private Dao<BoosterTable, Integer> boosterDao;

    @Getter(AccessLevel.NONE)
    private ConnectionSource connectionSource;

    private String getDatabaseURL() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        return "jdbc:sqlite:" + new File(plugin.getDataFolder(), "database.db");
    }

    public void initialize() {
        //LoggerFactory.setLogBackendFactory(LogBackendType.NULL);

        try {
            String databaseURL = getDatabaseURL();
            connectionSource = new JdbcConnectionSource(
                    databaseURL,
                    "test",
                    "test",
                    DatabaseTypeUtils.createDatabaseType(databaseURL));
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        CacheManager cacheManager = GoldmanEssentials.getPlugin().getCacheManager();

        //player data
        TableUtils.createTableIfNotExists(connectionSource, PlayerTable.class);
        playerDao = DaoManager.createDao(connectionSource, PlayerTable.class);
        //load player data into cache
        for (PlayerTable playerTable : playerDao.queryForAll()) cacheManager.setPlayerData(playerTable);

        //server data
        TableUtils.createTableIfNotExists(connectionSource, ServerTable.class);
        serverDao = DaoManager.createDao(connectionSource, ServerTable.class);
        //load server data into cache
        for (ServerTable serverTable : serverDao.queryForAll()) cacheManager.setServerData(serverTable);

        //punishment data
        TableUtils.createTableIfNotExists(connectionSource, PunishmentTable.class);
        punishmentDao = DaoManager.createDao(connectionSource, PunishmentTable.class);
        //load punishment data into cache
        for (PunishmentTable punishmentTable : punishmentDao.queryForAll()) cacheManager.setPunishmentData(punishmentTable);

        //booster data
        TableUtils.createTableIfNotExists(connectionSource, BoosterTable.class);
        boosterDao = DaoManager.createDao(connectionSource, BoosterTable.class);
        //booster punishment data into cache
        for (BoosterTable boosterTable : boosterDao.queryForAll()) cacheManager.setBoosterData(boosterTable);

    }

    public void closeConnection() {
        try {
            connectionSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void createPlayerTable(PlayerTable playerTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                playerDao.create(playerTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void updatePlayerTable(PlayerTable playerTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                playerDao.update(playerTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void createServerTable(ServerTable serverTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                serverDao.create(serverTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void updateServerTable(ServerTable serverTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                serverDao.update(serverTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void createPunishmentTable(PunishmentTable punishmentTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                punishmentDao.create(punishmentTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void updatePunishmentTable(PunishmentTable punishmentTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                punishmentDao.update(punishmentTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void createBoosterTable(BoosterTable boosterTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                boosterDao.create(boosterTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void updateBoosterTable(BoosterTable boosterTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                boosterDao.update(boosterTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void deleteBoosterTable(BoosterTable boosterTable) {
        Bukkit.getScheduler().runTaskAsynchronously(GoldmanEssentials.getPlugin(), () -> {
            try {
                boosterDao.delete(boosterTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
