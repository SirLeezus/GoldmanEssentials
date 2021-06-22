package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RankList {

    //NORMAL
    NOMAD("&7[&#00C1FENomad&7]", 5, "ADVENTURER", 1000, 1000),
    ADVENTURER("&e[&#2DE364Adventurer&e]", 10, "CONQUER", 2000, 2000),
    CONQUER("&e[&#FFC300Conquer&e]", 20, "HERO", 3000, 3000),
    HERO("&e[&#00A2FFHero&e]", 30, "LEGEND", 4000, 4000),
    LEGEND("&e[&#FF7800Legend&e]", 40, "MYTH", 5000, 5000),
    MYTH("&e[&#FF3909Myth&e]", 50, "IMMORTAL", 6000, 6000),
    IMMORTAL("&e[&#0074F6Immortal&e]", 60, "ASCENDANT", 7000, 7000),
    ASCENDANT("&e[&#F500F5Ascendant&e]", 75, "GOD", 8000, 8000),
    GOD("&#FF7800[&e&lGod&#FF7800]", 86, "LAST", 10000, 10000),

    //STAFF
    MOD("&d[&5&lMod&d]", 86, "STAFF", 100000, 10000),
    ADMIN("&2[&a&lAdmin&2]", 86, "STAFF", 100000, 10000),
    OWNER("&6[&#F40000&lOwner&6]", 86, "STAFF", 100000, 10000),
    ;

    @Getter private final String prefix;
    @Getter private final int rankupLevel;
    @Getter private final String nextRank;
    @Getter private final int exp;
    @Getter private final long cash;
}
