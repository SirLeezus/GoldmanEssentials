package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Settings {

    CLICK_DELAY(5),
    MAX_ENTITY_PER_CHUNK(5),
    ;

    @Getter private final int value;
}
