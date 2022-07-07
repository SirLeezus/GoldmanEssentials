package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Setting {

    CLICK_DELAY(5),
    PVP_DELAY(10),
    RTP_DELAY(30),
    SPAM_DELAY(1),
    BOT_KICK_DELAY(300),
    DEFAULT_PLAYER_HOMES(10),
    ACCRUED_HOME_MAX(100),
    ACCRUED_HOME_AMOUNT_GIVEN(1),
    ACCRUED_HOME_BASE_TIME_REQUIRED(3600),
    MAX_ENTITY_PER_CHUNK(25),
    HEAD_DROP_RNG(980),
    AUTO_RESTART(86400 * 20),
    RESOURCE_WORLD_RESET(86400),
    AFK_TIME(60),
    SPAM_ATTEMPTS(5),
    AUTO_MUTE_TIME(1800),
    ;

    @Getter private final int value;
}
