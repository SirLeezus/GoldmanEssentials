package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isFlying()) {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.ON.getString(null) }));
            } else {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.OFF.getString(null) }));
            }
        }
        return true;
    }
}
