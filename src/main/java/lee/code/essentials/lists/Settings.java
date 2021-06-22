package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Settings {

    CLICK_DELAY(5),
    PVP_DELAY(10),
    SPAM_DELAY(1),
    MAX_PLAYER_HOMES(10),
    MAX_ENTITY_PER_CHUNK(25),
    HEAD_DROP_RNG(960),
    ;

    @Getter private final int value;
}
