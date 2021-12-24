package lee.code.essentials.commands.cmds;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.menus.PlayerTradeMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TradeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Data data = plugin.getData();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            if (args.length > 0) {
                if (pu.getOnlinePlayers().contains(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        UUID tUUID = target.getUniqueId();
                        if (!uuid.equals(tUUID)) {
                            if (args.length > 1) {
                                if (data.isTradeRequestingPlayer(tUUID, uuid)) {
                                    if (args[1].equalsIgnoreCase("accept")) {
                                        if (!data.getPlayerMU(uuid).isOwnerTrading() && !data.getPlayerMU(uuid).isTraderTrading()) {
                                            if (!data.getPlayerMU(tUUID).isOwnerTrading() && !data.getPlayerMU(tUUID).isTraderTrading()) {
                                                data.removeTradeRequesting(tUUID);
                                                new PlayerTradeMenu(data.getPlayerMU(tUUID), uuid).open();
                                                player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
                                                target.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1, 1);
                                            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_TARGET_IS_ALREADY_TRADING.getComponent(new String[] { target.getName() })));
                                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_IS_ALREADY_TRADING.getComponent(null)));
                                    } else if (args[1].equalsIgnoreCase("deny")) {
                                        data.removeTradeRequesting(tUUID);
                                        target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TRADE_DENY_SENDER.getComponent(new String[] { player.getName() } )));
                                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TRADE_DENY_RECEIVER.getComponent(new String[] { target.getName() } )));
                                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_NOT_ARG.getComponent(new String[] { args[1] })));
                                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_NO_PENDING_REQUEST.getComponent(new String[] { target.getName() })));
                            } else {
                                if (!data.getPlayerMU(uuid).isOwnerTrading()) {
                                    if (!data.isTradeRequestingPlayer(uuid, tUUID)) {
                                        data.setTradeRequesting(uuid, tUUID);
                                        pu.tradeTimer(player, target);

                                        Component targetMessage = Lang.REQUEST_TRADE_TARGET.getComponent(new String[] { player.getName() });
                                        Component accept = Component.text("            ").append(Lang.REQUEST_ACCEPT.getComponent(null).hoverEvent(Lang.REQUEST_TRADE_ACCEPT_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trade " + player.getName() + " accept")));
                                        Component deny = Component.text("        ").append(Lang.REQUEST_DENY.getComponent(null).hoverEvent(Lang.REQUEST_TRADE_DENY_HOVER.getComponent(new String[] { player.getName() })).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trade " + player.getName() + " deny")));

                                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TRADE_REQUEST_SUCCESSFUL.getComponent(new String[] { target.getName() })));
                                        target.sendMessage(targetMessage.append(accept).append(deny));
                                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_REQUEST_ALREADY_SENT.getComponent(new String[] { target.getName() })));
                                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_IS_ALREADY_TRADING.getComponent(null)));
                            }
                        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TRADE_TRADE_SELF.getComponent(null)));
                    } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { args[0] })));
            } else player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
