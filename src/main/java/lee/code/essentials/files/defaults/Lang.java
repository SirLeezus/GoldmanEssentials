package lee.code.essentials.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    PREFIX("PREFIX", "&2&lEssentials &6➔ &r"),
    ON("ON", "&2&lON"),
    OFF("OFF", "&c&lOFF"),
    COMMAND_SPAWN_SUCCESSFUL("COMMAND_SPAWN_SUCCESSFUL", "&aYou have successfully been teleported to spawn!"),
    COMMAND_SETSPAWN_SUCCESSFUL("COMMAND_SETSPAWN_SUCCESSFUL", "&aYou have successfully set the new spawn!"),
    COMMAND_GAMEMODE_SUCCESSFUL("COMMAND_GAMEMODE_SUCCESSFUL", "&aYou successfully changed your gamemode to &6{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("COMMAND_FLY_TOGGLE_SUCCESSFUL", "&aYou successfully toggled your fly {0}&a!"),
    COMMAND_FLYSPEED_SUCCESSFUL("COMMAND_FLYSPEED_SUCCESSFUL", "&aYour fly speed is now set to &6{0}&a!"),
    COMMAND_WORLD_SUCCESSFUL("COMMAND_WORLD_SUCCESSFUL", "&aYou have been teleported to world &6{0}&a!"),
    COMMAND_BALANCE_SUCCESSFUL("COMMAND_BALANCE_SUCCESSFUL", "&2&lBalance&7: &6${0}"),
    COMMAND_BALANCETOP_SUCCESSFUL("COMMAND_BALANCETOP_SUCCESSFUL", "{0}&7. {1} &e- &6${2}"),
    COMMAND_BALANCETOP_TITLE("COMMAND_BALANCETOP_TITLE", "&a---------&e[ &2&lTop Balances &e]&a---------"),
    COMMAND_BALANCETOP_SPLITTER("COMMAND_BALANCETOP_SPLITTER", "&a---------------------------------"),
    ERROR_COMMAND_MONEY_VALUE("ERROR_COMMAND_MONEY_VALUE", "&cThe input &6{0} &cis not a number."),
    ERROR_COMMAND_WRONG_COMMAND_ARG("ERROR_COMMAND_WRONG_COMMAND_ARG", "&cThe input &6{0} &cis not a option for this command."),
    ERROR_PLAYER_NOT_ONLINE("ERROR_PLAYER_NOT_ONLINE", "&cThe player &6{0} &cis not online."),
    ERROR_COMMAND_WORLD_NOT_FOUND("ERROR_COMMAND_WORLD_NOT_FOUND", "&cThe world &6{0} &ccould not be found."),
    ERROR_CHUNK_MAX_ENTITIES("ERROR_CHUNK_MAX_ENTITIES", "&cYou can only have {0} of each entity in each chunk."),
    ERROR_COMMAND_WORLD_ARGS("ERROR_COMMAND_WORLD_ARGS", "&cYou need to input a world name to use this command."),
    ERROR_COMMAND_GAMEMODE_ARGS("ERROR_COMMAND_GAMEMODE_ARGS", "&cYou need to input a gamemode to use this command."),
    ERROR_COMMAND_FLYSPEED_LIMIT("ERROR_COMMAND_FLYSPEED_LIMIT", "&cYou can only adjust your fly speed between 1 and 10."),
    ERROR_COMMAND_FLYSPEED_ARGS("ERROR_COMMAND_FLYSPEED_ARGS", "&cYou need to input a number between 1 and 10 to use this command."),
    ERROR_COMMAND_GAMEMODE_NOT_FOUND("ERROR_COMMAND_GAMEMODE_NOT_FOUND", "&cSadly the gamemode &6{0} &cis not an option."),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";

        String value = ChatColor.translateAlternateColorCodes('&', fileValue);

        if (args == null) return value;
        else if (args.length == 0) return value;

        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);

        return value;
    }
}
