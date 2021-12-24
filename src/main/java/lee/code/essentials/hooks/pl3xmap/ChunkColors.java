package lee.code.essentials.hooks.pl3xmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
public enum ChunkColors {

    AQUA(Color.decode("#00FFFF")),
    BLACK(Color.decode("#000000")),
    BLUE(Color.decode("#0000FF")),
    DARK_AQUA(Color.decode("#249D9F")),
    DARK_BLUE(Color.decode("#00008B")),
    DARK_GRAY(Color.decode("#606060")),
    DARK_GREEN(Color.decode("#006400")),
    DARK_PURPLE(Color.decode("#9400D3")),
    DARK_RED(Color.decode("#8b0000")),
    GOLD(Color.decode("#ffd700")),
    GRAY(Color.decode("#A8A8A8")),
    GREEN(Color.decode("#32CD32")),
    LIGHT_PURPLE(Color.decode("#FF00FF")),
    RED(Color.decode("#FF0000")),
    WHITE(Color.decode("#FFFFFF")),
    YELLOW(Color.decode("#FFFF00")),

    ;

    @Getter
    private final Color color;
}
