package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class GiveCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            int amount = 1;

            if (args.length > 1) {
                if (plugin.getPU().getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        String mat = args[1].toLowerCase();
                        if (plugin.getData().getMaterialNames().contains(mat)) {
                            Material material = Material.valueOf(mat.toUpperCase());
                            if (args.length > 2) {
                                Scanner amountScanner = new Scanner(args[2]);
                                if (amountScanner.hasNextInt()) amount = Integer.parseInt(args[2]);
                            }
                            ItemStack item = new ItemStack(material, amount);
                            target.getInventory().addItem(item);
                        }
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { args[0] }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getString(new String[] { args[0] }));
            }
        }
        return true;
    }
}
