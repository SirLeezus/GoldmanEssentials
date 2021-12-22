package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;

@AllArgsConstructor
public enum NameTextColors {

    DARK_BLUE(NamedTextColor.DARK_BLUE),
    BLUE(NamedTextColor.BLUE),
    DARK_AQUA(NamedTextColor.DARK_AQUA),
    AQUA(NamedTextColor.AQUA),
    YELLOW(NamedTextColor.YELLOW),
    GOLD(NamedTextColor.GOLD),
    DARK_GREEN(NamedTextColor.DARK_GREEN),
    GREEN(NamedTextColor.GREEN),
    DARK_PURPLE(NamedTextColor.DARK_PURPLE),
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE),
    BLACK(NamedTextColor.BLACK),
    WHITE(NamedTextColor.WHITE),
    DARK_GRAY(NamedTextColor.DARK_GRAY),
    GRAY(NamedTextColor.GRAY),
    RED(NamedTextColor.RED),
    DARK_RED(NamedTextColor.DARK_RED)
    ;

    @Getter private final NamedTextColor color;
}
