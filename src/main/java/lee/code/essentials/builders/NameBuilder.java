package lee.code.essentials.builders;

import lee.code.essentials.GoldmanEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NameBuilder {

    Scoreboard board;
    Player player;
    ChatColor color;
    String suffix;
    String prefix;

    public NameBuilder(Player player) {
        this.player = player;
        this.board = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public NameBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public NameBuilder setColor(ChatColor color) {
        this.color = color;
        return this;
    }

    public NameBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void build() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Objective health = board.getObjective("health");

        if (health == null) {
            Objective o = board.registerNewObjective("health", "health", plugin.getPU().format("&c❤"), RenderType.HEARTS);
            o.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }

        if (board.getTeam(player.getName()) == null) {
            board.registerNewTeam(player.getName());
        }

        Team team = board.getTeam(player.getName());
        if (team != null) {
            team.setColor(color);
            team.setPrefix(plugin.getPU().format(prefix));
            team.setSuffix(plugin.getPU().format(suffix));
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            team.addEntry(player.getName());
        }

        player.setDisplayName(plugin.getPU().format(prefix + color + player.getName() + suffix));
        player.setPlayerListName(plugin.getPU().format(prefix + color + player.getName() + suffix));
    }
}
