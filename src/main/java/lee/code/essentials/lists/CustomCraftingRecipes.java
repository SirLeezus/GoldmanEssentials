package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

@AllArgsConstructor
public enum CustomCraftingRecipes {
    // A B C D E F G
    //1.17 missing recipes
    BUNDLE(new ItemStack(Material.BUNDLE), "ABA,B B,BBB", new Material[] {Material.STRING,  Material.RABBIT_HIDE}),
    SCULK_SENSOR(new ItemStack(Material.SCULK_SENSOR), "A A,BCB,DBD", new Material[] {Material.GLOW_LICHEN,  Material.DEEPSLATE, Material.REDSTONE, Material.CRYING_OBSIDIAN}),
    GLOW_BERRIES(new ItemStack(Material.GLOW_BERRIES), "ABA,BCB,ABA", new Material[] {Material.GLOWSTONE_DUST,  Material.GLOW_LICHEN, Material.SWEET_BERRIES}),
    ENCHANTED_GOLDEN_APPLE(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), "AAA,ABA,AAA", new Material[] {Material.GOLD_BLOCK,  Material.APPLE}),
    MOSS_BLOCK(new ItemStack(Material.MOSS_BLOCK), "AAA,AAA,AAA", new Material[] {Material.GRASS}),
    ;

    @Getter private final ItemStack item;
    @Getter private final String shape;
    @Getter private final Material[] recipeMaterial;

    public void registerRecipe() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        NamespacedKey key = new NamespacedKey(plugin, item.getType().name());
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        String[] shapeSplit = StringUtils.split(shape, ',');
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
