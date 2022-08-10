package lee.code.essentials.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum MenuItem {

    BOT_CHECKER(Material.LIME_STAINED_GLASS_PANE, Lang.MENU_BOT_CHECKER_ITEM_NAME.getString(), Lang.MENU_BOT_CHECKER_ITEM_LORE.getString()),

    FILLER_GLASS(Material.BLACK_STAINED_GLASS_PANE, "&r", null),
    TRADE_COUNT_DOWN(Material.LIME_STAINED_GLASS_PANE, "&r", null),
    NEXT_PAGE(Material.PAPER, "&eNext Page >", null),
    PREVIOUS_PAGE(Material.PAPER, "&e< Previous Page", null),

    ARMOR_STAND_SETTING_TRUE(Material.LIME_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_SETTING_FALSE(Material.RED_STAINED_GLASS_PANE, "", null),
    ARMOR_STAND_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_HEAD_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_TORSO_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_LEFT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_RIGHT_ARM_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_LEFT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_RIGHT_LEG_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString()),
    ARMOR_STAND_DIRECTION_POSITION(Material.COMPASS, "", Lang.MENU_ARMOR_STAND_DIRECTION_POSITION_LORE.getString()),

    RESOURCE_WORLD(Material.GRASS_BLOCK, "&a&lResource World", null),
    RESOURCE_END(Material.END_STONE, "&5&lResource End", null),
    RESOURCE_NETHER(Material.NETHERRACK, "&c&lResource Nether", null),

    HOME_BED(Material.RED_BED, "&c&lBed", null),
    HOME_CLOCK(Material.CLOCK, Lang.MENU_HOME_CLOCK_NAME.getString(), null),

    WELCOME_1(Material.LADDER, "&6&lServer Ranks", "&aYou can earn ranks by unlocking advancements!\n&aIf you press &e\"L\" &aon your keyboard you can bring\n&aup a menu of your advancements. To check your rank\n&aprogress run the command &e/rankup&a. Everytime you\n&arank up you'll also earn money and EXP!"),
    WELCOME_2(Material.DIAMOND_SWORD, "&c&lServer PvP", "&aPvP has been changed to a 1.8 state where swing\n&adelays don't exist. You can PvP anyone, anywhere\n&ausing the command &e/duel&a! If you don't want to lose\n&ayour items when you die, battle in claimed chunks.\n&aThe spawn is also a great place to duel."),
    WELCOME_3(Material.GRASS_BLOCK, "&2&lChunk Claiming", "&aYou can claim chunks in any world besides resource\n&aworlds. To claim the chunk you're standing on run\n&athe command &e/chunk claim &aor run the command\n&a&e/chunk autoclaim&a to claim chunks as you walk. You\n&acan also unclaim chunks by running the command\n&a&e/chunk unclaim&a. To manage chunk flags stand on\n&athe chunk you want to edit and run the command\n&e/chunk manage&a. This is helpful for when you want\n&ato toggle monsters in a chunk, change global flags,\n&aor chunk trusted user flags. To trust someone to a\n&achunk stand on it and run the command &e/chunk trust&a.\n&aTo trust them to all your chunks run the command\n&a&e/chunk trustall&a."),
    WELCOME_4(Material.DIAMOND_ORE, "&3&lResource Worlds", "&aThe server has 3 resource worlds which reset every\n&a24 hours automatically. These can be accessed by\n&ausing the command &e/resourceworlds, &abut keep in mind\n&ayou'll need to discover the Nether and End before\n&ayou can access those resource worlds."),
    WELCOME_5(Material.GOLD_BLOCK, "&6&lServer Economy", "&aThere are many ways you can earn money on this server.\n&aThe most common way is using the command &e/sell &ato sell\n&athe item in your hand. You can also use the command\n&e/sellhand &ato sell all the items in your inventory that\n&amatch your hand item. For more advanced players there\n&ais player shops where you'll be able to build your own\n&ashop and sell items to other players at your own set\n&aprices! To view a list of all the player shops run the\n&acommand &e/shops&a. You can also earn money by voting; you\n&acan find the link by running the command &e/vote&a. You can\n&aalso earn money by ranking up!"),
    WELCOME_6(Material.MILK_BUCKET, "&5&lPets", "&aYou can open the pet menu by using the command &e/pets&a!\n&aThese are cosmetic baby mobs that'll follow you around.\n&aThey can be unlocked by purchasing a premium rank or\n&apurchasing them directly on the online store. Use the\n&acommand &e/help store &ato get the link."),
    WELCOME_7(Material.TOTEM_OF_UNDYING, "&d&lTrails", "&aYou can open the trails menu by using the command &e/trails&a!\n&aThese are cosmetic particles that'll follow you around\n&awith a selected style. They can be unlocked by\n&apurchasing a premium rank or purchasing them directly\n&aon the online store. Use the command &e/help store &ato get\n&athe link."),
    WELCOME_8(Material.WHEAT, "&5&lDaily Shop", "&aEvery 24 hours the daily shop will restock with new items\n&ayou can buy. This menu can be accessed by running the\n&acommand &e/shop daily&a. To use it click the item you\n&awant to buy and select the amount."),
    WELCOME_9(Material.SPAWNER, "&e&lSpawner Shop", "&aIn this server you can pickup spawners by using the\n&aenchant &bSilk Touch&a. You can also buy spawners by using\n&athe command &e/shop spawners&a. These will spawn mobs every\n&afew seconds. Great for making farms to earn more cash!"),
    WELCOME_10(Material.IRON_BARS, "&c&lSecurity", "&aThe server has many checks to stop hackers without\n&achanging the normal player experience. We also encrypt all\n&aplayer connections so your IP can never be leaked along\n&awith multiple traffic routes to give you the best connection\n&apossible. The server is also protected from DDoS attacks\n&awith over 37tbps in bandwidth."),
    WELCOME_11(Material.ENCHANTED_BOOK, "&d&lLegendary Enchantments", "&aThere are legendary enchants on this server which can\n&abe obtained by enchanting an item at level 30 on an\n&aenchanting table. To view a list of all the legendary\n&aenchants run the command &e/help enchants&a. This server\n&aalso allows you to transfer enchants from armor and\n&aitems to enchanted books through anvils."),
    WELCOME_12(Material.CHEST, "&c&lLock Blocks", "&aTo learn about locking blocks with a sign use the command\n&a&e/help locker&a. This is helpful for when you have trusted\n&ausers in your claims that you don't want opening your stuff. \n&aOtherwise, claiming the land is enough to protect it."),


    TRADE_CONFIRM_TRUE(Material.LIME_STAINED_GLASS_PANE, "&6&lTrade Confirmed&7: &aTrue", null),
    TRADE_CONFIRM_FALSE(Material.RED_STAINED_GLASS_PANE, "&6&lTrade Confirmed&7: &cFalse", null),

    ;

    @Getter private final Material type;
    @Getter private final String name;
    @Getter private final String lore;

    public ItemStack getItem() {
        return BukkitUtils.getItem(type, name, lore, null, true);
    }
}
