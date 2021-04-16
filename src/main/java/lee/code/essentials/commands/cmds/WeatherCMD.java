package lee.code.essentials.commands.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WeatherCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String weather = args[0].toLowerCase();

                switch (weather) {

                    case "clear":
                        player.getWorld().setClearWeatherDuration(18000);
                        break;

                    case "rain":
                        player.getWorld().setStorm(true);
                        break;

                    case "thunder":
                        player.getWorld().setThundering(true);
                        break;
                }
            }
        }
        return true;
    }
}
