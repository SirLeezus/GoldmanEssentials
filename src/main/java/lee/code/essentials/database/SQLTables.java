package lee.code.essentials.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SQLTables {
    PLAYER_DATA("player_data"),
    SERVER_DATA("server"),
    PUNISHMENT_DATA("punishment"),
    BOOSTER_DATA("boosters"),
    ;

    @Getter private final String table;
}