package lee.code.essentials.commands.cmds;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.files.defaults.Lang;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TheEssentials plugin = TheEssentials.getPlugin();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.setspawn")) {

                FileConfiguration file = plugin.getFile("config").getData();

                Location location = player.getLocation();

                file.set("spawn.world", location.getWorld().getName());
                file.set("spawn.x", location.getX());
                file.set("spawn.y", location.getY());
                file.set("spawn.z", location.getZ());
                file.set("spawn.pitch", location.getPitch());
                file.set("spawn.yaw", location.getYaw());
                plugin.saveFile("config");

                player.sendMessage(Lang.COMMAND_SETSPAWN_SUCCESSFUL.getConfigValue(null));
            }
        }
        return true;
    }
}
