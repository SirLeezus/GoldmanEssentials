package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.menus.WelcomeMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelpCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            List<Component> lines = new ArrayList<>();
            int number = 1;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("essentials")) {

                    lines.add(Lang.COMMAND_HELP_ESSENTIALS_DIVIDER.getComponent(null));
                    lines.add(Lang.COMMAND_HELP_ESSENTIALS_TITLE.getComponent(null));
                    lines.add(Component.text(""));

                    for (String pCommand : data.getPluginCommands()) {
                        Command sCommand = Bukkit.getCommandMap().getCommand(pCommand);
                        if (sCommand != null) {
                            String permission = sCommand.getPermission();
                            if (permission != null) {
                                if (player.hasPermission(permission)) {
                                    String suggestCommand = sCommand.getUsage().contains(" ") ? sCommand.getUsage().split(" ")[0] : sCommand.getUsage();
                                    lines.add(Lang.COMMAND_HELP_ESSENTIALS.getComponent(new String[] { String.valueOf(number), sCommand.getUsage() }).hoverEvent(Lang.COMMAND_HELP_ESSENTIALS_HOVER.getComponent(new String[] {  sCommand.getDescription() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand)));
                                    number++;
                                }
                            }
                        }
                    }
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ESSENTIALS_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("welcome")) {
                    new WelcomeMenu(data.getPlayerMU(uuid)).open();
                    player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
                } else if (args[0].equalsIgnoreCase("hopperfilter")) {
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("     &2&l&nHow To Filter Hoppers Using Item Frames"));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 1&7: &ePlace a item frame on a hopper, any side works."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 2&7: &ePlace the item you want to filter in the item frame."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&eCongratulations! If you followed this guide your hopper should now only accept the items inside each item frame attached to the hopper."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("armorstand")) {
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("              &2&l&nHow To Adjust Armor Stands"));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 1&7: &ePlace a armor stand on the ground."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 2&7: &eSneak right-click the armor stand, that should bring up the menu."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&eCongratulations! If you followed this guide you should now be able to edit the armor stand using the menu options."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("claiming")) {
                    lines.add(Lang.COMMAND_HELP_CLAIMING_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("                &2&l&nHow To Claim Your Land"));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 1&7: &eStand on the chunk you want to claim."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&6Step 2&7: &eRun the command &6/chunk claim&e."));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&eCongratulations! If you followed this guide you should have claimed the chunk you're standing on."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_CLAIMING_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("store")) {
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&d&lStore: ").append(Lang.STORE.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.STORE.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("discord")) {
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&9&lDiscord: ").append(Lang.DISCORD.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.DISCORD.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("vanilla")) {
                    List<String> changes = new ArrayList<>();

                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent("&a&lWorld Difficulty: &cHard"));
                    lines.add(BukkitUtils.parseColorComponent("&a&lSeed: &e2378573831528949225").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "2378573831528949225")).hoverEvent(BukkitUtils.parseColorComponent("&eClick to Copy!")));
                    lines.add(Component.text(""));

                    changes.add("No swing delay with tools or weapons.");
                    changes.add("You can put any item on your head.");
                    changes.add("Item frames can be used to filter hoppers.");
                    changes.add("Armor stands can be modified by sneak-right-clicking them.");
                    changes.add("Trampling is disabled.");
                    changes.add("Llamas can be controlled when they have carpet on them.");
                    changes.add("Ender crystals will shoot phantoms.");
                    changes.add("Villagers will follow you if you're holding a emerald block.");
                    changes.add("Villagers can be leashed.");
                    changes.add("Villager clerics can farm nether wart.");
                    changes.add("Polar bears can be bred with salmon.");
                    changes.add("Chests can be opened with any block above it.");
                    changes.add("The more people sleeping the faster the night will be.");
                    changes.add("Anvils, signs and books support hex and normal color codes.");
                    changes.add("Infinity can be put on crossbows.");
                    changes.add("Phantoms do not target you if you're holding a torch.");
                    changes.add("You can feed a cow mushrooms to turn it into a mooshroom cow.");
                    changes.add("A creeper's health impacts the explosion power.");
                    changes.add("Ender dragon always drops full exp, a head and a dragon egg when killed.");
                    changes.add("Enderman ignore players who are wearing a dragon head.");
                    changes.add("Feeding a white/orange tulip to a fox changes the type to snow/regular.");
                    changes.add("You can breed parrots by feeding them any type of seeds.");
                    changes.add("Shulkers have a chance to spawn random colored shulkers with their bullets on impact.");
                    changes.add("Milk cures rabid wolves.");
                    changes.add("Mending always repairs the most damaged equipment first.");
                    changes.add("You only need a totem in your inventory for it to work.");
                    changes.add("If you have a totem in your inventory and are about to die in the void it'll teleport you to spawn.");
                    changes.add("Milk cures bad omen.");
                    changes.add("Ender pearls don't damage players when used.");
                    changes.add("Minecarts can be placed on anything and controlled.");
                    changes.add("Silk touch works on spawners.");
                    changes.add("You can break individual slabs when sneaking.");
                    changes.add("You can deactivate a spawner using redstone.");
                    changes.add("A max of 25 entities of each type are allowed in one chunk.");
                    changes.add("The stonecutter block will damage you if you walk on it.");
                    changes.add("You start with all recipes unlocked.");
                    changes.add("All entities have a chance of dropping a head.");
                    changes.add("You can toggle item frame invisibility by sneak-right-clicking them with shears.");
                    changes.add("Enchantments can be taken off items in anvils and put on enchanted books.");
                    changes.add("You can cross-enchant items instead of it requiring the same tool type.");
                    changes.add("All enchants that can be put on a type of tool or weapon no longer have enchantment conflicts.");
                    changes.add("You can disenchant enchanted books if you hold a normal book in your off-hand and sneak-click the enchanted book you want to disenchant.");
                    changes.add("Witches, Elder Guardians and Villagers have a chance of dropping a hat on death.");
                    changes.add("Dolphins can be controlled by right-clicking them, you can also use space bar to spit when riding a dolphin.");
                    changes.add("If you hold a compass a direction bar will appear on your screen.");
                    changes.add("Hoes will replant crops if you break them with one.");
                    changes.add("Rainbow sheep drop random color wool when sheared.");
                    changes.add("A wrench has been added as a recipe, it can rotate blocks with direction data.");
                    changes.add("Giants have a 5% chance to spawn with zombies in the resource world.");
                    changes.add("Illusioners have a 10% chance to spawn with pillagers in the resource world.");
                    changes.add("Drowned have a 50% chance of dropping a trident if they're holding one.");
                    changes.add("Killer Rabbits have a 3% chance to spawn with rabbits in the resource world.");
                    changes.add("Ocelots are more common in jungles.");
                    changes.add("You can right-click minecarts with a armor stand to insert a armor stand inside a minecart.");

                    for (String change : changes) {
                        lines.add(BukkitUtils.parseColorComponent("&3" + number + "&b. &6" + change));
                        number++;
                    }
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("enchants")) {
                    lines.add(Lang.COMMAND_HELP_ENCHANTS_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent(" &dLegendary enchants can be obtained from normal enchanting tables when you enchant a item at level 30. Custom enchants do work with enchanted books. Keep in mind you only have a small chance of receiving one. All legendary enchantments cost 1 exp to forge in anvils and are reduced to 0 exp cost when enchanting with multiple enchantments, this only applies to the enchantment cost itself. Hover over the enchantments down below for more details."));
                    lines.add(Component.text(""));

                    lines.add(BukkitUtils.parseColorComponent("&31&b. &#964B00Logger").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#964B00Logger\n\n&e&lHow does it work&7:\n&7When you chop a log it'll cut down the tree.\n\n&e&lSupported Items&7:\n&7All Axes\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&32&b. &#FCFF35Lightning Strike").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#FCFF35Lightning Strike\n\n&e&lHow does it work&7:\n&7When you sneak-left-click it'll summon lightning.\n\n&e&lSupported Items&7:\n&7All Swords\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&33&b. &#DE0000Destroyer").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#DE0000Destroyer\n\n&e&lHow does it work&7:\n&7When you break a block it will break a 3x3x3 area of blocks.\n\n&e&lSupported Items&7:\n&7All Shovels, All Pickaxes\n\n&e&lSide Effects:\n&7Does not drop exp when a ore is broken.\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&34&b. &#6A00E1Soul Bound").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#6A00E1Soul Bound\n\n&e&lHow does it work&7:\n&7Keep item on death.\n\n&e&lSupported Items&7:\n&7Elytra, Fishing Rod, Bow, Crossbow, Trident, Bundle, All Armor, All Tools, All Weapons\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&35&b. &#FF00E4Soul Reaper").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#FF00E4Soul Reaper\n\n&e&lHow does it work&7:\n&7Capture and release mobs by sneak-right-clicking them.\n\n&e&lSupported Items&7:\n&7All Hoes\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&36&b. &#FF9709Auto Sell").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#FF9709Auto Sell\n\n&e&lHow does it work&7:\n&7When you sneak-right-click a chest, barrel or shulker box it'll sell the items inside it.\n\n&e&lSupported Items&7:\n&7All Hoes\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&37&b. &#FF1700Life Steal").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#FF1700Life Steal\n\n&e&lHow does it work&7:\n&7When you kill a mob or player you get health. Each level gives you more health per-kill.\n\n&e&lSupported Items&7:\n&7All Swords, All Axes\n\n&e&lMax Level&7: \n&73")));
                    lines.add(BukkitUtils.parseColorComponent("&38&b. &#CF6010Molten Shot").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#CF6010Molten Shot\n\n&e&lHow does it work&7:\n&7When a arrow is shot it's changed to a fireball. Each level yields a bigger explosion. Works with the enchantment power.\n\n&e&lSupported Items&7:\n&7Bow\n\n&e&lMax Level&7: \n&73")));
                    lines.add(BukkitUtils.parseColorComponent("&39&b. &#FFBF00Smelting").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#FFBF00Smelting\n\n&e&lHow does it work&7:\n&7Smelts blocks when broken.\n\n&e&lSupported Items&7:\n&7All Axes, All Pickaxes, All Shovels\n\n&e&lMax Level&7: \n&70")));
                    lines.add(BukkitUtils.parseColorComponent("&310&b. &#16B037Head Hunter").hoverEvent(BukkitUtils.parseColorComponent("&5&lEnchantment&7:\n&#16B037Head Hunter\n\n&e&lHow does it work&7:\n&7Each level will give you a higher chance of dropping a mob head.\n\n&e&lSupported Items&7:\n&7All Axes, All Swords\n\n&e&lMax Level&7: \n&73")));

                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ENCHANTS_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("mysterybox")) {
                    lines.add(Lang.COMMAND_HELP_MYSTERY_BOX_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent(" &5You can earn mystery boxes by ranking up or in our online shop! Each rank up you earn 1 mystery box. Each box will reward you one cosmetic. Currently we offer pets, trails, trail styles and skins!"));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_MYSTERY_BOX_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("skins")) {
                    lines.add(Lang.COMMAND_HELP_SKINS_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(BukkitUtils.parseColorComponent(" &9You can earn skins by getting lucky from the mystery box! Skins can be forged in anvils, to figure out what items the skin supports look at the lore. To preview all the skins run the command &6/skins&9. For more info about the mystery box run the command &6/help mysterybox&9."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_SKINS_DIVIDER.getComponent(null));
                } else if (args[0].equalsIgnoreCase("shops")) {
                    player.chat("/shops help");
                } else if (args[0].equalsIgnoreCase("locker")) {
                    player.chat("/lock help");
                } else if (args[0].equalsIgnoreCase("chunks")) {
                    player.chat("/chunk help");
                } else if (args[0].equalsIgnoreCase("pets")) {
                    player.chat("/pets");
                } else if (args[0].equalsIgnoreCase("trails")) {
                    player.chat("/trails");
                }
                for (Component message : lines) player.sendMessage(message);

            } else {
                lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
                lines.add(Component.text(""));

                lines.add(BukkitUtils.parseColorComponent("&e1&7. &2&lEssentials").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "essential" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help essentials")));
                lines.add(BukkitUtils.parseColorComponent("&e2&7. &6&lShops").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "shop" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help")));
                lines.add(BukkitUtils.parseColorComponent("&e3&7. &c&lLocker").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "locker" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")));
                lines.add(BukkitUtils.parseColorComponent("&e4&7. &e&lChunks").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "chunk" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk help")));
                lines.add(BukkitUtils.parseColorComponent("&e5&8. &#8D27FF&lEnchants").hoverEvent(Lang.COMMAND_HELP_ENCHANT_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help enchants")));
                lines.add(BukkitUtils.parseColorComponent("&e6&7. &b&lPets").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "pets" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pet help")));
                lines.add(BukkitUtils.parseColorComponent("&e7&7. &d&lTrails").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "trails" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trail help")));
                lines.add(BukkitUtils.parseColorComponent("&e8&7. &9&lSkins").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "skins" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help skins")));
                lines.add(BukkitUtils.parseColorComponent("&e9&7. &5&lMystery Box").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "mystery box" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help mysterybox")));
                lines.add(BukkitUtils.parseColorComponent("&e10&7. &#ff843d&lVanilla").hoverEvent(Lang.COMMAND_HELP_PLUGIN_VANILLA_CHANGES_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help vanilla")));

                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_HELP_SPLITTER.getComponent(null));
                for (Component line : lines) player.sendMessage(line);
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
