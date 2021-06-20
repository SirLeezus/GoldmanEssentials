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

        if (sender instanceof Player player) {
            if (args.length > 0) {
                String gamemode = args[0].toLowerCase();

                switch (gamemode) {
                    case "survival", "0" -> {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[]{"Survival"}));
                    }
                    case "creative", "1" -> {
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[]{"Creative"}));
                        player.setGameMode(GameMode.CREATIVE);
                    }
                    case "adventure", "2" -> {
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[]{"Adventure"}));
                        player.setGameMode(GameMode.ADVENTURE);
                    }
                    case "spectator", "3" -> {
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_GAMEMODE_SUCCESSFUL.getString(new String[]{"Spectator"}));
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                    default -> player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.ERROR_COMMAND_WRONG_COMMAND_ARG.getString(new String[]{gamemode}));
                }
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_GAMEMODE_ARGS.getString(null));
        }
        return true;
    }
}
