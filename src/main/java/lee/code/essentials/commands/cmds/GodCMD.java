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

public class GodCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (cache.isGodPlayer(uuid)) {
                cache.setGodPlayer(uuid, false);
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GOD_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.OFF.getString(null) }));
            } else {
                cache.setGodPlayer(uuid, true);
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GOD_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.ON.getString(null) }));
            }
        }
        return true;
    }
}
