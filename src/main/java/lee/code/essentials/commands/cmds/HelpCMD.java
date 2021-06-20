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
import java.util.Arrays;
import java.util.List;

public class HelpCMD implements CommandExecutor {

    private final List<String> commands = Arrays.asList("ban", "tempban", "unban", "banlist", "mute", "unmute", "kick", "zap", "world", "weather", "vanish", "time", "teleportdeny", "teleportaccept", "teleport", "summon", "staffchat", "spawn", "sound", "home", "sethome", "deletehome", "setspawn", "setrank", "setprefix", "setcolor", "setrank", "reply", "rankup", "ranklist", "sell", "sellall", "worth", "money", "message", "itemname", "invsee", "help", "heal", "head", "god", "glow", "give", "gamemode", "flyspeed", "fly", "feed", "enchant", "balancetop", "balance", "advancement");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {

            if (args.length > 0 && args[0].equalsIgnoreCase("essentials")) {
                int number = 1;
                List<String> eLines = new ArrayList<>();

                eLines.add(Lang.COMMAND_HELP_ESSENTIALS_DIVIDER.getString(null));
                eLines.add(Lang.COMMAND_HELP_ESSENTIALS_TITLE.getString(null));
                eLines.add("");

                for (String pCommand : commands) {
                    Command sCommand = Bukkit.getCommandMap().getCommand(pCommand);
                    if (sCommand != null) {
                        String permission = sCommand.getPermission();
                        if (permission != null) {
                            if (player.hasPermission(permission)) {
                                eLines.add(Lang.COMMAND_HELP_ESSENTIALS.getString(new String[] { String.valueOf(number), sCommand.getUsage(), sCommand.getDescription() }));
                                number++;
                            }
                        }
                    }
                }
                eLines.add("");
                eLines.add(Lang.COMMAND_HELP_ESSENTIALS_DIVIDER.getString(null));
                for (String message : eLines) player.sendMessage(message);

            } else {
                List<Component> lines = new ArrayList<>();
                lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
                lines.add(Component.text(""));
                lines.add(plugin.getPU().formatC("&e1&7. &2&lEssentials").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "essential" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help essentials")).append(Component.text("      ")).append(plugin.getPU().formatC("&e2&7. &6&lShops").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "shop" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help"))));
                lines.add(plugin.getPU().formatC("&e3&7. &c&lLocker    ").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "locker" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")).append(Component.text("      ")).append(plugin.getPU().formatC("&e4&7. &e&lChunks").hoverEvent(plugin.getPU().formatC(Lang.COMMAND_HELP_PLUGIN_HOVER.getString(new String[] { "chunk" }))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk help"))));
                lines.add(Component.text(""));
                lines.add(Lang.COMMAND_HELP_SPLITTER.getComponent(null));
                for (Component line : lines) player.sendMessage(line);
            }
        }
        return true;
    }
}
