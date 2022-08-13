package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MessageCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (args.length > 1) {
                if (BukkitUtils.getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target != player) {

                        UUID targetUUID = target.getUniqueId();
                        if (data.isAFK(targetUUID)) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.AFK.getComponent(new String[]{ target.getName() })));
                        Component message = Component.text(BukkitUtils.buildStringFromArgs(args, 1));
                        data.setLastReplier(targetUUID, uuid);
                        data.setLastReplier(uuid, targetUUID);

                        player.sendMessage(Lang.MESSAGE_SENT.getComponent(new String[] { target.getName() })
                                .append(pu.parseVariables(player, message.color(TextColor.color(0, 220, 234)))));

                        target.sendMessage(Lang.MESSAGE_RECEIVED.getComponent(new String[] { player.getName() })
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " "))
                                .append(pu.parseVariables(player, message.color(TextColor.color(0, 220, 234)))));

                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_MESSAGE_TO_SELF.getComponent(null)));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
