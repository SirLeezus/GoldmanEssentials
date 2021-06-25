package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WeatherCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {

            if (args.length > 0) {
                String weather = args[0].toLowerCase();
                World world = player.getWorld();

                switch (weather) {
                    case "clear" -> {
                        world.setStorm(false);
                        world.setThundering(false);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WEATHER_CLEAR.getComponent(null)));
                    }
                    case "rain" -> {
                        world.setStorm(true);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WEATHER_RAIN.getComponent(null)));
                    }
                    case "thunder" -> {
                        world.setStorm(true);
                        world.setThundering(true);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WEATHER_THUNDER.getComponent(null)));
                    }
                }
            }
        }
        return true;
    }
}
