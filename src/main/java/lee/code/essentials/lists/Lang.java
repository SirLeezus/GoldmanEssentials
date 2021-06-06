package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&2&lEssentials &e➔ &r"),
    ANNOUNCEMENT("&e&lAnnouncement &6➔ &r"),
    WARNING("&4&lWarning &6➔ &r"),
    HOVER_WARNING("&6&l[&e&l!&6&l] &r"),
    NORMAL_WARNING("&6[&e!&6] &r"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    TRUE("&2True"),
    FALSE("&cFalse"),
    TELEPORT("&eWhooosh!"),
    PLAYER_JOIN(" &ajoined the server!"),
    PLAYER_QUIT(" &7left the server."),
    ERROR_NO_PERMISSION("&cYou sadly do not have permission for this."),
    SERVER_RESTART("&aThe server is restarting! Journey will be back online soon!"),
    COMMAND_SETSPAWN_SUCCESSFUL("&aYou have successfully set the new spawn!"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYour gamemode was changed to &6{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYour fly was successfully toggled {0}&a!"),
    COMMAND_GOD_TOGGLE_SUCCESSFUL("&aYour god mode was successfully toggled {0}&a!"),
    COMMAND_VANISH_TOGGLE_SUCCESSFUL("&aYour vanish has successfully been toggled {0}&a!"),
    COMMAND_FLYSPEED_SUCCESSFUL("&aYour fly speed is now set to &6{0}&a!"),
    COMMAND_BALANCE_SUCCESSFUL("&2&lBalance&7: &6${0}"),
    COMMAND_SETPREFIX_SUCCESSFUL("&aYou successfully set &6{0}'s &aprefix to: &f{1}"),
    COMMAND_GLOW_SUCCESSFUL("&aYou successfully toggled your glow {0}&a."),
    COMMAND_SETCOLOR_SUCCESSFUL("&aYou successfully set &6{0}'s &acolor to: &f{1}"),
    COMMAND_TELEPORT_REQUEST_SUCCESSFUL("&aYou successfully sent &6{0} &aa teleport request."),
    COMMAND_TELEPORT_DENY_SUCCESSFUL("&aYou successfully denied &6{0}'s &ateleport request."),
    COMMAND_TELEPORT_ACCEPT_SUCCESSFUL("&aYou successfully accepted &6{0}'s &ateleport request."),
    COMMAND_TELEPORT_ACCEPT_SUCCESSFUL_TARGET("&aThe player &6{0} &aaccepted your teleport request!"),
    COMMAND_TELEPORT_ADMIN_SUCCESSFUL("&aYou successfully teleported to the player &6{0}&a!"),
    COMMAND_SOUND_SUCCESSFUL("&aYou successfully played the sound &b{0} &afor &6{1}&a!"),
    ERROR_BALANCETOP_LIST_PAGE("&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    COMMAND_BALANCETOP_TITLE("&a--------- &e[ &2&lTop Balances &e] &a---------"),
    COMMAND_RANKUP_TITLE("&a-------------- &e[ &2&lRankup &e] &a--------------"),
    COMMAND_RANKUP_SPLITTER("&a---------------------------------------"),
    COMMAND_RANKLIST_TITLE("&a------------ &e[ &2&lRank List &e] &a------------"),
    COMMAND_RANKLIST_SPLITTER("&a-------------------------------------"),
    COMMAND_HELP_TITLE("&a------ &e[ &2&lServer Help &e] &a------"),
    COMMAND_HELP_SPLITTER("&a----------------------------"),
    COMMAND_HELP_ESSENTIALS("&3{0}&b. &e{1} &c| &7{2}"),
    COMMAND_HELP_ESSENTIALS_TITLE("                   &e-== &2&l&nEssentials Help&r &e==-"),
    COMMAND_HELP_ESSENTIALS_DIVIDER("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_PLUGIN_HOVER("&6&l[&e&l!&6&l] &eClick to view a list of {0} plugin commands you have permission to use."),
    COMMAND_RANKSET_SUCCESSFUL("&aThe rank &6{0} &awas successfully set for the player &6{1}&a!"),
    COMMAND_RANKLIST_SERVER_RANKS("&6&lServer Ranks&7:"),
    COMMAND_RANKLIST_STAFF_RANKS("&b&lStaff Ranks&7:"),
    COMMAND_RANKLIST_PREMIUM_RANKS("&a&lPremium Ranks&7:"),
    COMMAND_RANKUP_HOVER("&e&lTotal Advancements&7: &a{0}&7/&a{1}"),
    COMMAND_RANKUP_CONFIRM_RANKUP_MESSAGE("&6&l[&e&l!&6&l] &aClick confirm to rank up&7: "),
    COMMAND_RANKUP_CONFIRM_PRESTIGE("&6&l[&e&l!&6&l] &aClick confirm to prestige&7: "),
    COMMAND_RANKUP_CONFIRM_PRESTIGE_WARNING("&cYou will lose all your advancement progress if you prestige."),
    COMMAND_RANKUP_PRESTIGE_BROADCAST("&2Player &6{0} &2is now prestige &a&l{1}&2!"),
    COMMAND_RANKUP_CONFIRM_BUTTON("&6[&e&lCONFIRM&6]"),
    COMMAND_RANKUP_CONFIRM_RANKUP_HOVER("&aClick to rank up to {0}&a!"),
    COMMAND_RANKUP_CONFIRM_PRESTIGE_HOVER("&aClick to prestige to level {0}&a!"),
    COMMAND_RANKUP_BROADCAST("&aPlayer &6{0} &ahas ranked up to {1}&a!"),
    COMMAND_RANKUP_CHECK("&aAdvancement check completed successfully! You currently have &2{0}&7/&2{1} &aadvancements completed."),
    COMMAND_MONEY_GIVE("&aThe player &6{0} &ahas been sent &6${1}&a!"),
    COMMAND_MONEY_GIVE_TARGET("&aYou have received &6${0}&a!"),
    COMMAND_MONEY_SET("&aThe player &6{0} &abalance has been set to &6${1}&a."),
    COMMAND_MONEY_SET_TARGET("&aYour balance has been set to &6${0}&a."),
    COMMAND_MONEY_REMOVE("&aThe player &6{0} &ahad &6${1} &aremoved from their balance."),
    COMMAND_MONEY_REMOVE_TARGET("&aYour balance has just been deducted by &6${0}&a."),
    COMMAND_WEATHER_CLEAR("&aThe weather has been cleared!"),
    COMMAND_WEATHER_RAIN("&aThe weather has been changed to rain."),
    COMMAND_WEATHER_THUNDER("&aThe weather has been changed to thunderstorm."),
    ERROR_ARMOR_STAND_EDIT("&cThat armor stand is currently being edited by another player."),
    ERROR_COMMAND_RANKUP_CONFIRM("&cYou only have &2{0} &cadvancements completed and you need &2{1} &cto rankup."),
    ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED("&cYou already have a pending teleport request sent to &6{0}&c."),
    ERROR_COMMAND_TELEPORT_NOT_REQUESTING("&cThe player &6{0} &cis not currently requesting teleportation."),
    ERROR_COMMAND_MONEY_VALUE("&cThe input &6{0} &cis not a number."),
    ERROR_COMMAND_WRONG_COMMAND_ARG("&cThe input &6{0} &cis not a option for this command."),
    ERROR_COMMAND_TELEPORTACCEPT_ARG("&cYou need to input a player to run this command."),
    ERROR_COMMAND_TELEPORTDENY_ARG("&cYou need to input a player to run this command."),
    ERROR_COMMAND_TELEPORT_TO_SELF("&cYou can't teleport to yourself."),
    ERROR_COMMAND_MESSAGE_TO_SELF("&cYou can't message yourself."),
    ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED("&7Your teleport request to &6{0} &7has now expired."),
    ERROR_COMMAND_TELEPORTDENY_DENIED("&cSadly the player &6{0} &chas denied your teleport request."),
    ERROR_COMMAND_SETPREFIX_ARG("&cYou need to input a target player and new prefix to run this command."),
    ERROR_COMMAND_SETCOLOR_ARG("&cYou need to input a target player and new color to run this command."),
    ERROR_COMMAND_REPLY_NO_PLAYER("&cNo player was found for a reply."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_COMMAND_WORLD_NOT_FOUND("&cThe world &6{0} &ccould not be found."),
    ERROR_CHUNK_MAX_ENTITIES("&cYou can only have {0} of each entity in each chunk."),
    ERROR_COMMAND_WORLD_ARGS("&cYou need to input a world name to use this command."),
    ERROR_COMMAND_GAMEMODE_ARGS("&cYou need to input a gamemode to use this command."),
    ERROR_COMMAND_FLYSPEED_LIMIT("&cYou can only adjust your fly speed between 1 and 10."),
    ERROR_COMMAND_FLYSPEED_ARGS("&cYou need to input a number between 1 and 10 to use this command."),
    MENU_ARMOR_STAND_POSITION_LORE("&7» Left-Click -0.01\n&7» Right-Click +0.01\n&7» Shift-Click x10"),
    MENU_ARMOR_STAND_DIRECTION_POSITION_LORE("&7» Left-Click -0.50\n&7» Right-Click +0.50\n&7» Shift-Click x50"),
    MENU_ARMOR_STAND_SETTING_INVULNERABLE("&6&lInvulnerable&7: {0}"),
    MENU_ARMOR_STAND_SETTING_ARMS("&6&lArms&7: {0}"),
    MENU_ARMOR_STAND_SETTING_PLATE("&6&lPlate&7: {0}"),
    MENU_ARMOR_STAND_SETTING_SMALL("&6&lSmall&7: {0}"),
    MENU_ARMOR_STAND_SETTING_VISIBLE("&6&lVisible&7: {0}"),
    MENU_ARMOR_STAND_SETTING_GLOWING("&6&lGlowing&7: {0}"),
    MENU_ARMOR_STAND_SETTING_NAME_VISIBLE("&6&lName Visible&7: {0}"),
    MENU_ARMOR_STAND_SETTING_GRAVITY("&6&lGravity&7: {0}"),
    MENU_ARMOR_STAND_POSITION("&#0049bf&lPosition {0}"),
    MENU_ARMOR_STAND_HEAD_POSITION("&#ffee05&lHead Position {0}"),
    MENU_ARMOR_STAND_TORSO_POSITION("&#FF7C00&lTorso Position {0}"),
    MENU_ARMOR_STAND_LEFT_ARM_POSITION("&#FF00EC&lLeft Arm Position {0}"),
    MENU_ARMOR_STAND_LEFT_LEG_POSITION("&#00CDFF&lLeft Leg Position {0}"),
    MENU_ARMOR_STAND_RIGHT_LEG_POSITION("&#00CDFF&lRight Leg Position {0}"),
    MENU_ARMOR_STAND_DIRECTION_POSITION("&#0D7E00&lDirection&7 &cYaw&7: &6{0}"),
    MENU_ARMOR_STAND_RIGHT_ARM_POSITION("&#FF00EC&lRight Arm Position {0}"),
    MENU_ARMOR_STAND_TITLE("&#0D7E00&lArmor Stand Editor"),
    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        String value = string;
        if (variables == null || variables.length == 0) return plugin.getPU().format(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return plugin.getPU().format(value);
    }

    public Component getComponent(String[] variables) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        String value = string;
        if (variables == null || variables.length == 0) return plugin.getPU().formatC(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return plugin.getPU().formatC(value);
    }
}
