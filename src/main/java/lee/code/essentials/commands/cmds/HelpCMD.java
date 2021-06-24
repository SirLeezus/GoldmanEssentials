package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("essentials")) {
                    int number = 1;

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
                    for (Component message : lines) player.sendMessage(message);

                } else if (args[0].equalsIgnoreCase("welcome")) {

                    lines.add(Lang.COMMAND_HELP_WELCOME_DIVIDER.getComponent(null));
                    lines.add(Component.text(""));
                    lines.add(plugin.getPU().formatC(" &eWelcome to &2&lJourney Survival&e! To give you some insight on what type of server this is I'll quickly go through some helpful info! This is a survival server which focuses on vanilla gameplay mostly. The goal of the server is to build a place you enjoy and hopefully make some friends along the way. There is a economy and it's almost entirely player ran through player shops. \n\n You can also earn money and exp by ranking up. All ranks are designed around Minecraft's advancements and you have the option to prestige a unlimited amount of times. The PvP was reverted back to a 1.7 state, so swing delays don't exist. To get started use the wild warp villager at the spawn entrance and find a place you would like to start your journey. To claim your land simply run /chunk claim!"));
                    lines.add(Component.text(""));
                    lines.add(Lang.COMMAND_HELP_WELCOME_DIVIDER.getComponent(null));
                    for (Component message : lines) player.sendMessage(message);

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
                    for (Component message : lines) player.sendMessage(message);

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
                    for (Component message : lines) player.sendMessage(message);
                }
            } else {

                lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
                lines.add(Component.text(""));
                lines.add(plugin.getPU().formatC("&e1&7. &2&lEssentials").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "essential" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help essentials")).append(Component.text("      ")).append(plugin.getPU().formatC("&e2&7. &6&lShops").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "shop" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help"))));
                lines.add(plugin.getPU().formatC("&e3&7. &c&lLocker    ").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "locker" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")).append(Component.text("      ")).append(plugin.getPU().formatC("&e4&7. &e&lChunks").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "chunk" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk help"))));
                lines.add(plugin.getPU().formatC("&e5&7. &b&lPets    ").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "pets" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pet help")).append(Component.text("          ")).append(plugin.getPU().formatC("&e6&7. &d&lTrails").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "trails" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trail help"))));
                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_HELP_SPLITTER.getComponent(null));
                for (Component line : lines) player.sendMessage(line);
            }
        }
        return true;
    }
}
