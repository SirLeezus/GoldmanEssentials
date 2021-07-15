package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class SummonCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String entityName = args[0].toLowerCase();
                if (plugin.getData().getEntityNames().contains(entityName)) {
                    int amount = 1;
                    if (args.length > 1) {
                        Scanner buyScanner = new Scanner(args[1]);
                        if (buyScanner.hasNextInt()) amount = Integer.parseInt(args[1]);
                        if (amount > 100) amount = 100;
                    }
                    for (int a = 0; a < amount; a++) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(entityName.toUpperCase()));
                }
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
