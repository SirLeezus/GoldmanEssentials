package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SoundCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        if (sender instanceof Player player) {
            if (args.length > 1) {
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        String sound = args[1].toLowerCase();
                        if (plugin.getData().getSoundNames().contains(sound)) {
                            target.playSound(target.getLocation(), Sound.valueOf(sound.toUpperCase()), 1, 1);
                            player.sendMessage(Lang.PREFIX.getString(null) + Lang.COMMAND_SOUND_SUCCESSFUL.getString(new String[] { sound, target.getName() }));
                        }
                    }
                }
            }
        }
        return true;
    }
}
