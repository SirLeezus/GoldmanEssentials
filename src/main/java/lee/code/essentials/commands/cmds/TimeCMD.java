package lee.code.essentials.commands.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String time = args[0];

                switch (time) {

                    case "day":
                        player.getWorld().setTime(1000);
                        break;

                    case "noon":
                        player.getWorld().setTime(6000);
                        break;

                    case "midnight":
                        player.getWorld().setTime(18000);
                        break;

                    case "night":
                        player.getWorld().setTime(13000);
                        break;

                    default:

                }
            }
        }
        return true;
    }
}
