package lee.code.essentials.commands.cmds;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
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
        Data data = plugin.getData();
        PU pu = plugin.getPU();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = pu.buildStringFromArgs(args, 0).replaceAll("[^a-zA-Z0-9 ]", "");
                int maxHomes = Settings.MAX_PLAYER_HOMES.getValue();
                int homesSaved = cache.getHomes(uuid).size();
                if (!name.equalsIgnoreCase("bed")) {
                    if (!name.isEmpty() && !cache.isAlreadyHome(uuid, name)) {
                        if (homesSaved < maxHomes) {
                            if (data.getWhitelistedWorlds().contains(player.getWorld().getName())) {
                                String homeLocation = pu.formatPlayerHomeLocation(name, player.getLocation());
                                cache.addHome(uuid, homeLocation);
                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SETHOME_SUCCESSFUL.getComponent(new String[] { name })));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_SUPPORTED_WORLD.getComponent(null)));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_MAX_HOMES.getComponent(new String[] { String.valueOf(maxHomes) })));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_ALREADY_SAVED.getComponent(new String[] { name })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SETHOME_BED.getComponent(null)));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
