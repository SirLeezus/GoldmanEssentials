package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Rank {

    //NORMAL
    NOMAD(false, "&7[&#00C1FENomad&7]", "YELLOW", 5, "ADVENTURER", 1000, 1000, "l"),
    ADVENTURER(false, "&e[&#2DE364Adventurer&e]", "GREEN", 10, "CONQUEROR", 2000, 2000, "k"),
    CONQUEROR(false, "&e[&#FFC300Conqueror&e]", "BLUE", 20, "HERO", 3000, 3000, "j"),
    HERO(false, "&e[&#00A2FFHero&e]", "AQUA", 30, "LEGEND", 4000, 4000, "i"),
    LEGEND(false, "&e[&#FF7800Legend&e]", "LIGHT_PURPLE", 40, "MYTH", 5000, 5000, "h"),
    MYTH(false, "&e[&#FF3909Myth&e]", "DARK_AQUA", 50, "IMMORTAL", 6000, 6000, "g"),
    IMMORTAL(false, "&e[&#0074F6Immortal&e]", "DARK_GREEN", 60, "ASCENDANT", 7000, 7000, "f"),
    ASCENDANT(false, "&e[&#F500F5Ascendant&e]", "DARK_PURPLE", 90, "GOD", 8000, 8000, "e"),
    GOD(false, "&#FF7800[&e&lGod&#FF7800]","GOLD", 97, "LAST", 10000, 10000, "d"),

    //STAFF
    MOD(true, "&d[&5&lMod&d]", "LIGHT_PURPLE", 97, "STAFF", 100000, 10000, "a"),
    ADMIN(true, "&2[&a&lAdmin&2]", "DARK_GREEN", 97, "STAFF", 100000, 10000, "b"),
    OWNER(true, "&6[&#F40000&lOwner&6]", "GOLD", 97, "STAFF", 100000, 10000, "c"),
    ;

    @Getter private final boolean staffRank;
    @Getter private final String prefix;
    @Getter private final String color;
    @Getter private final int rankupLevel;
    @Getter private final String nextRank;
    @Getter private final int exp;
    @Getter private final long cash;
    @Getter private final String priority;
}
