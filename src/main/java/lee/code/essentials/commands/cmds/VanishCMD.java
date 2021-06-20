package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class VanishCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (cache.isVanishPlayer(uuid)) {
                cache.setVanishPlayer(uuid, false);
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(true);
                player.setFlying(true);
                for (Player oPlayer : Bukkit.getOnlinePlayers()) if (!plugin.getData().getVanishedPlayers().contains(oPlayer.getUniqueId())) oPlayer.showPlayer(plugin, player);
                for (UUID vUUID : plugin.getData().getVanishedPlayers()) {
                    OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(vUUID);
                    if (oPlayer.isOnline()) {
                        Player vPlayer = oPlayer.getPlayer();
                        if (vPlayer != null) player.hidePlayer(plugin, vPlayer);
                    }
                }
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_VANISH_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.OFF.getString(null) }));
            } else {
                cache.setVanishPlayer(uuid, true);
                player.setGameMode(GameMode.SPECTATOR);
                for (Player oPlayer : Bukkit.getOnlinePlayers()) if (!plugin.getData().getVanishedPlayers().contains(oPlayer.getUniqueId())) oPlayer.hidePlayer(plugin, player);
                for (UUID vUUID : plugin.getData().getVanishedPlayers()) {
                    OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(vUUID);
                    if (oPlayer.isOnline()) {
                        Player vPlayer = oPlayer.getPlayer();
                        if (vPlayer != null) player.showPlayer(plugin, vPlayer);
                    }
                }
                player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_VANISH_TOGGLE_SUCCESSFUL.getString(new String[] { Lang.ON.getString(null) }));
            }
        }
        return true;
    }
}
