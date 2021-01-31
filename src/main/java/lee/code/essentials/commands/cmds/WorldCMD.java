package lee.code.essentials.commands.cmds;

import lee.code.essentials.files.defaults.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.world")) {

                if (args.length > 0) {

                    String world = args[0].toLowerCase();

                    for (World selectedWorld : Bukkit.getWorlds()) {
                        if (world.equals(selectedWorld.getName().toLowerCase())) {
                            Location loc = new Location(selectedWorld, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getY());
                            player.teleport(loc);
                            player.sendMessage(Lang.COMMAND_WORLD_SUCCESSFUL.getConfigValue(new String[] { world }));
                            return true;
                        }
                    }

                    player.sendMessage(Lang.ERROR_COMMAND_WORLD_NOT_FOUND.getConfigValue(new String[] { world }));
                } else player.sendMessage(Lang.ERROR_COMMAND_WORLD_ARGS.getConfigValue(null));
            }
        }
        return true;
    }
}
