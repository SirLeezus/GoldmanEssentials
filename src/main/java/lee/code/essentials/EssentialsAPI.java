package lee.code.essentials;

import lee.code.essentials.database.Cache;

import java.util.UUID;

public class EssentialsAPI {

    public void deposit(UUID uuid, int amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        cache.deposit(uuid, amount);
    }

    public void withdraw(UUID uuid, int amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        cache.withdraw(uuid, amount);
    }

    public int getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        return cache.getBalance(uuid);
    }
}
