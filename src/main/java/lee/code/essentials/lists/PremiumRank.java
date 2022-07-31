package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PremiumRank {

    VIP("&#ffffff\ue8e0", "&#FCFF2D&lVIP"),
    MVP("&#ffffff\ue8e1", "&#00D8F6&lMVP"),
    ELITE("&#ffffff\ue8e2", "&#33cc33&lElite"),
    ;

    @Getter private final String suffix;
    @Getter private final String displayName;
}

