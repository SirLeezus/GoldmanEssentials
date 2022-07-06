package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PremiumRank {

    VIP("&#ffffff\uE507", "&#FCFF2D&lVIP"),
    MVP("&#ffffff\uE505", "&#00D8F6&lMVP"),
    ELITE("&#ffffff\uE64C", "&#33cc33&lElite"),
    ;

    @Getter private final String suffix;
    @Getter private final String displayName;
}

