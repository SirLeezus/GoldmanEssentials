package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ItemSellValues {

    REDSTONE(new ItemStack(Material.REDSTONE), 2),
    REDSTONE_BLOCK(new ItemStack(Material.REDSTONE_BLOCK), 18),
    LAPIS_LAZULI(new ItemStack(Material.LAPIS_LAZULI), 2),
    LAPIS_BLOCK(new ItemStack(Material.LAPIS_BLOCK), 18),
    CARROT(new ItemStack(Material.CARROT), 3),
    GOLDEN_CARROT(new ItemStack(Material.GOLDEN_CARROT), 32),
    BEETROOT(new ItemStack(Material.BEETROOT), 3),
    SWEET_BERRIES(new ItemStack(Material.SWEET_BERRIES), 3),
    GLOW_BERRIES(new ItemStack(Material.GLOW_BERRIES), 3),
    APPLE(new ItemStack(Material.APPLE), 5),
    GOLDEN_APPLE(new ItemStack(Material.GOLDEN_APPLE), 250),
    MELON(new ItemStack(Material.MELON), 5),
    POTATO(new ItemStack(Material.POTATO), 3),
    PUMPKIN(new ItemStack(Material.PUMPKIN), 5),
    HONEYCOMB(new ItemStack(Material.HONEYCOMB), 5),
    HONEYCOMB_BLOCK(new ItemStack(Material.HONEYCOMB_BLOCK), 20),
    NETHER_WART(new ItemStack(Material.NETHER_WART), 6),
    COAL(new ItemStack(Material.COAL), 5),
    CHARCOAL(new ItemStack(Material.CHARCOAL), 5),
    COAL_BLOCK(new ItemStack(Material.COAL_BLOCK), 45),
    QUARTZ(new ItemStack(Material.QUARTZ), 10),
    QUARTZ_BLOCK(new ItemStack(Material.QUARTZ_BLOCK), 40),
    COOKED_BEEF(new ItemStack(Material.COOKED_BEEF), 10),
    COOKED_COD(new ItemStack(Material.COOKED_COD), 10),
    COOKED_SALMON(new ItemStack(Material.COOKED_SALMON), 10),
    COOKED_CHICKEN(new ItemStack(Material.COOKED_CHICKEN), 10),
    COOKED_RABBIT(new ItemStack(Material.COOKED_RABBIT), 10),
    OBSIDIAN(new ItemStack(Material.OBSIDIAN), 15),
    CRYING_OBSIDIAN(new ItemStack(Material.CRYING_OBSIDIAN), 15),
    COPPER_INGOT(new ItemStack(Material.COPPER_INGOT), 15),
    COPPER_BLOCK(new ItemStack(Material.COPPER_BLOCK), 135),
    IRON_INGOT(new ItemStack(Material.IRON_INGOT), 20),
    IRON_BLOCK(new ItemStack(Material.IRON_BLOCK), 180),
    GOLD_INGOT(new ItemStack(Material.GOLD_INGOT), 30),
    GOLD_BLOCK(new ItemStack(Material.GOLD_BLOCK), 270),
    DIAMOND(new ItemStack(Material.DIAMOND), 50),
    DIAMOND_BLOCK(new ItemStack(Material.DIAMOND_BLOCK), 450),
    EMERALD(new ItemStack(Material.EMERALD), 60),
    EMERALD_BLOCK(new ItemStack(Material.EMERALD_BLOCK), 540),
    NETHERITE_INGOT(new ItemStack(Material.NETHERITE_INGOT), 70),
    NETHERITE_BLOCK(new ItemStack(Material.NETHERITE_BLOCK), 630),
    NETHER_STAR(new ItemStack(Material.NETHER_STAR), 80),
    ;

    @Getter private final ItemStack item;
    @Getter private final long value;
}
