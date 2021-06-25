package lee.code.essentials.commands.cmds;

import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GlowCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {
            if (player.isGlowing()) {
                player.setGlowing(false);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GLOW_SUCCESSFUL.getComponent(new String[] { Lang.OFF.getString(null) })));
            } else if (!player.isGlowing()) {
                player.setGlowing(true);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GLOW_SUCCESSFUL.getComponent(new String[] { Lang.ON.getString(null) })));
            }
        }
        return true;
    }
}
