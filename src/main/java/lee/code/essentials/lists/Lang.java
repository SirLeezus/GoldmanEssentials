package lee.code.essentials.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum Lang {
    PREFIX("&2&lEssentials &e➔ &r"),
    ANNOUNCEMENT("&e&lAnnouncement &6➔ &r"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    TELEPORT("&eWhooosh!"),
    SERVER_RESTART("&2The server is restarting! Journey will be back online soon!"),
    COMMAND_SETSPAWN_SUCCESSFUL("&aYou have successfully set the new spawn!"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYou successfully changed your gamemode to &6{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYou successfully toggled your fly {0}&a!"),
    COMMAND_FLYSPEED_SUCCESSFUL("&aYour fly speed is now set to &6{0}&a!"),
    COMMAND_BALANCE_SUCCESSFUL("&2&lBalance&7: &6${0}"),
    COMMAND_SETPREFIX_SUCCESSFUL("&aYou successfully set &6{0}'s &aprefix to: &f{1}"),
    COMMAND_GLOW_SUCCESSFUL("&aYou successfully toggled your glow {0}&a."),
    COMMAND_SETCOLOR_SUCCESSFUL("&aYou successfully set &6{0}'s &acolor to: &f{1}"),
    COMMAND_TELEPORT_REQUEST_SUCCESSFUL("&aYou successfully sent &6{0} &aa teleport request."),
    COMMAND_TELEPORT_DENY_SUCCESSFUL("&aYou successfully denied &6{0}'s &ateleport request."),
    COMMAND_TELEPORT_ACCEPT_SUCCESSFUL("&aYou successfully accepted &6{0}'s &ateleport request."),
    COMMAND_SOUND_SUCCESSFUL("&aYou successfully played the sound &b{0} &afor &6{1}&a!"),
    COMMAND_BALANCETOP_SUCCESSFUL("{0}&7. {1} &e- &6${2}"),
    COMMAND_BALANCETOP_TITLE("&a--------- &e[ &2&lTop Balances &e] &a---------"),
    COMMAND_BALANCETOP_SPLITTER("&a----------------------------------"),
    COMMAND_RANKUP_TITLE("&a-------------- &e[ &2&lRankup &e] &a--------------"),
    COMMAND_RANKUP_SPLITTER("&a---------------------------------------"),
    COMMAND_RANKUP_HOVER("&e&lTotal Advancements&7: &a{0}&7/&a{1}"),
    COMMAND_RANKUP_CONFIRM_MESSAGE("&6&l[&e&l!&6&l] &aClick confirm to rank up&7: "),
    COMMAND_RANKUP_CONFIRM_BUTTON("&6[&e&lCONFIRM&6]"),
    COMMAND_RANKUP_CONFIRM_HOVER("&aClick to rank up to {0}&a!"),
    COMMAND_RANKUP_BROADCAST("&aPlayer &6{0} &ahas ranked up to {1}&a!"),
    ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED("&cYou already have a pending teleport request sent to &6{0}&c."),
    ERROR_COMMAND_TELEPORT_NOT_REQUESTING("&cThe player &6{0} &cis not currently requesting teleportation."),
    ERROR_COMMAND_MONEY_VALUE("&cThe input &6{0} &cis not a number."),
    ERROR_COMMAND_WRONG_COMMAND_ARG("&cThe input &6{0} &cis not a option for this command."),
    ERROR_COMMAND_TELEPORTACCEPT_ARG("&cYou need to input a player to run this command."),
    ERROR_COMMAND_TELEPORTDENY_ARG("&cYou need to input a player to run this command."),
    ERROR_COMMAND_TELEPORT_TO_SELF("&cYou can't teleport to yourself."),
    ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED("&7Your teleport request to &6{0} &7has now expired."),
    ERROR_COMMAND_TELEPORTDENY_DENIED("&cSadly the player &6{0} &chas denied your teleport request."),
    ERROR_COMMAND_SETPREFIX_ARG("&cYou need to input a target player and new prefix to run this command."),
    ERROR_COMMAND_SETCOLOR_ARG("&cYou need to input a target player and new color to run this command."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_COMMAND_WORLD_NOT_FOUND("&cThe world &6{0} &ccould not be found."),
    ERROR_CHUNK_MAX_ENTITIES("&cYou can only have {0} of each entity in each chunk."),
    ERROR_COMMAND_WORLD_ARGS("&cYou need to input a world name to use this command."),
    ERROR_COMMAND_GAMEMODE_ARGS("&cYou need to input a gamemode to use this command."),
    ERROR_COMMAND_FLYSPEED_LIMIT("&cYou can only adjust your fly speed between 1 and 10."),
    ERROR_COMMAND_FLYSPEED_ARGS("&cYou need to input a number between 1 and 10 to use this command."),
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
