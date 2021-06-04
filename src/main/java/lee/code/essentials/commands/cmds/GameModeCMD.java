package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModeCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {

                String gamemode = args[0].toLowerCase();

                switch (gamemode) {

                    case "survival":
                    case "0":
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Lang.NORMAL_WARNING.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Survival" }));
                        break;
                    case "creative":
                    case "1":
                        player.sendMessage(Lang.NORMAL_WARNING.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Creative" }));
                        player.setGameMode(GameMode.CREATIVE);
                        break;
                    case "adventure":
                    case "2":
                        player.sendMessage(Lang.NORMAL_WARNING.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Adventure" }));
                        player.setGameMode(GameMode.ADVENTURE);
                        break;
                    case "spectator":
                    case "3":
                        player.sendMessage(Lang.NORMAL_WARNING.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[] { "Spectator" }));
                        player.setGameMode(GameMode.SPECTATOR);
                        break;
                    default:
                        player.sendMessage(Lang.NORMAL_WARNING.getString(null) + Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getString(new String[]{ gamemode }));
                        break;
                }
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_GAMEMODE_ARGS.getString(null));
        }
        return true;
    }
}
