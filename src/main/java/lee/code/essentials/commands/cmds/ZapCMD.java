package lee.code.essentials.commands.cmds;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ZapCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            Block block = player.getTargetBlock(120);
            if (block != null) {
                Location location = block.getLocation();
                location.getWorld().strikeLightning(location);
            }
        }
        return true;
    }
}
