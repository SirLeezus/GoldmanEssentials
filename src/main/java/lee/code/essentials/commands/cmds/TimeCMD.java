package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String time = args[0];

                World world = player.getWorld();
                String worldName = player.getWorld().getName();

                switch (time) {
                    case "day" -> {
                        world.setTime(1000);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TIME_SUCCESSFUL.getComponent( new String[] { worldName, plugin.getPU().formatTime(world.getTime()) })));
                    }
                    case "noon" -> {
                        world.setTime(6000);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TIME_SUCCESSFUL.getComponent( new String[] { worldName, plugin.getPU().formatTime(world.getTime()) })));
                    }
                    case "midnight" -> {
                        world.setTime(18000);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TIME_SUCCESSFUL.getComponent( new String[] { worldName, plugin.getPU().formatTime(world.getTime()) })));
                    }
                    case "night" -> {
                        player.getWorld().setTime(13000);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TIME_SUCCESSFUL.getComponent( new String[] { worldName, plugin.getPU().formatTime(world.getTime()) })));
                    }
                    default -> player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TIME.getComponent(new String[] { time })));
                }
            }
        }
        return true;
    }
}
