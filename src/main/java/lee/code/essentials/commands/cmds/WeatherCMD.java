package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
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
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_WEATHER_CLEAR.getString(null));
                        break;

                    case "rain":
                        player.getWorld().setStorm(true);
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_WEATHER_RAIN.getString(null));
                        break;

                    case "thunder":
                        player.getWorld().setThundering(true);
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_WEATHER_THUNDER.getString(null));
                        break;
                }
            }
        }
        return true;
    }
}
