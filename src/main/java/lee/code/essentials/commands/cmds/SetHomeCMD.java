package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.lists.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SetHomeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = plugin.getPU().buildStringFromArgs(args, 0).replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("bed", "");
                int maxHomes = Settings.MAX_PLAYER_HOMES.getValue();
                int homesSaved = cache.getHomes(uuid).size();
                if (!name.isEmpty() && !cache.isAlreadyHome(uuid, name)) {
                    if (homesSaved < maxHomes) {
                        String homeLocation = plugin.getPU().formatPlayerHomeLocation(name, player.getLocation());
                        cache.addHome(uuid, homeLocation);
                        player.sendMessage(Lang.NORMAL_ALERT.getString(null) + Lang.COMMAND_SETHOME_SUCCESSFUL.getString(new String[] { name }));
                    } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_MAX_HOMES.getString(new String[] { String.valueOf(maxHomes) }));
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_HOME_ALREADY_SAVED.getString(new String[] { name }));
            }
        }
        return true;
    }
}
