package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {
            GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

            if (args.length > 0) {

                String worldString = args[0].toLowerCase();

                if (plugin.getData().getWorldNames().contains(worldString)) {
                    World world = Bukkit.getWorld(worldString);
                    Location loc = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getY());
                    player.teleportAsync(loc);
                    player.sendActionBar(Lang.TELEPORT.getComponent(null));
                    player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1,1);
                } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_WORLD_NOT_FOUND.getString(new String[] { worldString }));
            } else player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_COMMAND_WORLD_ARGS.getString(null));
        }
        return true;
    }
}
