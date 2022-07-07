package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
public enum BoosterDropBlock {

    COAL_ORE(Material.COAL_ORE),
    DEEPSLATE_COAL_ORE(Material.DEEPSLATE_COAL_ORE),
    IRON_ORE(Material.IRON_ORE),
    DEEPSLATE_IRON_ORE(Material.DEEPSLATE_IRON_ORE),
    COPPER_ORE(Material.COPPER_ORE),
    DEEPSLATE_COPPER_ORE(Material.DEEPSLATE_COPPER_ORE),
    GOLD_ORE(Material.GOLD_ORE),
    DEEPSLATE_GOLD_ORE(Material.DEEPSLATE_GOLD_ORE),
    NETHER_GOLD_ORE(Material.NETHER_GOLD_ORE),
    DIAMOND_ORE(Material.DIAMOND_ORE),
    DEEPSLATE_DIAMOND_ORE(Material.DEEPSLATE_DIAMOND_ORE),
    REDSTONE_ORE(Material.REDSTONE_ORE),
    DEEPSLATE_REDSTONE_ORE(Material.DEEPSLATE_REDSTONE_ORE),
    EMERALD_ORE(Material.EMERALD_ORE),
    DEEPSLATE_EMERALD_ORE(Material.DEEPSLATE_EMERALD_ORE),
    LAPIS_ORE(Material.LAPIS_ORE),
    DEEPSLATE_LAPIS_ORE(Material.DEEPSLATE_LAPIS_ORE),
    GLOWSTONE(Material.GLOWSTONE),
    NETHER_QUARTZ_ORE(Material.NETHER_QUARTZ_ORE),
    AMETHYST_CLUSTER(Material.AMETHYST_CLUSTER),
    CLAY(Material.CLAY)

    ;

    @Getter private final Material block;
}

