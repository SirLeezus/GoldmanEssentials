package lee.code.essentials.commands.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SummonCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            EnderDragon entity = (EnderDragon) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_DRAGON);
            entity.setCustomNameVisible(true);
            entity.setCustomName("test");
        }
        return true;
    }
}
