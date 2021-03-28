package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RankList {

    //NORMAL
    NOMAD("&7[#00C1FENomad&7]", 5, "ADVENTURER"),
    ADVENTURER("&e[#2DE364Adventurer&e]", 10, "CONQUER"),
    CONQUER("&e[#FFC300Conquer&e]", 20, "HERO"),
    HERO("&e[#00A2FFHero&e]", 30, "LEGEND"),
    LEGEND("&e[#FF7800Legend&e]", 40, "MYTH"),
    MYTH("&e[#FF3909Myth&e]", 50, "IMMORTAL"),
    IMMORTAL("&e[#0074F6Immortal&e]", 60, "GOD"),
    GOD("#FF7800[&e&lGod#FF7800]", 75, "LAST"),

    //STAFF
    MOD("&d[&5&lMod&d]", 0, "STAFF"),
    ADMIN("&2[&a&lAdmin&2]", 0, "STAFF"),
    OWNER("&6[#F40000&lOwner&6]", 0, "STAFF"),
    ;

    @Getter private final String prefix;
    @Getter private final int rankupLevel;
    @Getter private final String nextRank;
}
