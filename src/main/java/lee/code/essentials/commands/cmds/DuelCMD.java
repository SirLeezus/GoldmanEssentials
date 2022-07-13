package lee.code.essentials.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.managers.CountdownTimer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.time.Duration;
import java.util.UUID;

public class DuelCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (args.length > 0) {
                if (BukkitUtils.getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        UUID tUUID = target.getUniqueId();
                        if (!uuid.equals(tUUID)) {
                            if (!data.isDuelCurrent(tUUID)) {
                                if (!data.isDuelCurrent(uuid)) {
                                    if (args.length > 1) {
                                        if (data.isDuelRequestingPlayer(tUUID, uuid)) {
                                            if (args[1].equalsIgnoreCase("accept")) {
                                                data.removeDuelRequesting(tUUID);
                                                player.teleport(target.getLocation());
                                                Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1000), Duration.ofMillis(100));
                                                CountdownTimer timer = new CountdownTimer(plugin,
                                                        10,
                                                        () -> {
                                                            if (player.isOnline() && target.isOnline()) {
                                                                player.showTitle(Title.title(Lang.DUEL_TIMER_START_TITLE.getComponent(null), Component.text(""), times));
                                                                target.showTitle(Title.title(Lang.DUEL_TIMER_START_TITLE.getComponent(null), Component.text(""), times));
                                                            }
                                                        },
                                                        () -> {
                                                            if (player.isOnline() && target.isOnline()) {
                                                                data.setDuelingPlayer(tUUID, uuid);
                                                                player.showTitle(Title.title(Lang.DUEL_TIMER_END_TITLE.getComponent(null), Component.text(""), times));
                                                                target.showTitle(Title.title(Lang.DUEL_TIMER_END_TITLE.getComponent(null), Component.text(""), times));
                                                                playPingSound(player, 2);
                                                                playPingSound(target, 2);
                                                            }
                                                        },
                                                        (t) ->  {
                                                            if (player.isOnline() && target.isOnline()) {
                                                                player.showTitle(Title.title(Lang.DUEL_TIMER_TITLE.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) }), Component.text(""), times));
                                                                target.showTitle(Title.title(Lang.DUEL_TIMER_TITLE.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) }), Component.text(""), times));
                                                                playPingSound(player, 1);
                                                                playPingSound(target, 1);
                                                            } else t.stop();
                                                        });
                                                timer.scheduleTimer();
                                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_ACCEPT_SUCCESSFUL.getComponent(new String[] { target.getName() })));
                                                target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_ACCEPT_TARGET_SUCCESSFUL.getComponent(new String[] { player.getName() })));
                                            } else if (args[1].equalsIgnoreCase("deny")) {
                                                data.removeDuelRequesting(tUUID);
                                                target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_DENY_SENDER.getComponent(new String[] { player.getName() } )));
                                                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_DENY_RECEIVER.getComponent(new String[] { target.getName() } )));
                                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_NOT_ARG.getComponent(new String[] { args[1] })));
                                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_NO_PENDING_REQUEST.getComponent(new String[] { target.getName() })));
                                    } else {
                                        if (!data.isDuelRequestingPlayer(uuid, tUUID)) {
                                            data.setDuelRequesting(uuid, tUUID);
                                            pu.pvpRequestTimeoutTimer(player, target);

                                            Component targetMessage = Lang.REQUEST_DUEL_TARGET.getComponent(new String[] { player.getName() });
                                            Component accept = Component.text("            ").append(Lang.REQUEST_ACCEPT.getComponent(null).hoverEvent(Lang.REQUEST_DUEL_ACCEPT_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/duel " + player.getName() + " accept")));
                                            Component deny = Component.text("        ").append(Lang.REQUEST_DENY.getComponent(null).hoverEvent(Lang.REQUEST_DUEL_DENY_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/duel " + player.getName() + " deny")));

                                            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_DUEL_REQUEST_SUCCESSFUL.getComponent(new String[] { target.getName() })));
                                            target.sendMessage(targetMessage.append(accept).append(deny));
                                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_REQUEST_ALREADY_SENT.getComponent(new String[] { target.getName() })));
                                    }
                                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_REQUEST_YOUR_IN_DUEL.getComponent(null)));
                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_REQUEST_IN_DUEL.getComponent(new String[]{ target.getName() })));
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_DUEL_DUEL_SELF.getComponent(null)));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
            } else sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }

    private void playPingSound(Player player, int pitch) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, (float) 0.5, (float) pitch);
    }
}
