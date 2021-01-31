package lee.code.essentials.commands.cmds;

import lee.code.essentials.files.defaults.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.fly")) {

                if (!player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getConfigValue(new String[] { Lang.ON.getConfigValue(null) }));
                } else {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getConfigValue(new String[] { Lang.OFF.getConfigValue(null) }));
                }
            }
        }
        return true;
    }
}
