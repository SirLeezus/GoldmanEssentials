package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GlowCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
            UUID uuid = player.getUniqueId();
            SQLite SQL = plugin.getSqLite();

            if (player.hasPermission("essentials.command.glow")) {
                //glow
                if (player.isGlowing()) {
                    player.setGlowing(false);
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GLOW_SUCCESSFUL.getString(new String[] { Lang.OFF.getString(null) }));
                } else if (!player.isGlowing()) {
                    player.setGlowing(true);
                    player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_GLOW_SUCCESSFUL.getString(new String[] { Lang.ON.getString(null) }));
                }
            }
        }
        return true;
    }
}
