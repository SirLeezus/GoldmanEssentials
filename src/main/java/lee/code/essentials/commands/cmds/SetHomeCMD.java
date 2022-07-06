package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.database.CacheManager;
import lee.code.essentials.lists.Lang;
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
        CacheManager cacheManager = plugin.getCacheManager();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 0) {
                String name = BukkitUtils.buildStringFromArgs(args, 0).replaceAll("[^a-zA-Z0-9 ]", "");
                int maxHomes = pu.getMaxHomes(player);
                int homesSaved = cacheManager.getHomes(uuid).size();
                if (!name.equalsIgnoreCase("bed")) {
                    if (!name.isEmpty() && !cacheManager.isAlreadyHome(uuid, name)) {
                        if (homesSaved <= maxHomes) {
                            if (data.getWhitelistedWorlds().contains(player.getWorld().getName())) {
                                String homeLocation = pu.serializeHomeLocation(name, player.getLocation());
                                cacheManager.addHome(uuid, homeLocation);
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
