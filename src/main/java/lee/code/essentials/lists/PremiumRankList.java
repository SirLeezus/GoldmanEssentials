package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PremiumRankList {

    VIP("&#FF9B00❉", "&#FF9B00&lVIP "),
    MVP("&#00D8F6❈", "&#00D8F6&lMVP "),
    ELITE("&#FCFF2D☀", "&#FCFF2D&lElite "),
    ;

    @Getter private final String suffix;
    @Getter private final String displayName;
}

