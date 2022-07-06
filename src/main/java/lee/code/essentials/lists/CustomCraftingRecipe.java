package lee.code.essentials.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
public enum CustomCraftingRecipe {
    // A B C D E F G
    //1.17 missing recipes
    BUNDLE(new ItemStack(Material.BUNDLE), "ABA,B B,BBB", new Material[] {Material.STRING,  Material.RABBIT_HIDE}, false, 0, null),
    SCULK_SENSOR(new ItemStack(Material.SCULK_SENSOR), "A A,BCB,DBD", new Material[] {Material.GLOW_LICHEN,  Material.DEEPSLATE, Material.REDSTONE, Material.CRYING_OBSIDIAN}, false, 0, null),
    GLOW_BERRIES(new ItemStack(Material.GLOW_BERRIES), "ABA,BCB,ABA", new Material[] {Material.GLOWSTONE_DUST,  Material.GLOW_LICHEN, Material.SWEET_BERRIES}, false, 0, null),
    ENCHANTED_GOLDEN_APPLE(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), "AAA,ABA,AAA", new Material[] {Material.GOLD_BLOCK,  Material.APPLE}, false, 0, null),
    MOSS_BLOCK(new ItemStack(Material.MOSS_BLOCK), "AAA,AAA,AAA", new Material[] { Material.GRASS }, false, 0, null),
    NAME_TAG(new ItemStack(Material.NAME_TAG), null, new Material[] { Material.STRING, Material.PAPER }, true, 0, null),
    SADDLE(new ItemStack(Material.SADDLE), "AAA,BBB,B B", new Material[] { Material.LEAD, Material.LEATHER }, false, 0, null),
    IRON_HORSE_ARMOR(new ItemStack(Material.IRON_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.IRON_INGOT }, false, 0, null),
    GOLDEN_HORSE_ARMOR(new ItemStack(Material.GOLDEN_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.GOLD_INGOT }, false, 0, null),
    DIAMOND_HORSE_ARMOR(new ItemStack(Material.DIAMOND_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.DIAMOND }, false, 0, null),
    WRENCH(new ItemStack(Material.GUNPOWDER), "A A, A , A ", new Material[] { Material.IRON_INGOT }, false, 3000, "&fWrench"),
    TRIDENT(new ItemStack(Material.TRIDENT), "ABA,DCD,DCD", new Material[] { Material.IRON_INGOT, Material.NETHER_STAR, Material.STICK, Material.PRISMARINE_SHARD }, false, 0, null),
    TOTEM_OF_UNDYING(new ItemStack(Material.TOTEM_OF_UNDYING), "AAA,BAB,CAC", new Material[] { Material.GOLD_INGOT, Material.EMERALD, Material.EXPERIENCE_BOTTLE }, false, 0, null),
    ;

    @Getter private final ItemStack item;
    @Getter private final String craftingRecipe;
    @Getter private final Material[] recipeMaterial;
    @Getter private final boolean shapeless;
    @Getter private final int id;
    @Getter private final String customName;

    public void registerRecipe() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        NamespacedKey key = new NamespacedKey(plugin, item.getType().name());

        ItemMeta itemMeta = item.getItemMeta();
        if (id != 0) itemMeta.setCustomModelData(id);
        if (customName != null) itemMeta.displayName(BukkitUtils.parseColorComponent(customName));
        item.setItemMeta(itemMeta);

        if (shapeless) {
            ShapelessRecipe recipe = new ShapelessRecipe(key, item);
            for (Material mat : recipeMaterial) recipe.addIngredient(mat);
            plugin.getServer().addRecipe(recipe);
        } else {
            ShapedRecipe recipe = new ShapedRecipe(key, item);
            String[] shapeSplit = StringUtils.split(craftingRecipe, ',');
            recipe.shape(shapeSplit[0], shapeSplit[1], shapeSplit[2]);
            char c;
            int count = 0;
            int max = recipeMaterial.length;
            for (c = 'A'; c <= 'G'; ++c) {
                recipe.setIngredient(c, recipeMaterial[count]);
                count++;
                if (count >= max) break;
            }
            plugin.getServer().addRecipe(recipe);
        }
    }
}
