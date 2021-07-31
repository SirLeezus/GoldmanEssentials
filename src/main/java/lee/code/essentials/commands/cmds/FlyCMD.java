package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (!player.isFlying()) {
                player.setAllowFlight(true);
                player.setFlying(true);
                cache.setFlying(uuid, true);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.ON.getString(null) })));
            } else {
                player.setAllowFlight(false);
                player.setFlying(false);
                cache.setFlying(uuid, false);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLY_TOGGLE_SUCCESSFUL.getComponent(new String[] { Lang.OFF.getString(null) })));
            }
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
