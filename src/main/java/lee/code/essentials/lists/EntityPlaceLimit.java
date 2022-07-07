package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

@AllArgsConstructor
public enum EntityPlaceLimit {
    MINECART(Material.MINECART, EntityType.MINECART),
    CHEST_MINECART(Material.CHEST_MINECART, EntityType.MINECART_CHEST),
    FURNACE_MINECART(Material.FURNACE_MINECART, EntityType.MINECART_FURNACE),
    TNT_MINECART(Material.TNT_MINECART, EntityType.MINECART_TNT),
    HOPPER_MINECART(Material.HOPPER_MINECART, EntityType.MINECART_HOPPER),
    COMMAND_BLOCK_MINECART(Material.COMMAND_BLOCK_MINECART, EntityType.MINECART_COMMAND),
    OAK_BOAT(Material.OAK_BOAT, EntityType.BOAT),
    BIRCH_BOAT(Material.BIRCH_BOAT, EntityType.BOAT),
    SPRUCE_BOAT(Material.SPRUCE_BOAT, EntityType.BOAT),
    ACACIA_BOAT(Material.ACACIA_BOAT, EntityType.BOAT),
    DARK_OAK_BOAT(Material.DARK_OAK_BOAT, EntityType.BOAT),
    MANGROVE_BOAT(Material.MANGROVE_BOAT, EntityType.BOAT),
    JUNGLE_BOAT(Material.JUNGLE_BOAT, EntityType.BOAT),
    OAK_CHEST_BOAT(Material.OAK_CHEST_BOAT, EntityType.CHEST_BOAT),
    BIRCH_CHEST_BOAT(Material.BIRCH_CHEST_BOAT, EntityType.CHEST_BOAT),
    SPRUCE_CHEST_BOAT(Material.SPRUCE_CHEST_BOAT, EntityType.CHEST_BOAT),
    ACACIA_CHEST_BOAT(Material.ACACIA_CHEST_BOAT, EntityType.CHEST_BOAT),
    DARK_OAK_CHEST_BOAT(Material.DARK_OAK_CHEST_BOAT, EntityType.CHEST_BOAT),
    MANGROVE_CHEST_BOAT(Material.MANGROVE_CHEST_BOAT, EntityType.CHEST_BOAT),
    JUNGLE_CHEST_BOAT(Material.JUNGLE_CHEST_BOAT, EntityType.CHEST_BOAT),
    ITEM_FRAME(Material.ITEM_FRAME, EntityType.ITEM_FRAME),
    GLOW_ITEM_FRAME(Material.GLOW_ITEM_FRAME, EntityType.GLOW_ITEM_FRAME),
    ARMOR_STAND(Material.ARMOR_STAND, EntityType.ARMOR_STAND),
    ;

    @Getter private final Material material;
    @Getter private final EntityType entityType;
}
