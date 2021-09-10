package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RankList {

    //NORMAL
    NOMAD("&7[&#00C1FENomad&7]", "YELLOW", 5, "ADVENTURER", 1000, 1000, "9"),
    ADVENTURER("&e[&#2DE364Adventurer&e]", "GREEN", 10, "CONQUEROR", 2000, 2000, "8"),
    CONQUEROR("&e[&#FFC300Conqueror&e]", "BLUE", 20, "HERO", 3000, 3000, "7"),
    HERO("&e[&#00A2FFHero&e]", "AQUA", 30, "LEGEND", 4000, 4000, "6"),
    LEGEND("&e[&#FF7800Legend&e]", "LIGHT_PURPLE", 40, "MYTH", 5000, 5000, "5"),
    MYTH("&e[&#FF3909Myth&e]", "DARK_AQUA", 50, "IMMORTAL", 6000, 6000, "4"),
    IMMORTAL("&e[&#0074F6Immortal&e]", "DARK_GREEN", 60, "ASCENDANT", 7000, 7000, "3"),
    ASCENDANT("&e[&#F500F5Ascendant&e]", "DARK_PURPLE", 75, "GOD", 8000, 8000, "2"),
    GOD("&#FF7800[&e&lGod&#FF7800]","GOLD", 86, "LAST", 10000, 10000, "1"),

    //STAFF
    MOD("&d[&5&lMod&d]", "LIGHT_PURPLE", 86, "STAFF", 100000, 10000, "c"),
    ADMIN("&2[&a&lAdmin&2]", "DARK_GREEN", 86, "STAFF", 100000, 10000, "b"),
    OWNER("&6[&#F40000&lOwner&6]", "GOLD", 86, "STAFF", 100000, 10000, "a"),
    ;

    @Getter private final String prefix;
    @Getter private final String color;
    @Getter private final int rankupLevel;
    @Getter private final String nextRank;
    @Getter private final int exp;
    @Getter private final long cash;
    @Getter private final String priority;
}
