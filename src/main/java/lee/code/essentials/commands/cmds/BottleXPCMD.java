package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BottleXPCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.getType().equals(Material.GLASS_BOTTLE)) {
                int amount = handItem.getAmount();
                int exp = BukkitUtils.getPlayerExp(player);
                int requiredEXP = 8;
                if (exp > requiredEXP) {
                    int convertEXPBottleAmount = exp / requiredEXP;
                    if (convertEXPBottleAmount > amount) convertEXPBottleAmount = amount;

                    int glassBottleAmount = amount - convertEXPBottleAmount;

                    if (glassBottleAmount > 0) player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE, glassBottleAmount));
                    else player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

                    ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
                    int space = BukkitUtils.getFreeSpace(player, expBottle);
                    expBottle.setAmount(convertEXPBottleAmount);

                    if (space > convertEXPBottleAmount) player.getInventory().addItem(expBottle);
                    else player.getLocation().getWorld().dropItemNaturally(player.getLocation(), expBottle);

                    player.setExp(0);
                    player.setLevel(0);
                    player.giveExp(exp - requiredEXP * convertEXPBottleAmount);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_EXP_BOTTLE_POINTS.getComponent(null)));
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_EXP_BOTTLE_NO_BOTTLE.getComponent(null)));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
