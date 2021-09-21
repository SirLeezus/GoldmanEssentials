package lee.code.essentials.commands.cmds;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RulesCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();

        if (sender instanceof Player player) {

            List<Component> lines = new ArrayList<>();

            lines.add(Lang.COMMAND_RULES_TITLE.getComponent(null));
            lines.add(Component.text(""));
            lines.add(pu.formatC("&6&lSurvival Rules:"));
            lines.add(pu.formatC("&31. &eHacking is not allowed."));
            lines.add(pu.formatC("&32. &eExploiting bugs is not allowed."));
            lines.add(pu.formatC("&33. &eCreating unnecessary massive redstone contraptions is not allowed."));
            lines.add(pu.formatC("&34. &ePlacing a massive amount of block entities (Chests, Signs, Campfires) in the same area is not allowed."));
            lines.add(pu.formatC("&35. &eDo not lie or cheat players, keep your word."));
            lines.add(pu.formatC("&36. &eDon't be a bully."));
            lines.add(pu.formatC("&37. &eNo claiming around other player's bases if they don't want you near them."));
            lines.add(pu.formatC("&38. &eNo griefing, but if you do end up getting griefed items will not be refunded. Claim your land."));
            lines.add(pu.formatC("&39. &eNo stealing of any kind, but if someone does steal from you, you will not be refunded. Claim your land."));
            lines.add(Component.text(""));
            lines.add(pu.formatC("&6&lChat Rules:"));
            lines.add(pu.formatC("&31. &ePlease keep cursing and profanity to a minimum."));
            lines.add(pu.formatC("&32. &eRacism won't be tolerated."));
            lines.add(pu.formatC("&33. &eSexism won't be tolerated."));
            lines.add(pu.formatC("&34. &eDo not spam the chat."));
            lines.add(pu.formatC("&35. &eDo not share other server addresses in chat."));
            lines.add(pu.formatC("&36. &eDo not link inappropriate websites in chat."));
            lines.add(Component.text(""));
            lines.add(pu.formatC("&6&lPvP Rules:"));
            lines.add(pu.formatC("&31. &eYou don't need to return items if you kill someone but if you give your word you'll return the items you do have to return them."));
            lines.add(pu.formatC("&32. &eTP killing is frowned upon but allowed, be careful who you trust."));
            lines.add(Component.text(""));
            lines.add(pu.formatC("&6&lPlayer Shop Rules:"));
            lines.add(pu.formatC("&31. &eDo not set a spawn for your shop if you don't have a shop area claimed and setup."));
            lines.add(pu.formatC("&32. &eYou are not allowed to set your shop spawn in a place that'll hurt the player when they teleport."));
            lines.add(pu.formatC("&33. &eIf a shop is fully out of stock for over a month the spawn point will be removed."));
            lines.add(Component.text(""));
            lines.add(Lang.WARNING.getComponent(null).append(pu.formatC("&c&cBreaking any of these rules can result in a permanent ban so be careful how you decide to play.")));
            lines.add(Component.text(""));
            lines.add(Lang.COMMAND_RULES_SPLITTER.getComponent(null));

            for (Component line : lines) player.sendMessage(line);
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }
}
