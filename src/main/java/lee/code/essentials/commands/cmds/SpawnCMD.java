package lee.code.essentials.commands.cmds;

import lee.code.essentials.files.defaults.Config;
import lee.code.essentials.files.defaults.DoubleValues;
import lee.code.essentials.files.defaults.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.spawn")) {

                World world = Bukkit.getWorld(Config.SERVER_SPAWN_WORLD.getConfigValue(null));
                double x = DoubleValues.SERVER_SPAWN_X.getConfigValue();
                double y = DoubleValues.SERVER_SPAWN_Y.getConfigValue();
                double z = DoubleValues.SERVER_SPAWN_Z.getConfigValue();
                float pitch = (float) DoubleValues.SERVER_SPAWN_PITCH.getConfigValue();
                float yaw = (float) DoubleValues.SERVER_SPAWN_YAW.getConfigValue();

                Location location = new Location(world, x, y, z, yaw, pitch);
                player.teleport(location);
                player.sendMessage(Lang.COMMAND_SPAWN_SUCCESSFUL.getConfigValue(null));
            }
        }
        return true;
    }
}
