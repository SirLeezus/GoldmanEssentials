package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RandomTeleportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                if (data.isRTPTaskActive(uuid) && !player.hasPermission("essentials.command.bypass")) {
                    long time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    long delay = data.getRTPTimer(uuid);
                    if (time < delay) {
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOM_TELEPORT_DELAY.getComponent(new String[] { BukkitUtils.parseSeconds(delay - time) })));
                    }
                } else if (data.isRandomTeleporting(uuid)) {
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOM_TELEPORT_ACTIVE.getComponent(null)));
                } else {
                    data.addRandomTeleporting(uuid);
                    plugin.getPU().rtpPlayer(player);
                }
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOM_WORLD_TYPE.getComponent(null)));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
