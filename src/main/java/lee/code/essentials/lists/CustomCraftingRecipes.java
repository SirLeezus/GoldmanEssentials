package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@AllArgsConstructor
public enum CustomCraftingRecipes {
    // A B C D E F G
    //1.17 missing recipes
    BUNDLE(new ItemStack(Material.BUNDLE), "ABA,B B,BBB", new Material[] {Material.STRING,  Material.RABBIT_HIDE}, false),
    SCULK_SENSOR(new ItemStack(Material.SCULK_SENSOR), "A A,BCB,DBD", new Material[] {Material.GLOW_LICHEN,  Material.DEEPSLATE, Material.REDSTONE, Material.CRYING_OBSIDIAN}, false),
    GLOW_BERRIES(new ItemStack(Material.GLOW_BERRIES), "ABA,BCB,ABA", new Material[] {Material.GLOWSTONE_DUST,  Material.GLOW_LICHEN, Material.SWEET_BERRIES}, false),
    ENCHANTED_GOLDEN_APPLE(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), "AAA,ABA,AAA", new Material[] {Material.GOLD_BLOCK,  Material.APPLE}, false),
    MOSS_BLOCK(new ItemStack(Material.MOSS_BLOCK), "AAA,AAA,AAA", new Material[] { Material.GRASS }, false),
    NAME_TAG(new ItemStack(Material.NAME_TAG), null, new Material[] { Material.STRING, Material.PAPER }, true),
    SADDLE(new ItemStack(Material.SADDLE), "AAA,BBB,B B", new Material[] { Material.LEAD, Material.LEATHER }, false),
    IRON_HORSE_ARMOR(new ItemStack(Material.IRON_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.IRON_INGOT }, false),
    GOLDEN_HORSE_ARMOR(new ItemStack(Material.GOLDEN_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.GOLD_INGOT }, false),
    DIAMOND_HORSE_ARMOR(new ItemStack(Material.DIAMOND_HORSE_ARMOR), "A A,AAA,A A", new Material[] { Material.DIAMOND }, false),
    ;

    @Getter private final ItemStack item;
    @Getter private final String craftingRecipe;
    @Getter private final Material[] recipeMaterial;
    @Getter private final boolean shapeless;

    public void registerRecipe() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        NamespacedKey key = new NamespacedKey(plugin, item.getType().name());
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
