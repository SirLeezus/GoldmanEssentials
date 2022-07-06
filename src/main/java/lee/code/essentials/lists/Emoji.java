package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
public enum Emoji {

//    MAD("\uE59B"),
//    HAPPY("\uE59C"),
    PIG("\uE855"),
    SHEEP("\uE857"),
    COW("\uE859"),
    MUSHROOM_COW("\uE85F"),
    CHICKEN("\uE85B"),
    SQUID("\uE85C"),
    WOLF("\uE85D"),
    ANGRY_WOLF("\uE85E"),
    SNOW_GOLEM("\uE861"),
    SNOW_GOLEM_HEAD("\uE862"),
    OCELOT("\uE863"),
    CAT("\uE864"),
    IRON_GOLEM("\uE867"),
    HORSE("\uE86A"),
    RABBIT("\uE870"),
    KILLER_RABBIT("\uE877"),
    ANGRY_VILLAGER("\uE791"),
    ELDER_GUARDIAN("\uE7EB"),
    WITHER_SKELETON("\uE7EC"),
    STRAY("\uE7EE"),
    HUSK("\uE7F5"),
    ZOMBIE_VILLAGER("\uE7FA"),
    SKELETON_HORSE("\uE806"),
    ZOMBIE_HORSE("\uE807"),
    DONKEY("\uE80A"),
    MULE("\uE80B"),
    EVOKER_FANGS("\uE80C"),
    EVOKER("\uE80D"),
    VINDICATOR("\uE80F"),
    VEX("\uE811"),
    ILLUSIONER("\uE815"),
    CREEPER("\uE824"),
    CHARGED_CREEPER("\uE826"),
    SKELETON("\uE828"),
    SPIDER("\uE82A"),
    ZOMBIE("\uE82C"),
    SLIME("\uE82E"),
    GHAST("\uE830"),
    GHAST_ANGRY("\uE831"),
    ENDERMAN("\uE834"),
    CAVE_SPIDER("\uE837"),
    SILVERFISH("\uE83A"),
    BLAZE("\uE83B"),
    MAGMA_CUBE("\uE83D"),
    ENDER_DRAGON("\uE840"),
    WITHER("\uE841"),
    BAT("\uE845"),
    WITCH("\uE846"),
    ENDERMITE("\uE848"),
    GUARDIAN("\uE849"),
    SHULKER("\uE84B"),
    DROWNED("\uE84D"),
    MOOSHROOM_COW("\uE85F"),
    LLAMA("\uE87C"),
    PARROT("\uE87F"),
    COD("\uE884"),
    SALMON("\uE886"),
    TURTLE("\uE889"),
    DOLPHIN("\uE88A"),
    VILLAGER("\uE88B"),
    RAW_COD("\uE5BD"),
    RAW_SALMON("\uE5BE"),
    TROPICAL_FISH("\uE5BF"),
    PUFFERFISH("\uE5C0"),
    ENDER_DRAGON_FIREBALL("\uE7F9"),
    FIREBALL("\uE7F2"),
    POLAR_BEAR("\uE878"),
    COAL("\uE503"),
    CHARCOAL("\uE504"),
    QUARTZ("\uE65C"),
    LAPIS_LAZULI("\uE5C7"),
    REDSTONE_DUST("\uE547"),
    IRON_INGOT("\uE506"),
    GOLD_INGOT("\uE507"),
    COPPER_INGOT("\uE8C1"),
    DIAMOND("\uE505"),
    EMERALD("\uE64C"),
    NETHERITE_INGOT("\uE8C0"),
    AMETHYST_CLUSTER("\uE8C2"),
    NETHER_STAR("\uE656"),
    END_CRYSTAL("\uE67F"),
    REDSTONE_BLOCK("\uE2BA"),
    COAL_BLOCK("\uE32B"),
    QUARTZ_BLOCK("\uE2C2"),
    IRON_BLOCK("\uE0DE"),
    GOLD_BLOCK("\uE0DD"),
    COPPER_BLOCK("\uE8C3"),
    DIAMOND_BLOCK("\uE125"),
    EMERALD_BLOCK("\uE263"),
    NETHERITE_BLOCK("\uE8C4"),
    STONE("\uE001"),
    GRANITE("\uE002"),
    POLISHED_GRANITE("\uE003"),
    DIORITE("\uE004"),
    POLISHED_DIORITE("\uE005"),
    ANDESITE("\uE006"),
    POLISHED_ANDESITE("\uE007"),
    GRASS_BLOCK("\uE00A"),
    DIRT("\uE00B"),
    COARSE_DIRT("\uE00C"),
    PODZOL("\uE00E"),
    COBBLESTONE("\uE00F"),
    OAK_PLANKS("\uE010"),
    SPRUCE_PLANKS("\uE011"),
    BIRCH_PLANKS("\uE012"),
    JUNGLE_PLANKS("\uE013"),
    ACACIA_PLANKS("\uE014"),
    DARK_OAK_PLANKS("\uE015"),
    CRIMSON_PLANKS("\uE8C5"),
    WARPED_PLANKS("\uE8C6"),
    MANGROVE_PLANKS("\uE8C7"),
    BEDROCK("\uE01C"),
    WATER("\uE01D"),
    LAVA("\uE01F"),
    SAND("\uE021"),
    RED_SAND("\uE022"),
    GRAVEL("\uE023"),
    NETHER_QUARTZ_ORE("\uE2BB"),
    LAPIS_LAZULI_ORE("\uE048"),
    COAL_ORE("\uE026"),
    REDSTONE_ORE("\uE150"),
    COPPER_ORE("\uE8C9"),
    GOLD_ORE("\uE024"),
    IRON_ORE("\uE025"),
    DIAMOND_ORE("\uE124"),
    EMERALD_ORE("\uE258"),
    OAK_LOG_SIDE("\uE027"),
    OAK_LOG_TOP("\uE028"),
    SPRUCE_LOG_SIDE("\uE029"),
    SPRUCE_LOG_TOP("\uE02A"),
    BIRCH_LOG_SIDE("\uE02B"),
    BIRCH_LOG_TOP("\uE02C"),
    JUNGLE_LOG_SIDE("\uE02D"),
    JUNGLE_LOG_TOP("\uE02E"),
    ACACIA_LOG_SIDE("\uE02F"),
    ACACIA_LOG_TOP("\uE030"),
    DARK_OAK_LOG_SIDE("\uE031"),
    DARK_OAK_LOG_TOP("\uE032"),
    MANGROVE_LOG_SIDE("\uE8CB"),
    MANGROVE_LOG_TOP("\uE8CA"),
    STRIPPED_OAK_LOG_SIDE("\uE033"),
    STRIPPED_OAK_LOG_TOP("\uE034"),
    STRIPPED_SPRUCE_LOG_SIDE("\uE035"),
    STRIPPED_SPRUCE_LOG_TOP("\uE036"),
    STRIPPED_BIRCH_LOG_SIDE("\uE037"),
    STRIPPED_BIRCH_LOG_TOP("\uE038"),
    STRIPPED_JUNGLE_LOG_SIDE("\uE039"),
    STRIPPED_JUNGLE_LOG_TOP("\uE03A"),
    STRIPPED_ACACIA_LOG_SIDE("\uE03B"),
    STRIPPED_ACACIA_LOG_TOP("\uE03C"),
    STRIPPED_DARK_OAK_LOG_SIDE("\uE03D"),
    STRIPPED_DARK_OAK_LOG_TOP("\uE03E"),
    STRIPPED_WARPED_STEM_SIDE("\uE8CC"),
    STRIPPED_WARPED_STEM_TOP("\uE8CD"),
    STRIPPED_CRIMSON_STEM_SIDE("\uE8CE"),
    STRIPPED_CRIMSON_STEM_TOP("\uE8CF"),
    STRIPPED_MANGROVE_STEM_SIDE("\uE8D0"),
    STRIPPED_MANGROVE_STEM_TOP("\uE8D1"),
    OAK_LEAVES("\uE03F"),
    SPRUCE_LEAVES("\uE040"),
    BIRCH_LEAVES("\uE041"),
    JUNGLE_LEAVES("\uE042"),
    ACACIA_LEAVES("\uE043"),
    DARK_OAK_LEAVES("\uE044"),
    SPONGE("\uE045"),
    WET_SPONGE("\uE046"),
    GLASS("\uE047"),
    DISPENSER("\uE04A"),
    SANDSTONE("\uE04E"),
    CHISELED_SANDSTONE("\uE051"),
    CUT_SANDSTONE("\uE052"),
    NOTE_BLOCK("\uE053"),
    RAIL("\uE13B"),
    POWERED_RAIL("\uE094"),
    DETECTOR_RAIL("\uE098"),
    PISTON("\uE0BE"),
    STICKY_PISTON("\uE0A8"),
    COBWEB("\uE0AB"),
    GRASS("\uE0AC"),
    FERN("\uE0AD"),
    DEAD_BUSH("\uE0AE"),
    SEAGRASS("\uE0AF"),
    WHITE_WOOL("\uE0C1"),
    ORANGE_WOOL("\uE0C2"),
    MAGENTA_WOOL("\uE0C3"),
    LIGHT_BLUE_WOOL("\uE0C4"),
    YELLOW_WOOL("\uE0C5"),
    LIME_WOOL("\uE0C6"),
    PINK_WOOL("\uE0C7"),
    GRAY_WOOL("\uE0C8"),
    LIGHT_GRAY_WOOL("\uE0C9"),
    CYAN_WOOL("\uE0CA"),
    PURPLE_WOOL("\uE0CB"),
    BLUE_WOOL("\uE0CC"),
    BROWN_WOOL("\uE0CD"),
    GREEN_WOOL("\uE0CE"),
    RED_WOOL("\uE0CF"),
    BLACK_WOOL("\uE0D0"),
    WITHER_ROSE("\uE597"),
    DANDELION("\uE598"),
    POPPY("\uE0D2"),
    BLUE_ORCHID("\uE0D3"),
    ALLIUM("\uE0D4"),
    AZURE_BLUET("\uE0D5"),
    RED_TULIP("\uE0D6"),
    ORANGE_TULIP("\uE0D7"),
    WHITE_TULIP("\uE0D8"),
    PINK_TULIP("\uE0D9"),
    OXEYE_DAISY("\uE0DA"),
    LILY_OF_THE_VALLEY("\uE8D2"),
    BROWN_MUSHROOM("\uE0DB"),
    RED_MUSHROOM("\uE0DC"),
    OAK_SAPLING("\uE016"),
    SPRUCE_SAPLING("\uE017"),
    BIRCH_SAPLING("\uE018"),
    JUNGLE_SAPLING("\uE018"),
    ACACIA_SAPLING("\uE01A"),
    DARK_OAK_SAPLING("\uE01B"),
    MANGROVE_PROPAGULE("\uE8A4"),
    SEA_PICKLE("\uE599"),
    BRICKS("\uE0DF"),
    TNT("\uE0E0"),
    BOOKSHELF("\uE0E3"),
    MOSSY_COBBLESTONE("\uE0E4"),
    OBSIDIAN("\uE0E5"),
    FIRE("\uE0E7"),
    SPAWNER("\uE0E9"),
    CHEST("\uE0EE"),
    CHRISTMAS_CHEST("\uE0F1"),
    CRAFTING_TABLE("\uE128"),
    WHEAT_CROP("\uE130"),
    FARMLAND("\uE131"),
    FURNACE("\uE133"),
    BLAST_FURNACE("\uE58F"),
    LADDER("\uE13A"),
    STONE_BUTTON("\uE153"),
    SNOW("\uE15B"),
    CACTUS("\uE15D"),
    CLAY_BLOCK("\uE160"),
    SUGAR_CANE("\uE161"),
    JUKEBOX("\uE162"),
    CRIMSON_FUNGUS("\uE8D6"),
    WARPED_FUNGUS("\uE8D7"),
    NETHERRACK("\uE167"),
    SOUL_SAND("\uE168"),
    GLOWSTONE("\uE169"),
    NETHER_PORTAL("\uE16A"),
    CARVED_PUMPKIN("\uE16B"),
    JACK_O_LANTERN("\uE16C"),
    CAKE_TOP("\uE16D"),
    CAKE("\uE5D5"),
    WHITE_STAINED_GLASS("\uE1DD"),
    ORANGE_STAINED_GLASS("\uE1DE"),
    MAGENTA_STAINED_GLASS("\uE1DF"),
    LIGHT_BLUE_STAINED_GLASS("\uE1E0"),
    YELLOW_STAINED_GLASS("\uE1E1"),
    LIME_STAINED_GLASS("\uE1E2"),
    PINK_STAINED_GLASS("\uE1E3"),
    GRAY_STAINED_GLASS("\uE1E4"),
    LIGHT_GRAY_STAINED_GLASS("\uE1E5"),
    CYAN_STAINED_GLASS("\uE1E6"),
    PURPLE_STAINED_GLASS("\uE1E7"),
    BLUE_STAINED_GLASS("\uE1E8"),
    BROWN_STAINED_GLASS("\uE1E9"),
    GREEN_STAINED_GLASS("\uE1EA"),
    RED_STAINED_GLASS("\uE1EB"),
    BLACK_STAINED_GLASS("\uE1EC"),
    OAK_TRAPDOOR("\uE1ED"),
    SPRUCE_TRAPDOOR("\uE1EF"),
    BIRCH_TRAPDOOR("\uE1F1"),
    JUNGLE_TRAPDOOR("\uE1F3"),
    ACACIA_TRAPDOOR("\uE1F5"),
    DARK_OAK_TRAPDOOR("\uE1F7"),
    STONE_BRICKS("\uE1FF"),
    MOSSY_STONE_BRICKS("\uE200"),
    CRACKED_STONE_BRICKS("\uE201"),
    CHISELED_STONE_BRICKS("\uE202"),
    MOSHROOM_BLOCK("\uE203"),
    BROWN_MUSHROOM_BLOCK("\uE204"),
    RED_MUSHROOM_BLOCK("\uE205"),
    MUSHROOM_STEM("\uE206"),
    IRON_BARS("\uE207"),
    MELON("\uE209"),
    VINE("\uE21D"),
    MYCELIUM("\uE228"),
    LILLY_PAD("\uE229"),
    NETHER_BRICKS("\uE22A"),
    ENCHANTING_TABLE("\uE236"),
    BREWING_STAND("\uE58A"),
    CAULDRON("\uE23B"),
    END_PORTAL("\uE23F"),
    END_PORTAL_FRAME("\uE242"),
    END_STONE("\uE24A"),
    DRAGON_EGG("\uE24B"),
    REDSTONE_LAMP("\uE24C"),
    ENDER_CHEST("\uE259"),
    COMMAND_BLOCK("\uE271"),
    BEACON("\uE27B"),
    SKELETON_SKULL("\uE28F"),
    WITHER_SKELETON_SKULL("\uE290"),
    ZOMBIE_HEAD("\uE291"),
    PLAYER_HEAD("\uE292"),
    CREEPER_HEAD("\uE293"),
    DRAGON_HEAD("\uE294"),
    ANVIL("\uE295"),
    TRAPPED_CHEST("\uE299"),
    DAYLIGHT_DETECTOR("\uE2B7"),
    HOPPER("\uE2BC"),
    CHISELED_QUARTZ_BLOCK("\uE2C5"),
    QUARTZ_PILLAR_BLOCK("\uE2C6"),
    ACTIVATOR_RAIL("\uE2CD"),
    DROPPER("\uE2D1"),
    TERRACOTTA("\uE32A"),
    WHITE_TERRACOTTA("\uE2D5"),
    ORANGE_TERRACOTTA("\uE2D6"),
    MAGENTA_TERRACOTTA("\uE2D7"),
    LIGHT_BLUE_TERRACOTTA("\uE2D8"),
    YELLOW_TERRACOTTA("\uE2D9"),
    LIME_TERRACOTTA("\uE2DA"),
    PINK_TERRACOTTA("\uE2DB"),
    GRAY_TERRACOTTA("\uE2DC"),
    LIGHT_GRAY_TERRACOTTA("\uE2DD"),
    CYAN_TERRACOTTA("\uE2DE"),
    PURPLE_TERRACOTTA("\uE2DF"),
    BLUE_TERRACOTTA("\uE2E0"),
    BROWN_TERRACOTTA("\uE2E1"),
    GREEN_TERRACOTTA("\uE2E2"),
    RED_TERRACOTTA("\uE2E3"),
    BLACK_TERRACOTTA("\uE2E4"),
    SLIME_BLOCK("\uE2FD"),
    BARRIER("\uE2FE"),
    IRON_TRAPDOOR("\uE2FF"),
    PRISMARINE("\uE301"),
    PRISMARINE_BRICKS("\uE302"),
    DARK_PRISMARINE("\uE303"),
    SEA_LANTERN("\uE316"),
    HAY_BALE("\uE318"),
    BLOCK_OF_COAL("\uE32B"),
    PACKED_ICE("\uE32C"),
    BLUE_ICE("\uE15C"),
    ICE("\uE3C8"),
    FROSTED_ICE("\uE3CB"),
    SUNFLOWER("\uE330"),
    LILAC("\uE332"),
    ROSE_BUSH("\uE334"),
    PEONY("\uE336"),
    RED_SANDSTONE("\uE35C"),
    CHISELED_RED_SANDSTONE("\uE35F"),
    CUT_RED_SANDSTONE("\uE360"),
    SMOOTH_STONE("\uE374"),
    END_ROD("\uE39F"),
    PURPUR_BLOCK("\uE3A4"),
    PURPUR_PILLAR("\uE3A5"),
    PURPUR_PILLAR_SIDE("\uE3A6"),
    END_STONE_BRICKS("\uE3AC"),
    GRASS_PATH("\uE3B2"),
    REPEATING_COMMAND_BLOCK("\uE3B5"),
    CHAIN_COMMAND_BLOCK("\uE3BF"),
    MAGMA_BLOCK("\uE3CC"),
    NETHER_WART_BLOCK("\uE3CD"),
    RED_NETHER_BRICKS("\uE3CE"),
    BONE_BLOCK("\uE3CF"),
    STRUCTURE_VOID("\uE3D2"),
    OBSERVER("\uE3D3"),
    SHULKER_BOX("\uE3DB"),
    WHITE_SHULKER_BOX("\uE3E1"),
    ORANGE_SHULKER_BOX("\uE3E7"),
    MAGENTA_SHULKER_BOX("\uE3ED"),
    LIGHT_BLUE_SHULKER_BOX("\uE3F3"),
    YELLOW_SHULKER_BOX("\uE3F9"),
    LIME_SHULKER_BOX("\uE3FF"),
    PINK_SHULKER_BOX("\uE405"),
    GRAY_SHULKER_BOX("\uE40B"),
    LIGHT_GRAY_SHULKER_BOX("\uE411"),
    CYAN_SHULKER_BOX("\uE417"),
    PURPLE_SHULKER_BOX("\uE41D"),
    BLUE_SHULKER_BOX("\uE423"),
    BROWN_SHULKER_BOX("\uE429"),
    GREEN_SHULKER_BOX("\uE42F"),
    RED_SHULKER_BOX("\uE435"),
    BLACK_SHULKER_BOX("\uE43B"),
    WHITE_GLAZED_TERRACOTTA("\uE43F"),
    ORANGE_GLAZED_TERRACOTTA("\uE443"),
    MAGENTA_GLAZED_TERRACOTTA_NORTH("\uE447"),
    MAGENTA_GLAZED_TERRACOTTA_SOUTH("\uE448"),
    MAGENTA_GLAZED_TERRACOTTA_WEST("\uE449"),
    MAGENTA_GLAZED_TERRACOTTA_EAST("\uE44A"),
    LIGHT_BLUE_GLAZED_TERRACOTTA("\uE44B"),
    YELLOW_GLAZED_TERRACOTTA("\uE44F"),
    LIME_GLAZED_TERRACOTTA("\uE453"),
    PINK_GLAZED_TERRACOTTA("\uE457"),
    GRAY_GLAZED_TERRACOTTA("\uE45B"),
    LIGHT_GRAY_GLAZED_TERRACOTTA("\uE45F"),
    CYAN_GLAZED_TERRACOTTA("\uE463"),
    PURPLE_GLAZED_TERRACOTTA("\uE467"),
    BLUE_GLAZED_TERRACOTTA("\uE46B"),
    BROWN_GLAZED_TERRACOTTA("\uE46F"),
    GREEN_GLAZED_TERRACOTTA("\uE473"),
    RED_GLAZED_TERRACOTTA("\uE477"),
    BLACK_GLAZED_TERRACOTTA("\uE47B"),
    WHITE_CONCRETE("\uE47F"),
    ORANGE_CONCRETE("\uE480"),
    MAGENTA_CONCRETE("\uE481"),
    LIGHT_BLUE_CONCRETE("\uE482"),
    YELLOW_CONCRETE("\uE483"),
    LIME_CONCRETE("\uE484"),
    PINK_CONCRETE("\uE485"),
    GRAY_CONCRETE("\uE486"),
    LIGHT_GRAY_CONCRETE("\uE487"),
    CYAN_CONCRETE("\uE488"),
    PURPLE_CONCRETE("\uE489"),
    BLUE_CONCRETE("\uE48A"),
    BROWN_CONCRETE("\uE48B"),
    GREEN_CONCRETE("\uE48C"),
    RED_CONCRETE("\uE48D"),
    BLACK_CONCRETE("\uE48E"),
    WHITE_CONCRETE_POWDER("\uE48F"),
    ORANGE_CONCRETE_POWDER("\uE490"),
    MAGENTA_CONCRETE_POWDER("\uE491"),
    LIGHT_BLUE_CONCRETE_POWDER("\uE492"),
    YELLOW_CONCRETE_POWDER("\uE493"),
    LIME_CONCRETE_POWDER("\uE494"),
    PINK_CONCRETE_POWDER("\uE495"),
    GRAY_CONCRETE_POWDER("\uE496"),
    LIGHT_GRAY_CONCRETE_POWDER("\uE497"),
    CYAN_CONCRETE_POWDER("\uE498"),
    PURPLE_CONCRETE_POWDER("\uE499"),
    BLUE_CONCRETE_POWDER("\uE49A"),
    BROWN_CONCRETE_POWDER("\uE49B"),
    GREEN_CONCRETE_POWDER("\uE49C"),
    RED_CONCRETE_POWDER("\uE49D"),
    BLACK_CONCRETE_POWDER("\uE49E"),
    DRIED_KELP_BLOCK("\uE4A1"),
    TURTLE_EGG("\uE4ED"),
    TUBE_CORAL_BLOCK("\uE4B5"),
    BRAIN_CORAL_BLOCK("\uE4B6"),
    BUBBLE_CORAL_BLOCK("\uE4B7"),
    FIRE_CORAL_BLOCK("\uE4B8"),
    HORN_CORAL_BLOCK("\uE4B9"),
    TUBE_CORAL("\uE4BF"),
    BRAIN_CORAL("\uE4C0"),
    BUBBLE_CORAL("\uE4C1"),
    FIRE_CORAL("\uE4C2"),
    HORN_CORAL("\uE4C3"),
    TUBE_CORAL_FAN("\uE4C9"),
    BRAIN_CORAL_FAN("\uE4CA"),
    BUBBLE_CORAL_FAN("\uE4CB"),
    FIRE_CORAL_FAN("\uE4CC"),
    HORN_CORAL_FAN("\uE4CD"),
    CONDUIT("\uE4D9"),
    IRON_DOOR("\uE4EE"),
    OAK_DOOR("\uE4EF"),
    SPRUCE_DOOR("\uE4F0"),
    BIRCH_DOOR("\uE4F1"),
    JUNGLE_DOOR("\uE4F2"),
    ACACIA_DOOR("\uE4F3"),
    DARK_OAK_DOOR("\uE4F4"),
    REDSTONE_REPEATER("\uE4F5"),
    REDSTONE_COMPARATOR("\uE4F6"),
    TURTLE_SHELL("\uE4F7"),
    SCUTE("\uE4F8"),
    FLINT_AND_STEEL("\uE4FC"),
    APPLE("\uE4FD"),
    BOW("\uE4FE"),
    BOW_PULLED("\uE501"),
    ARROW("\uE502"),
    WOODEN_SWORD("\uE509"),
    WOODEN_SHOVEL("\uE50A"),
    WOODEN_PICKAXE("\uE50B"),
    WOODEN_AXE("\uE50C"),
    WOODEN_HOE("\uE51F"),
    STONE_SWORD("\uE50D"),
    STONE_SHOVEL("\uE50E"),
    STONE_PICKAXE("\uE50F"),
    STONE_AXE("\uE510"),
    STONE_HOE("\uE520"),
    GOLDEN_SWORD("\uE518"),
    GOLDEN_SHOVEL("\uE519"),
    GOLDEN_PICKAXE("\uE51A"),
    GOLDEN_AXE("\uE51B"),
    GOLDEN_HOE("\uE523"),
    IRON_SHOVEL("\uE4F9"),
    IRON_PICKAXE("\uE4FA"),
    IRON_AXE("\uE4FB"),
    IRON_SWORD("\uE508"),
    IRON_HOE("\uE521"),
    DIAMOND_SWORD("\uE511"),
    DIAMOND_SHOVEL("\uE512"),
    DIAMOND_PICKAXE("\uE513"),
    DIAMOND_AXE("\uE514"),
    DIAMOND_HOE("\uE522"),
    NETHERITE_SWORD("\uE581"),
    NETHERITE_SHOVEL("\uE582"),
    NETHERITE_PICKAXE("\uE583"),
    NETHERITE_AXE("\uE584"),
    NETHERITE_HOE("\uE585"),
    STICK("\uE515"),
    BOWL("\uE516"),
    MUSHROOM_STEW("\uE517"),
    STRING("\uE51C"),
    FEATHER("\uE51D"),
    GUNPOWDER("\uE51E"),
    WHEAT_SEEDS("\uE524"),
    WHEAT("\uE525"),
    BREAD("\uE526"),
    LEATHER_CAP("\uE527"),
    LEATHER_TUNIC("\uE528"),
    LEATHER_PANTS("\uE529"),
    LEATHER_BOOTS("\uE52A"),
    CHAINMAIL_HELMET("\uE52B"),
    CHAINMAIL_CHESTPLATE("\uE52C"),
    CHAINMAIL_LEGGINGS("\uE52D"),
    CHAINMAIL_BOOTS("\uE52E"),
    IRON_HELMET("\uE52F"),
    IRON_CHESTPLATE("\uE530"),
    IRON_LEGGINGS("\uE531"),
    IRON_BOOTS("\uE532"),
    DIAMOND_HELMET("\uE533"),
    DIAMOND_CHESTPLATE("\uE534"),
    DIAMOND_LEGGINGS("\uE535"),
    DIAMOND_BOOTS("\uE536"),
    NETHERITE_HELMET("\uE57D"),
    NETHERITE_CHESTPLATE("\uE57E"),
    NETHERITE_LEGGINGS("\uE57F"),
    NETHERITE_BOOTS("\uE580"),
    GOLDEN_HELMET("\uE537"),
    GOLDEN_CHESTPLATE("\uE538"),
    GOLDEN_LEGGINGS("\uE539"),
    GOLDEN_BOOTS("\uE53A"),
    FLINT("\uE53B"),
    RAW_PORKCHOP("\uE53C"),
    COOKED_PORKCHOP("\uE53D"),
    GOLDEN_APPLE("\uE53F"),
    ENCHANTED_GOLDEN_APPLE("\uE540"),
    PAINTING("\uE53E"),
    OAK_SIGN("\uE541"),
    BUCKET("\uE542"),
    WATER_BUCKET("\uE543"),
    LAVA_BUCKET("\uE544"),
    MILK_BUCKET("\uE54B"),
    BUCKET_OF_PUFFERFISH("\uE54C"),
    BUCKET_OF_SALMON("\uE54D"),
    BUCKET_OF_COD("\uE54E"),
    BUCKET_OF_TROPICAL_FISH("\uE54F"),
    AXOLOTL_BUCKET("\uE58E"),
    MINECART("\uE58C"),
    MINECART_WITH_CHEST("\uE557"),
    MINECART_WITH_FURNACE("\uE558"),
    MINECART_WITH_TNT("\uE65D"),
    MINECART_WITH_HOPPER("\uE65E"),
    MINECART_WITH_COMMAND_BLOCK("\uE66C"),
    SADDLE("\uE546"),
    SNOWBALL("\uE548"),
    LEATHER("\uE54A"),
    BRICK("\uE550"),
    CLAY("\uE551"),
    KELP("\uE553"),
    PAPER("\uE554"),
    BOOK("\uE555"),
    SLIMEBALL("\uE556"),
    EGG("\uE559"),
    COMPASS("\uE56D"),
    CLOCK("\uE57C"),
    GLOWSTONE_DUST("\uE5BC"),
    COOKED_COD("\uE5C1"),
    COOKED_SALMON("\uE5C2"),
    INK_SAC("\uE5C3"),
    ROSE_RED("\uE5C4"),
    CACTUS_GREEN("\uE5C5"),
    COCA_BEANS("\uE5C6"),
    PURPLE_DYE("\uE5C8"),
    CYAN_DYE("\uE5C9"),
    LIGHT_GRAY_DYE("\uE5CA"),
    GRAY_DYE("\uE5CB"),
    PINK_DYE("\uE5CC"),
    LIME_DYE("\uE5CD"),
    DANDELION_YELLOW("\uE5CE"),
    LIGHT_BLUE_DYE("\uE5CF"),
    MAGENTA_DYE("\uE5D0"),
    ORANGE_DYE("\uE5D1"),
    BONE_MEAL("\uE5D2"),
    BONE("\uE5D3"),
    SUGAR("\uE5D4"),
    FISHING_ROD("\uE57A"),
    CARROT_ON_A_STICK("\uE655"),
    WHITE_BED("\uE5D6"),
    ORANGE_BED("\uE5D7"),
    MAGENTA_BED("\uE5D8"),
    LIGHT_BLUE_BED("\uE5D9"),
    YELLOW_BED("\uE5DA"),
    LIME_BED("\uE5DB"),
    PINK_BED("\uE5DC"),
    GRAY_BED("\uE5DD"),
    LIGHT_GRAY_BED("\uE5DE"),
    CYAN_BED("\uE5DF"),
    PURPLE_BED("\uE5E0"),
    BLUE_BED("\uE5E1"),
    BROWN_BED("\uE5E2"),
    GREEN_BED("\uE5E3"),
    RED_BED("\uE5E4"),
    BLACK_BED("\uE5E5"),
    COOKIE("\uE5E6"),
    MAP("\uE5E7"),
    OCEAN_EXPLORER_MAP("\uE5E8"),
    EMPTY_MAP("\uE653"),
    SHEARS("\uE5EA"),
    MELON_SLICE("\uE5EB"),
    DRIED_KELP("\uE5EC"),
    PUMPKIN_SEEDS("\uE5ED"),
    MELON_SEEDS("\uE5EE"),
    RAW_BEEF("\uE5EF"),
    STEAK("\uE5F0"),
    RAW_CHICKEN("\uE5F1"),
    COOKED_CHICKEN("\uE5F2"),
    ROTTEN_FLESH("\uE5F3"),
    ENDER_PEARL("\uE5F4"),
    BLAZE_ROD("\uE59A"),
    GHAST_TEAR("\uE5F6"),
    GOLD_NUGGET("\uE5F7"),
    NETHER_WART("\uE5F8"),
    WATER_BOTTLE("\uE5FA"),
    POTION_OF_NIGHT_VISION("\uE5FB"),
    POTION_OF_INVISIBILITY("\uE5FC"),
    POTION_OF_LEAPING("\uE5FD"),
    POTION_OF_FIRE_RESISTANCE("\uE5FE"),
    POTION_OF_SWIFTNESS("\uE5FF"),
    POTION_OF_SLOWNESS("\uE600"),
    POTION_OF_THE_TURTLE_MASTER("\uE601"),
    POTION_OF_WATER_BREATHING("\uE602"),
    POTION_OF_HEALING("\uE603"),
    POTION_OF_HARMING("\uE604"),
    POTION_OF_POISON("\uE605"),
    POTION_OF_REGENERATION("\uE606"),
    POTION_OF_STRENGTH("\uE607"),
    POTION_OF_WEAKNESS("\uE608"),
    POTION_OF_LUCK("\uE609"),
    POTION_OF_SLOW_FALLING("\uE60A"),
    GLASS_BOTTLE("\uE60B"),
    SPLASH_WATER_BOTTLE("\uE687"),
    SPLASH_POTION_OF_NIGHT_VISION("\uE688"),
    SPLASH_POTION_OF_INVISIBILITY("\uE689"),
    SPLASH_POTION_OF_LEAPING("\uE68A"),
    SPLASH_POTION_OF_FIRE_RESISTANCE("\uE68B"),
    SPLASH_POTION_OF_SWIFTNESS("\uE68C"),
    SPLASH_POTION_OF_SLOWNESS("\uE68D"),
    SPLASH_POTION_OF_THE_TURTLE_MASTER("\uE68E"),
    SPLASH_POTION_OF_WATER_BREATHING("\uE68F"),
    SPLASH_POTION_OF_HEALING("\uE690"),
    SPLASH_POTION_OF_HARMING("\uE691"),
    SPLASH_POTION_OF_POISON("\uE692"),
    SPLASH_POTION_OF_REGENERATION("\uE693"),
    SPLASH_POTION_OF_STRENGTH("\uE694"),
    SPLASH_POTION_OF_WEAKNESS("\uE695"),
    SPLASH_POTION_OF_LUCK("\uE696"),
    SPLASH_POTION_OF_SLOW_FALLING("\uE697"),
    SPECTRAL_ARROW("\uE698"),
    ARROW_OF_NIGHT_VISION("\uE69A"),
    ARROW_OF_INVISIBILITY("\uE69B"),
    ARROW_OF_LEAPING("\uE69C"),
    ARROW_OF_FIRE_RESISTANCE("\uE69D"),
    ARROW_OF_SWIFTNESS("\uE69E"),
    ARROW_OF_SLOWNESS("\uE69F"),
    ARROW_OF_THE_TURTLE_MASTER("\uE6A0"),
    ARROW_OF_WATER_BREATHING("\uE6A1"),
    ARROW_OF_HEALING("\uE6A2"),
    ARROW_OF_HARMING("\uE6A3"),
    ARROW_OF_POISON("\uE6A4"),
    ARROW_OF_REGENERATION("\uE6A5"),
    ARROW_OF_STRENGTH("\uE6A6"),
    ARROW_OF_WEAKNESS("\uE6A7"),
    ARROW_OF_LUCK("\uE6A8"),
    ARROW_OF_SLOW_FALLING("\uE6A9"),
    LINGERING_POTION_WATER_BOTTLE("\uE6AB"),
    LINGERING_POTION_OF_NIGHT_VISION("\uE6AC"),
    LINGERING_POTION_OF_INVISIBILITY("\uE6AD"),
    LINGERING_POTION_OF_LEAPING("\uE6AE"),
    LINGERING_POTION_OF_FIRE_RESISTANCE("\uE6AF"),
    LINGERING_POTION_OF_SWIFTNESS("\uE6B0"),
    LINGERING_POTION_OF_SLOWNESS("\uE6B1"),
    LINGERING_POTION_OF_THE_TURTLE_MASTER("\uE6B2"),
    LINGERING_POTION_OF_WATER_BREATHING("\uE6B3"),
    LINGERING_POTION_OF_HEALING("\uE6B4"),
    LINGERING_POTION_OF_HARMING("\uE6B5"),
    LINGERING_POTION_OF_POISON("\uE6B6"),
    LINGERING_POTION_OF_REGENERATION("\uE6B7"),
    LINGERING_POTION_OF_STRENGTH("\uE6B8"),
    LINGERING_POTION_OF_WEAKNESS("\uE6B9"),
    LINGERING_POTION_OF_LUCK("\uE6BA"),
    LINGERING_POTION_OF_SLOW_FALLING("\uE6BB"),
    SPIDER_EYE("\uE60C"),
    FERMENTED_SPIDER_EYE("\uE60D"),
    BLAZE_POWDER("\uE60E"),
    MAGMA_CREAM("\uE60F"),
    EYE_OF_ENDER("\uE612"),
    GLISTERING_MELON_SLICE("\uE613"),
    EXPERIENCE_BOTTLE("\uE648"),
    FIRE_CHARGE("\uE649"),
    BOOK_AND_QUILL("\uE64A"),
    WRITTEN_BOOK("\uE64B"),
    ITEM_FRAME("\uE64D"),
    FLOWER_POT("\uE64E"),
    GOAT_HORN("\uE591"),
    CANDLE("\uE595"),
    SCULK("\uE596"),
    BEEHIVE("\uE593"),
    BEE_NEST("\uE594"),
    CARROT("\uE64F"),
    GOLDEN_CARROT("\uE654"),
    POTATO("\uE650"),
    BAKED_POTATO("\uE651"),
    POISONOUS_POTATO("\uE652"),
    PUMPKIN_PIE("\uE657"),
    FIREWORK_ROCKET("\uE658"),
    FIREWORK_STAR("\uE659"),
    ENCHANTED_BOOK("\uE65A"),
    NETHER_BRICK("\uE65B"),
    PRISMARINE_SHARD("\uE65F"),
    PRISMARINE_CRYSTAL("\uE660"),
    RAW_RABBIT("\uE661"),
    COOKED_RABBIT("\uE662"),
    RABBIT_STEW("\uE663"),
    RABBITS_FOOT("\uE664"),
    RABBITS_HIDE("\uE665"),
    ARMOR_STAND("\uE666"),
    IRON_HORSE_ARMOR("\uE667"),
    GOLDEN_HORSE_ARMOR("\uE668"),
    DIAMOND_HORSE_ARMOR("\uE669"),
    LEAD("\uE66A"),
    NAME_TAG("\uE66B"),
    RAW_MUTTON("\uE66D"),
    COOKED_MUTTON("\uE66E"),
    CHORUS_FRUIT("\uE680"),
    POPPED_CHORUS_FRUIT("\uE681"),
    BEETROOT("\uE682"),
    BEETROOT_SEEDS("\uE683"),
    BEETROOT_SOUP("\uE684"),
    DRAGONS_BREATH("\uE685"),
    ELYTRA("\uE6BD"),
    BROKEN_ELYTRA("\uE6BE"),
    SPRUCE_BOAT("\uE6BF"),
    BIRCH_BOAT("\uE6C0"),
    JUNGLE_BOAT("\uE6C1"),
    ACACIA_BOAT("\uE6C2"),
    DARK_OAK_BOAT("\uE6C3"),
    TOTEM_OF_UNDYING("\uE6C4"),
    SHULKER_SHELL("\uE6C5"),
    IRON_NUGGET("\uE6C6"),
    KNOWLEDGE_BOOK("\uE6C7"),
    MUSIC_DISC_13("\uE6C8"),
    MUSIC_DISC_CAT("\uE6C9"),
    MUSIC_DISC_BLOCKS("\uE6CA"),
    MUSIC_DISC_CHIRP("\uE6CB"),
    MUSIC_DISC_FAR("\uE6CC"),
    MUSIC_DISC_MALL("\uE6CD"),
    MUSIC_DISC_MELLOHI("\uE6CE"),
    MUSIC_DISC_STAL("\uE6CF"),
    MUSIC_DISC_STRAD("\uE6D0"),
    MUSIC_DISC_WARD("\uE6D1"),
    MUSIC_DISC_5("\uE587"),
    MUSIC_DISC_PIGSTEP("\uE588"),
    MUSIC_DISC_11("\uE6D2"),
    MUSIC_DISC_OTHERSIDE("\uE589"),
    MUSIC_DISC_WAIT("\uE6D3"),
    TRIDENT("\uE6D4"),
    PHANTOM_MEMBRANE("\uE6D5"),
    NAUTILUS_SHELL("\uE6D6"),
    HEART_OF_THE_SEA("\uE6D7"),
    PAINTING_KEBAB("\uE6DF"),
    PAINTING_AZTEC("\uE6E0"),
    PAINTING_ALBAN("\uE6E1"),
    PAINTING_AZTEC2("\uE6E2"),
    PAINTING_BOMB("\uE6E3"),
    PAINTING_PLANT("\uE6E4"),
    PAINTING_WASTELAND("\uE6E5"),
    MANSION_MAP_MARKER("\uE775"),
    OCEAN_MONUMENT_MAP_MARKER("\uE776"),
    RED_CROSS_MAP_MARKER("\uE787"),
    AIR_BUBBLE("\uE788"),
    FISHING_HOOK("\uE789"),
    MUSICAL_NOTE("\uE78A"),
    BLACK_HEART("\uE78D"),
    HEART("\uE78E"),
    ANGER_PARTICLE("\uE78F"),
    HAPPY_PARTICLE("\uE790"),
    DROPLET_PARTICLE("\uE792"),
    CONDUIT_PARTICLE("\uE794"),
    EFFECT_SPEED("\uE7AF"),
    EFFECT_SLOWNESS("\uE7B0"),
    EFFECT_HASTE("\uE7B1"),
    EFFECT_MINING_FATIGUE("\uE7B2"),
    EFFECT_STRENGTH("\uE7B3"),
    EFFECT_WEAKNESS("\uE7B4"),
    EFFECT_POISON("\uE7B5"),
    EFFECT_REGENERATION("\uE7B6"),
    EFFECT_INVISIBILITY("\uE7B7"),
    EFFECT_HUNGER("\uE7B8"),
    EFFECT_JUMP_BOOST("\uE7B9"),
    EFFECT_NAUSEA("\uE7BA"),
    EFFECT_NIGHT_VISION("\uE7BB"),
    EFFECT_BLINDNESS("\uE7BC"),
    EFFECT_RESISTANCE("\uE7BD"),
    EFFECT_FIRE_RESISTANCE("\uE7BE"),
    EFFECT_WATER_BREATHING("\uE7BF"),
    EFFECT_WITHER("\uE7C0"),
    EFFECT_ABSORPTION("\uE7C1"),
    EFFECT_LEVITATION("\uE7C2"),
    EFFECT_GLOWING("\uE7C3"),
    EFFECT_LUCK("\uE7C4"),
    EFFECT_BAD_LUCK("\uE7C5"),
    EFFECT_HEALTH_BOOST("\uE7C6"),
    EFFECT_SLOW_FALLING("\uE7C7"),
    EFFECT_CONDUIT_POWER("\uE7C8"),
    EFFECT_DOLPHINS_GRACE("\uE7C9"),
    ANVIL_HAMMER("\uE7D0"),
    MOON("\uE7E0"),
    SUN("\uE7E8"),
    EXPERIENCE_ORB("\uE7E9"),
    AREA_EFFECT("\uE7EA"),
    END_CRYSTAL_FACE_1("\uE897"),
    END_CRYSTAL_FACE_2("\uE898"),
    END_CRYSTAL_FACE_3("\uE899"),
    END_CRYSTAL_FACE_4("\uE89A"),
    END_CRYSTAL_FACE_5("\uE89B"),
    END_CRYSTAL_FACE_6("\uE89C"),
    STEVE("\uE89D"),
    ALEX("\uE89F"),
    TORCH("\uE8B4"),
    SOUL_TORCH("\uE590"),
    REDSTONE_TORCH("\uE8B8"),

    ;

    @Getter private final String unicode;
}
