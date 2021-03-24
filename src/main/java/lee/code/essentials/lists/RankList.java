package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RankList {

    //NORMAL
    NOMAD("&7[&bNomad&7]", 5, "ADVENTURER"),
    ADVENTURER("&e[&aAdventurer&e]", 10, "CONQUER"),
    CONQUER("&e[&aConquer&e]", 20, "HERO"),
    HERO("&e[&aHero&e]", 30, "LEGEND"),
    LEGEND("&e[&aLegend&e]", 40, "MYTH"),
    MYTH("&e[&aMyth&e]", 50, "IMMORTAL"),
    IMMORTAL("&e[&aImmortal&e]", 60, "GOD"),
    GOD("&e[&aGod&e]", 75, "LAST"),

    //STAFF
    MOD("&d[&5&lMod&d]", 0, "STAFF"),
    ADMIN("&2[&a&lAdmin&2]", 0, "STAFF"),
    OWNER("&2[&c&lOwner&2]", 0, "STAFF"),
    ;

    @Getter private final String prefix;
    @Getter private final int rankupLevel;
    @Getter private final String nextRank;
}
