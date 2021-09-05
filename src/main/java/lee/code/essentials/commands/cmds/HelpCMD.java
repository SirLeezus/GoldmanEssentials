package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
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
                                    lines.add(Lang.COMMAND_HELP_ESSENTIALS.getComponent(new String[] { String.valueOf(number), sCommand.getUsage(), sCommand.getDescription() }));
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
                    lines.add(plugin.getPU().formatC(" &eWelcome to &2&lJourney Survival&e! To give you some insight on what type of server this is I'll quickly go through some helpful info! This is a survival server which focuses on vanilla gameplay mostly. The goal of the server is to build a place you enjoy and hopefully make some friends along the way. There is a economy and it's almost entirely player ran through player shops. \n\n You can also earn money and exp by ranking up. All ranks are designed around Minecraft's advancements and you have the option to prestige a unlimited amount of times. The PvP was reverted back to a 1.7 state, so swing delays don't exist. To get started use the wild warp villager at the spawn entrance and find a place you would like to start your journey. To claim your land simply run /chunk claim!"));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_WELCOME_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("hopperfilter")) {
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("     &2&l&nHow To Filter Hoppers Using Item Frames"));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&6Step 1&7: &ePlace a item frame on a hopper, any side works."));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&6Step 2&7: &ePlace the item you want to filter in the item frame."));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&eCongratulations! If you followed this guide your hopper should now only accept the items inside each item frame attached to the hopper."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_HOPPER_FILTER_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("armorstand")) {
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("              &2&l&nHow To Adjust Armor Stands"));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&6Step 1&7: &ePlace a armor stand on the ground."));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&6Step 2&7: &eSneak right-click the armor stand, that should bring up the menu."));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&eCongratulations! If you followed this guide you should now be able to edit the armor stand using the menu options."));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_ARMOR_STAND_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("store")) {
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&d&lStore: ").append(Lang.STORE.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.STORE.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_STORE_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("discord")) {
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&9&lDiscord: ").append(Lang.DISCORD.getComponent(null).color(NamedTextColor.YELLOW).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.DISCORD.getString()))));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_DISCORD_DIVIDER.getComponent(null));

                } else if (args[0].equalsIgnoreCase("vanilla")) {
                    List<String> changes = new ArrayList<>();

                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_TITLE.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC("&a&lWorld Difficulty: &eNormal"));
                    lines.add(Component.text(""));

                    changes.add("No swing delay with tools or weapons.");
                    changes.add("Item frames can be used to filter hoppers.");
                    changes.add("Armor stands can be edited by sneak-clicking them.");
                    changes.add("Trampling is disabled.");
                    changes.add("Llama can be controlled when riding.");
                    changes.add("Ender crystals will shoot phantoms.");
                    changes.add("Villagers will follow you if you're holding a emerald block.");
                    changes.add("Villagers can be leashed.");
                    changes.add("Villager clerics can farm nether wart.");
                    changes.add("Polar bears can be bread with salmon.");
                    changes.add("Chests can be opened with any block above it.");
                    changes.add("The more people sleeping the faster the night will be.");
                    changes.add("Anvils, signs and books support hex and normal color codes.");
                    changes.add("Infinity on bows and crossbows works with all arrows.");
                    changes.add("Phantoms do not target you if you're holding a torch.");
                    changes.add("You can feed a cow mushrooms to turn it into a mooshroom cow.");
                    changes.add("A creeper's health impacts the explosion power.");
                    changes.add("Ender dragon always drops full exp and a skull when killed.");
                    changes.add("Enderman ignore players who are wearing a dragon head.");
                    changes.add("Feeding a white/orange tulip to a fox changes the type to snow/regular.");
                    changes.add("You can breed parrots by feeding them any type of seeds.");
                    changes.add("The killer rabbit has a chance of spawning.");
                    changes.add("Shulkers have a chance to spawn random colored shulkers with their bullets on impact.");
                    changes.add("Milk cures rabid wolves.");
                    changes.add("Mending always repairs the most damaged equipment first.");
                    changes.add("You only need a totem in your inventory for it to work.");
                    changes.add("Entities cannot use portals.");
                    changes.add("Milk cures bad omen.");
                    changes.add("Ender pearls don't damage players when used.");
                    changes.add("Minecarts can be placed on anything and controlled.");
                    changes.add("Silk touch works on spawners.");
                    changes.add("Entities can spawn on packed ice.");
                    changes.add("You can break individual slabs when sneaking.");
                    changes.add("You can deactivate a spawner using redstone.");
                    changes.add("A max of 25 entities of each type are allowed in one chunk.");
                    changes.add("The stonecutter block will damage you if you walk on it.");
                    changes.add("You start with all recipes unlocked.");
                    changes.add("All entities have a chance of dropping a head.");

                    for (String change : changes) {
                        lines.add(plugin.getPU().formatC("&3" + number + "&b. &6" + change));
                        number++;
                    }
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_VANILLA_CHANGES_DIVIDER.getComponent(null));
                }
                for (Component message : lines) player.sendMessage(message);
            } else {

                lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
                lines.add(Component.text(""));
                lines.add(plugin.getPU().formatC("&e1&7. &2&lEssentials").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "essential" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help essentials")).append(Component.text("      ")).append(plugin.getPU().formatC("&e2&7. &6&lShops").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "shop" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help"))));
                lines.add(plugin.getPU().formatC("&e3&7. &c&lLocker    ").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "locker" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")).append(Component.text("      ")).append(plugin.getPU().formatC("&e4&7. &e&lChunks").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "chunk" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk help"))));
                lines.add(plugin.getPU().formatC("&e5&7. &b&lPets    ").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "pets" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pet help")).append(Component.text("          ")).append(plugin.getPU().formatC("&e6&7. &d&lTrails").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "trails" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trail help"))));
                lines.add(plugin.getPU().formatC("&e7&7. &#ff843d&lVanilla").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_VANILLA_CHANGES_HOVER.getString(null))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help vanilla")));
                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_HELP_SPLITTER.getComponent(null));
                for (Component line : lines) player.sendMessage(line);
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
