package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (pu.containOnlyNumbers(args[0])) {
                    int number = Integer.parseInt(args[0]);
                    float speed = (float) 0;
                    if (number <= 10) {
                        switch (number) {
                            case 1 -> speed = (float) 0.1;
                            case 2 -> speed = (float) 0.2;
                            case 3 -> speed = (float) 0.3;
                            case 4 -> speed = (float) 0.4;
                            case 5 -> speed = (float) 0.5;
                            case 6 -> speed = (float) 0.6;
                            case 7 -> speed = (float) 0.7;
                            case 8 -> speed = (float) 0.8;
                            case 9 -> speed = (float) 0.9;
                            case 10 -> speed = (float) 1;
                        }
                        player.setFlySpeed(speed);
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLYSPEED_SUCCESSFUL.getComponent(new String[]{String.valueOf(number)})));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_FLYSPEED_LIMIT.getComponent(null)));
                }
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
