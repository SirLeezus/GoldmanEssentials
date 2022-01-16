package lee.code.essentials;

import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.ItemSellValues;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EssentialsAPI {

    public void deposit(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        cache.deposit(uuid, amount);
    }

    public void withdraw(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        cache.withdraw(uuid, amount);
    }

    public long getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        return cache.getBalance(uuid);
    }

    public long getWorth(ItemStack item) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        ItemStack checkItem = new ItemStack(item.getType());
        if (data.getSupportedSellItems().contains(checkItem)) return ItemSellValues.valueOf(checkItem.getType().name()).getValue();
        else return 0;
    }

    public boolean isBoosterActive() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        return cache.isBoosterActive();
    }

    public int getBoosterMultiplier() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        return Integer.parseInt(cache.getBoosterMultiplier(cache.getActiveBoosterID()));
    }
}
