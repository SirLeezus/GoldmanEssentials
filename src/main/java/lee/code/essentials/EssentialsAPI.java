package lee.code.essentials;

import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.ItemSellValue;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EssentialsAPI {

    public void deposit(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        cacheManager.deposit(uuid, amount);
    }

    public void withdraw(UUID uuid, long amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        cacheManager.withdraw(uuid, amount);
    }

    public long getBalance(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        return cacheManager.getBalance(uuid);
    }

    public long getWorth(ItemStack item) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        ItemStack checkItem = new ItemStack(item.getType());
        if (data.getSupportedSellItems().contains(checkItem)) return ItemSellValue.valueOf(checkItem.getType().name()).getValue();
        else return 0;
    }

    public boolean isBoosterActive() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        return cacheManager.isBoosterActive();
    }

    public int getBoosterMultiplier() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        return cacheManager.getBoosterMultiplier(cacheManager.getActiveBoosterID());
    }

    public boolean isDuelingPlayer(UUID uuid, UUID target) {
        return GoldmanEssentials.getPlugin().getData().isDuelingPlayer(uuid, target);
    }

    public ItemStack getEntityHead(Entity entity, int rng) {
        return GoldmanEssentials.getPlugin().getPU().getEntityHead(entity, rng);
    }
}
