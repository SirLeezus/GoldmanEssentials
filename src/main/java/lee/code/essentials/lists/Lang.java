package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum Lang {
    PREFIX("&2&lEssentials &6➔ &r"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    COMMAND_SPAWN_SUCCESSFUL("&aYou have successfully been teleported to spawn!"),
    COMMAND_SETSPAWN_SUCCESSFUL("&aYou have successfully set the new spawn!"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYou successfully changed your gamemode to &6{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYou successfully toggled your fly {0}&a!"),
    COMMAND_FLYSPEED_SUCCESSFUL("&aYour fly speed is now set to &6{0}&a!"),
    COMMAND_WORLD_SUCCESSFUL("&aYou have been teleported to world &6{0}&a!"),
    COMMAND_BALANCE_SUCCESSFUL("&2&lBalance&7: &6${0}"),
    COMMAND_BALANCETOP_SUCCESSFUL("{0}&7. {1} &e- &6${2}"),
    COMMAND_BALANCETOP_TITLE("&a---------&e[ &2&lTop Balances &e]&a---------"),
    COMMAND_BALANCETOP_SPLITTER("&a---------------------------------"),
    ERROR_COMMAND_MONEY_VALUE("&cThe input &6{0} &cis not a number."),
    ERROR_COMMAND_WRONG_COMMAND_ARG("&cThe input &6{0} &cis not a option for this command."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_COMMAND_WORLD_NOT_FOUND("&cThe world &6{0} &ccould not be found."),
    ERROR_CHUNK_MAX_ENTITIES("&cYou can only have {0} of each entity in each chunk."),
    ERROR_COMMAND_WORLD_ARGS("&cYou need to input a world name to use this command."),
    ERROR_COMMAND_GAMEMODE_ARGS("&cYou need to input a gamemode to use this command."),
    ERROR_COMMAND_FLYSPEED_LIMIT("&cYou can only adjust your fly speed between 1 and 10."),
    ERROR_COMMAND_FLYSPEED_ARGS("&cYou need to input a number between 1 and 10 to use this command."),
    ERROR_COMMAND_GAMEMODE_NOT_FOUND("&cSadly the gamemode &6{0} &cis not an option."),
    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        String value = ChatColor.translateAlternateColorCodes('&', string);
        if (variables == null) return value;
        else if (variables.length == 0) return value;
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
