package lee.code.essentials.database.tables;

import lee.code.core.ormlite.field.DatabaseField;
import lee.code.core.ormlite.table.DatabaseTable;
import lee.code.essentials.lists.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "player")
public class PlayerTable {

    @DatabaseField(id = true, canBeNull = false)
    private UUID player;

    @DatabaseField(columnName = "balance", canBeNull = false)
    private long balance;

    @DatabaseField(columnName = "rank", canBeNull = false)
    private String rank;

    @DatabaseField(columnName = "perms", canBeNull = false)
    private String perms;

    @DatabaseField(columnName = "prefix", canBeNull = false)
    private String prefix;

    @DatabaseField(columnName = "suffix", canBeNull = false)
    private String suffix;

    @DatabaseField(columnName = "color", canBeNull = false)
    private String color;

    @DatabaseField(columnName = "level", canBeNull = false)
    private int level;

    @DatabaseField(columnName = "prestige", canBeNull = false)
    private int prestige;

    @DatabaseField(columnName = "vanish", canBeNull = false)
    private boolean vanish;

    @DatabaseField(columnName = "god", canBeNull = false)
    private boolean god;

    @DatabaseField(columnName = "homes", canBeNull = false)
    private String homes;

    @DatabaseField(columnName = "flying", canBeNull = false)
    private boolean flying;

    @DatabaseField(columnName = "votes", canBeNull = false)
    private int votes;

    @DatabaseField(columnName = "playtime", canBeNull = false)
    private long playtime;

    @DatabaseField(columnName = "locked_hotbar", canBeNull = false)
    private boolean lockedHotbar;

    public PlayerTable(UUID player) {
        this.player = player;
        this.balance = 0;
        this.rank = Rank.NOMAD.name();
        this.perms = "0";
        this.prefix = Rank.NOMAD.getPrefix();
        this.suffix = "0";
        this.color = ChatColor.YELLOW.name();
        this.level = 0;
        this.prestige = 0;
        this.vanish = false;
        this.god = false;
        this.homes = "0";
        this.flying = false;
        this.votes = 0;
        this.playtime = 0;
        this.lockedHotbar = false;
    }
}