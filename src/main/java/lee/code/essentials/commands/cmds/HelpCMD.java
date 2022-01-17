package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HelpCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {

            List<Component> lines = new ArrayList<>();
            int number = 1;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("essentials")) {

                    lines.add(Lang.COMMAND_HELP_ESSENTIALS_DIVIDER.getComponent(null));
                    lines.add(Lang.COMMAND_HELP_ESSENTIALS_TITLE.getComponent(null));
                    lines.add(Component.text(""));

                    for (String pCommand : plugin.getData().getPluginCommands()) {
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
                    lines.add(Lang.COMMAND_HELP_WELCOME_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC(" &eWelcome to &2&lJourney Survival&e! To give you some insight on what type of server this is I'll quickly go through some helpful info! This is a survival server which focuses on vanilla gameplay mostly. The goal of the server is to build a place you enjoy and hopefully make some friends along the way. There is a economy and it's almost entirely player ran through player shops. \n\n You can also earn money and exp by ranking up. All ranks are designed around Minecraft's advancements and you have the option to prestige a unlimited amount of times. The PvP was reverted back to a 1.8 state, so swing delays don't exist. To get started use the wild warp villager at the spawn entrance and find a place you would like to start your journey. To claim your land simply run &6/chunk claim&e!"));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_WELCOME_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("hopperfilter")) {
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("     &2&l&nHow To Filter Hoppers Using Item Frames"));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 1&7: &ePlace a item frame on a hopper, any side works."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 2&7: &ePlace the item you want to filter in the item frame."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&eCongratulations! If you followed this guide your hopper should now only accept the items inside each item frame attached to the hopper."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("armorstand")) {
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("              &2&l&nHow To Adjust Armor Stands"));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 1&7: &ePlace a armor stand on the ground."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 2&7: &eSneak right-click the armor stand, that should bring up the menu."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&eCongratulations! If you followed this guide you should now be able to edit the armor stand using the menu options."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("claiming")) {
                    lines.add(Lang.COMMAND_HELP_CLAIMING_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("                &2&l&nHow To Claim Your Land"));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 1&7: &eStand on the chunk you want to claim."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&6Step 2&7: &eRun the command /chunk claim."));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&eCongratulations! If you followed this guide you should have claimed the chunk you're standing on."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_CLAIMING_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("store")) {
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&d&lStore: ").append(Lang.STORE.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.STORE.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("discord")) {
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&9&lDiscord: ").append(Lang.DISCORD.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.DISCORD.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("vanilla")) {
                    List<String> changes = new ArrayList<>();

                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC("&a&lWorld Difficulty: &eNormal"));
                    lines.add(Component.text(""));

                    changes.add("No swing delay with tools or weapons.");
                    changes.add("You can put any item on your head.");
                    changes.add("Item frames can be used to filter hoppers.");
                    changes.add("Armor stands can be edited by sneak-right-clicking them.");
                    changes.add("You can store exp by sneak-clicking glass bottles.");
                    changes.add("Trampling is disabled.");
                    changes.add("Llamas can be controlled when they have carpet on them.");
                    changes.add("Ender crystals will shoot phantoms.");
                    changes.add("Villagers will follow you if you're holding a emerald block.");
                    changes.add("Villagers can be leashed.");
                    changes.add("Villager clerics can farm nether wart.");
                    changes.add("Polar bears can be bread with salmon.");
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
                    changes.add("All protection enchantments can be put on the same armor.");
                    changes.add("Witches and some villagers have a chance of dropping their hat.");
                    changes.add("Dolphins can be controlled by right-clicking them, you can also use space bar to spit when riding a dolphin.");
                    changes.add("If you hold a compass a direction bar will appear on your screen.");
                    changes.add("Hoes will replant crops if you break them with one.");
                    changes.add("Rainbow sheep drop random color wool when sheared.");
                    changes.add("A wrench has been added as a recipe, it can rotate blocks with direction data.");
                    changes.add("Giants have a 5% chance to spawn with zombies in the resource world.");
                    changes.add("Illusioners have a 10% chance to spawn with pillagers in the resource world.");
                    changes.add("Drowned have a 50% chance of dropping a trident if they're holding one.");
                    changes.add("Ocelots are more common in jungles.");

                    for (String change : changes) {
                        lines.add(pu.formatC("&3" + number + "&b. &6" + change));
                        number++;
                    }
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("enchants")) {
                    lines.add(Lang.COMMAND_HELP_ENCHANTS_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(pu.formatC(" &dCustom enchants can be obtained from normal enchanting tables when you enchant a item at level 30. Custom enchants do work with books. Keep in mind you only have a small chance of receiving one. Hover over the enchantments down below for more details."));
                    lines.add(Component.text(""));

                    lines.add(pu.formatC("&31&b. &#964B00Logger").hoverEvent(pu.formatC("&5&lEnchantment:\n&#964B00Logger\n\n&e&lHow does it work:\n&7Breaking a log will break connected logs.\n\n&e&lSupported Items:\n&7Diamond Axe, Netherite Axe")));
                    lines.add(pu.formatC("&32&b. &#FCFF35Lightning Strike").hoverEvent(pu.formatC("&5&lEnchantment:\n&#FCFF35Lightning Strike\n\n&e&lHow does it work:\n&7Sneak-Left-Clicking will summon lightning.\n\n&e&lSupported Items:\n&7Diamond Sword, Netherite Sword")));
                    lines.add(pu.formatC("&33&b. &#DE0000Destroyer").hoverEvent(pu.formatC("&5&lEnchantment:\n&#DE0000Destroyer\n\n&e&lHow does it work:\n&7Breaking a block will break a 3x3 area of blocks.\n\n&e&lSupported Items:\n&7Diamond Pickaxe, Diamond Shovel, Netherite Pickaxe, Netherite Shovel\n\n&e&lSide Effects:\n&7Does not drop exp when a ore is broken.")));
                    lines.add(pu.formatC("&34&b. &#6A00E1Soul Bound").hoverEvent(pu.formatC("&5&lEnchantment:\n&#6A00E1Soul Bound\n\n&e&lHow does it work:\n&7Keep item on death.\n\n&e&lSupported Items:\n&7Elytra, Fishing Rod, Bow, Crossbow, Trident, Bundle, All Diamond Armor, All Diamond Tools, All Diamond Weapons, All Netherite Armor, All Netherite Tools, All Netherite Weapons")));
                    lines.add(pu.formatC("&35&b. &#FF00E4Soul Reaper").hoverEvent(pu.formatC("&5&lEnchantment:\n&#FF00E4Soul Reaper\n\n&e&lHow does it work:\n&7Capture and release mobs by sneak-right-clicking them.\n\n&e&lSupported Items:\n&7Diamond Hoe, Netherite Hoe")));
                    lines.add(pu.formatC("&36&b. &#FF9709Auto Sell").hoverEvent(pu.formatC("&5&lEnchantment:\n&#FF9709Auto Sell\n\n&e&lHow does it work:\n&7Sneak-Right-Clicking a chest, barrel or shulker box will sell the items inside.\n\n&e&lSupported Items:\n&7Diamond Hoe, Netherite Hoe")));

                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ENCHANTS_DIVIDER.getComponent(null));

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

                lines.add(pu.formatC("&e1&7. &2&lEssentials").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "essential" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help essentials")));
                lines.add(pu.formatC("&e2&7. &6&lShops").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "shop" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help")));
                lines.add(pu.formatC("&e3&7. &c&lLocker").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "locker" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")));
                lines.add(pu.formatC("&e4&7. &e&lChunks").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "chunk" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk help")));
                lines.add(pu.formatC("&e5&7. &b&lPets").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "pets" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pet help")));
                lines.add(pu.formatC("&e6&7. &d&lTrails").hoverEvent(Lang.COMMAND_HELP_PLUGIN_HOVER.getComponent(new String[] { "trails" })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trail help")));
                lines.add(pu.formatC("&e7&8. &#8D27FF&lEnchants").hoverEvent(Lang.COMMAND_HELP_ENCHANT_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help enchants")));
                lines.add(pu.formatC("&e8&7. &#ff843d&lVanilla").hoverEvent(Lang.COMMAND_HELP_PLUGIN_VANILLA_CHANGES_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help vanilla")));

                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_HELP_SPLITTER.getComponent(null));
                for (Component line : lines) player.sendMessage(line);
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
