package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (args.length > 1) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[1]);
            if (targetPlayer != null) {
                if (targetPlayer.isOnline()) {
                    Player target = targetPlayer.getPlayer();
                    if (target != null) {
                        String type = args[0];
                        if (plugin.getData().getEntityNames().contains(type)) {
                            ItemStack spawner = plugin.getPU().createSpawner(EntityType.valueOf(type.toUpperCase()));
                            target.getInventory().addItem(spawner);
                        }
                    } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[1] })));
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[1] })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[1] })));

        } else if (args.length > 0) {
            if (sender instanceof Player player) {
                String type = args[0];
                if (plugin.getData().getEntityNames().contains(type)) {
                    ItemStack spawner = plugin.getPU().createSpawner(EntityType.valueOf(type.toUpperCase()));
                    player.getInventory().addItem(spawner);
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
