package lee.code.essentials;

import lee.code.essentials.database.SQLite;

import java.util.UUID;

public class EssentialsAPI {

    public void deposit(UUID uuid, int amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        SQL.deposit(uuid, amount);
    }

    public void withdraw(UUID uuid, int amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        SQL.withdraw(uuid, amount);
    }

    public int getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        return SQL.getBalance(uuid);
    }
}
