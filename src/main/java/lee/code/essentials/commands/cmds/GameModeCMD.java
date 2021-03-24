package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
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
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Survival" }));
                            player.updateInventory();
                            break;
                        case "creative":
                        case "1":
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Creative" }));
                            player.setGameMode(GameMode.CREATIVE);
                            player.updateInventory();
                            break;
                        case "adventure":
                        case "2":
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Adventure" }));
                            player.setGameMode(GameMode.ADVENTURE);
                            player.updateInventory();
                            break;
                        case "spectator":
                        case "3":
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Spectator" }));
                            player.setGameMode(GameMode.SPECTATOR);
                            player.updateInventory();
                            break;
                        default:
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getString(new String[]{ gamemode }));
                            break;
                    }
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_GAMEMODE_ARGS.getString(null));
            }
        }
        return true;
    }
}
