package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.lists.Lang;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essentials.command.spawn")) {
                player.teleport(plugin.getPU().unFormatPlayerLocation(SQL.getSpawn()));

                TextComponent message = new TextComponent(Lang.TELEPORT.getString(null));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1,1);
            }
        }
        return true;
    }
}
