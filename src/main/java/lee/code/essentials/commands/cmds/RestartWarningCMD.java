package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.managers.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RestartWarningCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        CountdownTimer timer = new CountdownTimer(plugin,
                30,
                () -> Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_START.getComponent(null))),
                () -> {
                    Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.AUTO_RESTART_WARNING_END.getComponent(null)));
                    Bukkit.getServer().shutdown();
                },
                (t) -> Bukkit.getServer().sendActionBar(Lang.AUTO_RESTART_TIME.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) })));
        timer.scheduleTimer();

        return true;
    }
}
