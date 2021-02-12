package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PrefixList  {

    ADMIN("&2Admin"),
    MOD("&5Mod"),
    OWNER("&cOwner"),
    BALLER("&dBaller"),

    ;

    @Getter private final String prefix;
}
