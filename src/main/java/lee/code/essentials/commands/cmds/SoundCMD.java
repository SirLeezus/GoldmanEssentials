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

        if (args.length > 1) {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    String sound = args[0].toLowerCase();
                    if (plugin.getData().getSoundNames().contains(sound)) {
                        target.playSound(target.getLocation(), Sound.valueOf(sound.toUpperCase()), 1, 1);
                        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SOUND_SUCCESSFUL.getComponent(new String[] { sound, target.getName() })));
                    }
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));

        } else if (args.length > 0) {
            if (sender instanceof Player player) {
                String sound = args[0].toLowerCase();
                if (plugin.getData().getSoundNames().contains(sound)) {
                    player.playSound(player.getLocation(), Sound.valueOf(sound.toUpperCase()), 1, 1);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SOUND_SUCCESSFUL.getComponent(new String[] { sound, player.getName() })));
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
