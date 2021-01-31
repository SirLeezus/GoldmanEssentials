package lee.code.essentials.commands.cmds;

import lee.code.essentials.files.defaults.Lang;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.gamemode")) {

                if (args.length > 0) {

                    String gamemode = args[0].toLowerCase();

                    switch (gamemode) {
                        case "survival":
                        case "0":
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getConfigValue(new String[] { "Survival" }));
                            break;
                        case "creative":
                        case "1":
                            player.sendMessage(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getConfigValue(new String[] { "Creative" }));
                            player.setGameMode(GameMode.CREATIVE);
                            break;
                        case "adventure":
                        case "2":
                            player.sendMessage(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getConfigValue(new String[] { "Adventure" }));
                            player.setGameMode(GameMode.ADVENTURE);
                            break;
                        case "spectator":
                        case "3":
                            player.sendMessage(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getConfigValue(new String[] { "Spectator" }));
                            player.setGameMode(GameMode.SPECTATOR);
                            break;
                        default:
                            player.sendMessage(Lang.ERROR_COMMAND_GAMEMODE_NOT_FOUND.getConfigValue(new String[] { args[0] }));
                            break;
                    }
                } else player.sendMessage(Lang.ERROR_COMMAND_GAMEMODE_ARGS.getConfigValue(null));
            }
        }
        return true;
    }
}
