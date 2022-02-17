package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&6[&e!&6] &r"),
    DEATH_PREFIX("&cDeath &6➔ &r"),
    ADVANCEMENT_PREFIX("&aAdvancement &6➔ &r"),
    ANNOUNCEMENT("&e&lAnnouncement &6➔ &r"),
    TIP("&a&lTip &2➔ &r"),
    WARNING("&4&lWarning &6➔ &r"),
    HOVER_ALERT("&6&l[&e&l!&6&l] &r"),
    USAGE("&6&lUsage: &e{0}"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    TRUE("&2True"),
    FALSE("&cFalse"),
    TELEPORT("&eWhooosh!"),
    DURABILITY("&cDurability&7: &e{0}&7/&e{1}"),
    TARGET("&aRedstone Power&7: &6{0}"),
    BEEHIVE("&aBees: &6{0}"),
    NEXT_PAGE_TEXT("&2&lNext &a&l>>--------"),
    PREVIOUS_PAGE_TEXT("&a&l--------<< &2&lPrev"),
    PAGE_SPACER(" &e| "),
    NEXT_PAGE_HOVER("&6&lNext Page"),
    PREVIOUS_PAGE_HOVER("&6&lPrevious Page"),
    PLAYER_JOIN(" &ajoined the server!"),
    PLAYER_QUIT(" &7left the server."),
    MESSAGE_SENT("&9[&eYou &9-> &e{0}&9] "),
    AFK("&7The player &6{0} &7is currently AFK."),
    AFK_ON("&7You're now AFK."),
    AFK_OFF("&7You're no longer AFK."),
    VOTE("&2The player &6{0} &2just voted for the server and received &6$5,000&2!"),
    BOT_CHECKER_KICK("&cYou failed to verify you're not a bot so you've been kicked."),
    AUTO_RESTART_WARNING_START("&eThe server is about to restart!"),
    AUTO_RESTART_WARNING_END("&eThe server is restarting!"),
    AUTO_RESTART_TIME("&2&lRestarting in &e&l{0}&6&ls"),
    DEATH_CORDS("&cYou died at &b(&eW&7:&6{0} &eX&7:&6{1} &eY&7:&6{2} &eZ&7:&6{3}&b) &cUse &6/back &cto return to death location."),
    STAFF_CHAT_COLOR("&#5628FF : "),
    STORE("https://journey.buycraft.net"),
    DISCORD("https://discord.gg/UP65uzBP7m"),
    MAP("https://map.journey-mc.net"),
    SERVER_UUID("ffffffff-ffff-ffff-ffff-ffffffffffff"),
    STAFF_CHAT_PREFIX("&#0073A5[&#A50000&lSC&#0073A5] "),
    SPAWNER_NAME("&e{0} Spawner"),
    REQUEST_DUEL_TARGET("&6&l[&e&l!&6&l] &ePlayer &6{0} &eis requesting a duel: \n"),
    REQUEST_TRADE_TARGET("&6&l[&e&l!&6&l] &ePlayer &6{0} &eis requesting a trade: \n"),
    REQUEST_TELEPORT_TARGET("&6&l[&e&l!&6&l] &ePlayer &6{0} &eis requesting teleportation: \n"),
    REQUEST_ACCEPT("&a&l[&2&lACCEPT&a&l]"),
    REQUEST_DENY("&4&l[&c&lDENY&4&l]"),
    REQUEST_TELEPORT_ACCEPT_HOVER("&6&l[&e&l!&6&l] &2Click to accept &6&l{0}'s &2teleport request."),
    REQUEST_TELEPORT_DENY_HOVER("&6&l[&e&l!&6&l] &cClick to deny &6&l{0}'s &cteleport request."),
    REQUEST_TRADE_ACCEPT_HOVER("&6&l[&e&l!&6&l] &2Click to accept &6&l{0}'s &2trade request."),
    REQUEST_DUEL_ACCEPT_HOVER("&6&l[&e&l!&6&l] &2Click to accept &6&l{0}'s &2duel request."),
    REQUEST_DUEL_DENY_HOVER("&6&l[&e&l!&6&l] &cClick to deny &6&l{0}'s &cduel request."),
    REQUEST_TRADE_DENY_HOVER("&6&l[&e&l!&6&l] &cClick to deny &6&l{0}'s &ctrade request."),
    MESSAGE_RECEIVED("&9[&e{0} &9-> &eYou&9] "),
    BANNED("&cYou have been banned from the server forever for: &7{0}"),
    TEMPBANNED("&cYou have been temporarily banned from the server for &e{0} &cfor: &7{1}"),
    DUEL_COMMAND_ERROR("&cYou can't run commands while in a duel."),
    KICKED("&cYou have been kicked from the server for: &7{0}"),
    MUTED("&cYou have been muted from chat forever for: &7{0}"),
    TEMPMUTED("&cYou have been temporarily muted from chat for &e{0} &cfor: &7{1}"),
    TEMPUNMUTED("&aYour temporary mute is now over. Please follow our rules."),
    SPAM_CHAT("&cPlease slow down the speed you're sending messages."),
    SPAM_COMMAND("&cPlease slow down the speed you're requesting commands."),
    BOOSTER_TITLE("&6&lDrops x{0} Booster&a&l ends in {1}"),
    COLOR_MENU_SELECT("&aYour name color was successfully updated to &f{0}&a!"),
    TABLIST_HEADER("&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n&r\uE790 &#4dc462&lJourney Survival &r\uE790\n&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    TABLIST_FOOTER("\n&#228B22&lOnline&7: &#4dc462{0}"),
    FIRST_JOIN_MESSAGE("&aThe player &6{0} &ajoined for the first time! Welcome to &2&lJourney Survival&a! &d#{1}"),
    SLEEPING_WEATHER_CLEARED_SUBTITLE("&#00E0FFWeather has been cleared!"),
    SLEEPING_TITLE("&#FF8300{0}"),
    SLEEPING_SUBTITLE("&#00E0FFPlayers Sleeping&7: &a{0}&7/&a{1}"),
    TRADE_COMPLETED_SUBTITLE("&#46C646with the player &6{0}"),
    TRADE_COMPLETED_TITLE("&#46C646Trade Completed"),
    TRADE_FAILED_SUBTITLE("&#D71E1Ewith the player &6{0}"),
    TRADE_FAILED_TITLE("&#D71E1ETrade Failed"),
    DUEL_TIMER_TITLE("&6{0}"),
    DUEL_TIMER_START_TITLE("&cPrepare For Battle!"),
    DUEL_TIMER_END_TITLE("&cFight!!!"),
    DUEL_INTERACT_WARNING("&cRun the command &6/duel {0} &cto pvp this player!"),
    ERROR_NO_PERMISSION("&cYou sadly do not have permission for this."),
    BROADCAST_BANNED_FOREVER("&cThe player &6{0} &chas been banned forever for: &7{1}"),
    BROADCAST_TEMPBANNED("&cThe player &6{0} &chas been banned for &e{1} &cfor: &7{2}"),
    BROADCAST_KICKED("&cThe player &6{0} &chas been kicked from the server for: &7{1}"),
    BROADCAST_MUTED_FOREVER("&cThe player &6{0} &chas been muted forever for: &7{1}"),
    BROADCAST_TEMPMUTED("&cThe player &6{0} &chas been muted for {1}&c for: &7{2}"),
    BROADCAST_UNBANNED("&aThe player &6{0} &ahas been unbanned!"),
    BROADCAST_UNMUTED("&aThe player &6{0} &ahas been unmuted!"),
    BROADCAST_BOOSTER_ENDED("&aThe &6&lDrops x{0} Booster &ahas ended. You can thank the player &6{1} &afor activating it!"),
    BROADCAST_BOOSTER_STARTED("&aA &6&lDrops x{0} Booster &ahas started thanks to the player &6{1}&a!"),
    SERVER_RESTART("&aThe server is restarting! Journey will be back online soon!"),
    COMMAND_TRADE_DENY_SENDER("&7The player &6{0} &7has denied your trade request."),
    COMMAND_TRADE_DENY_RECEIVER("&aYou have successfully denied &6{0}'s &atrade request."),
    COMMAND_DUEL_DENY_SENDER("&7The player &6{0} &7has denied your duel request."),
    COMMAND_DUEL_DENY_RECEIVER("&aYou have successfully denied &6{0}'s &aduel request."),
    COMMAND_ADDPERM_SUCCESSFUL("&aThe perm &b{0} &awas successfully added to the player &6{1}&a!"),
    COMMAND_REMOVEPERM_SUCCESSFUL("&aThe perm &b{0} &awas successfully removed to the player &6{1}&a!"),
    COMMAND_SETSPAWN_MAIN_SUCCESSFUL("&aYou have successfully set the main spawn in world &b{0}&a!"),
    COMMAND_SETSPAWN_RESOURCE_SUCCESSFUL("&aYou have successfully set the &3{0} &aspawn in world &b{1}&a!"),
    COMMAND_ITEMLORE_SUCCESSFUL("&aYou have successfully set lore line &e{0} &ato &7(&5&o{1}&a&7)&a for the item &e{2}&a!"),
    COMMAND_RELOAD_SUCCESSFUL("&aYou have successfully reloaded essential's text file."),
    COMMAND_PLAYTIME_SUCCESSFUL("&aYou have played&7: &e{0}"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYour gamemode was updated to &e{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYour fly was successfully toggled {0}&a!"),
    COMMAND_GOD_TOGGLE_SUCCESSFUL("&aYour god mode was successfully toggled {0}&a!"),
    COMMAND_VANISH_TOGGLE_SUCCESSFUL("&aYour vanish has successfully been toggled {0}&a!"),
    COMMAND_FLYSPEED_SUCCESSFUL("&aYour fly speed is now set to &6{0}&a!"),
    COMMAND_STAFFCHAT_TOGGLE_SUCCESSFUL("&aStaff chat has been toggled {0}&a."),
    COMMAND_SETRANK_PREMIUM_SUCCESSFUL("&dThank you for supporting our server by purchasing the {0} &drank! Your new suffix, trails, commands and pets have been set!"),
    COMMAND_BALANCE_SUCCESSFUL("&2Balance&7: &6${0}"),
    COMMAND_SETPREFIX_SUCCESSFUL("&aYou successfully set &6{0}'s &aprefix to: &f{1}"),
    COMMAND_SETSUFFIX_SUCCESSFUL("&aYou successfully set &6{0}'s &asuffix to: &f{1}"),
    COMMAND_GLOW_SUCCESSFUL("&aYour glow was successfully toggled {0}&a."),
    COMMAND_SETCOLOR_SUCCESSFUL("&aYou successfully set &6{0}'s &acolor to: &f{1}"),
    COMMAND_PAY_SENDER_SUCCESSFUL("&aYou paid the player &e{0} &6${1}&a!"),
    COMMAND_PAY_TARGET_SUCCESSFUL("&aYou just received &6${0} &afrom the player &e{1}&a!"),
    COMMAND_TELEPORT_REQUEST_SUCCESSFUL("&aYou successfully sent &6{0} &aa teleport request."),
    COMMAND_TRADE_REQUEST_SUCCESSFUL("&aYou successfully sent &6{0} &aa trade request."),
    COMMAND_DUEL_REQUEST_SUCCESSFUL("&aYou successfully sent &6{0} &aa duel request."),
    COMMAND_DUEL_ACCEPT_SUCCESSFUL("&aYou successfully accepted &6{0}'s &aduel request."),
    COMMAND_DUEL_ACCEPT_TARGET_SUCCESSFUL("&aThe player &6{0} &asuccessfully accepted your duel request."),
    COMMAND_DUEL_WINNER("&aCongratulations, you won the duel against &6{0}&a!"),
    COMMAND_DUEL_LOSER("&7Sadly you lost the duel against the player &6{0}&7."),
    COMMAND_TELEPORT_DENY_SUCCESSFUL("&aYou successfully denied &6{0}'s &ateleport request."),
    COMMAND_TELEPORT_ACCEPT_SUCCESSFUL("&aYou successfully accepted &6{0}'s &ateleport request."),
    COMMAND_TELEPORT_ACCEPT_SUCCESSFUL_TARGET("&aThe player &6{0} &aaccepted your teleport request!"),
    COMMAND_TELEPORT_ADMIN_SUCCESSFUL("&aYou successfully teleported to the player &6{0}&a!"),
    COMMAND_SOUND_SUCCESSFUL("&aYou successfully played the sound &b{0} &afor &6{1}&a!"),
    COMMAND_SELL_SUCCESSFUL("&aYou successfully sold &b{0} x{1} &afor &6${2}&a!"),
    COMMAND_WORTH_SUCCESSFUL("&aThe item &b{0} &acan be sold to the server for &6${1} &aeach!\n" + PREFIX.getString() + "&aYour hand is holding &6${2} &aworth.\n" + PREFIX.getString() + "&aYour inventory is holding &6${3} &aworth."),
    COMMAND_WORTH_LIST_LINE("&3{0}. &e{1} &7- &6${2}"),
    COMMAND_BANLIST_NO_BANS("&aThere are currently no players banned."),
    COMMAND_TIME_SUCCESSFUL("&aThe time for world &e{0} &awas changed to &e{1}&a."),
    COMMAND_SEEN_SUCCESSFUL("&aThe last time the player &6{0} &ajoined was &b{1}&a."),
    COMMAND_BOOSTER_ID_HOVER("&9&lID&7: &e{0}"),
    COMMAND_BOOSTER_TITLE("&a------------------ &e[ &2&lBoosters &e] &a------------------"),
    COMMAND_BOOSTER_ACTIVE("&6&lActive Booster: &bDrops x{0} Booster &e| &6{1}"),
    COMMAND_BOOSTER_QUEUE("&3{0}&b. &9Drops x{1} Booster &e| &6{2}"),
    COMMAND_BOOSTER_SPLITTER("&a-------------------------------------------------"),
    COMMAND_BOOSTER_ADD_SUCCESSFUL("&a{0}'s Drops x{1} Booster has been queued."),
    COMMAND_BOOSTER_REMOVE_SUCCESSFUL("&aThe booster id &9{0} &awas successfully removed."),
    COMMAND_RESET_RESOURCE_WORLD_SUCCESSFUL("&aAll resource worlds will reset on restart now."),
    COMMAND_COLORS_TITLE("&a------- &e[ &2&lColors &e] &a-------"),
    COMMAND_COLORS_SPLITTER("&a------------------------"),
    COMMAND_WORTH_LIST_TITLE("&a----------- &e[ &2&lWorth List &e] &a-----------"),
    COMMAND_WORTH_LIST_SPLITTER("&a-------------------------------------"),
    COMMAND_VOTETOP_TITLE("&a------ &e[ &2&lVoting Leaderboard &e] &a------"),
    COMMAND_BALANCETOP_TITLE("&a----- &e[ &2&lBalance Leaderboard &e] &a-----"),
    COMMAND_PLAYTIMETOP_TITLE("&a---- &e[ &2&lPlay Time Leaderboard &e] &a----"),
    COMMAND_BANLIST_TITLE("&a--------- &e[ &2&lBanned Players &e] &a---------"),
    COMMAND_RANKUP_TITLE("&a-------------- &e[ &2&lRankup &e] &a--------------"),
    COMMAND_RANKUP_SPLITTER("&a---------------------------------------"),
    COMMAND_RANKLIST_TITLE("&a------------ &e[ &2&lRank List &e] &a------------"),
    COMMAND_RANKLIST_SPLITTER("&a-------------------------------------"),
    COMMAND_HELP_TITLE("&a------ &e[ &2&lServer Help &e] &a------"),
    COMMAND_HELP_SPLITTER("&a----------------------------"),
    COMMAND_VOTE_TITLE("&a--------- &e[ &2&lVoting &e] &a---------"),
    COMMAND_VOTE_SPLITTER("&a----------------------------"),
    COMMAND_CHECK_ADVANCEMENT_TITLE("&a--------- &e[ &2&lAdvancement Progress &e] &a---------"),
    COMMAND_CHECK_ADVANCEMENT_SPLITTER("&a---------------------------------------------"),
    COMMAND_HOME_TITLE("&a------------- &e[ &2&lHomes &e] &a-------------"),
    COMMAND_HOME_SPLITTER("&a--------------------------------------"),
    COMMAND_RULES_TITLE("&a----------------- &e[ &2&lRules &e] &a-----------------"),
    COMMAND_RULES_SPLITTER("&a----------------------------------------------"),
    COMMAND_ITEM_INFO_TITLE("&a---------------- &e[ &2&lItem Info &e] &a----------------"),
    COMMAND_ITEM_INFO_SPLITTER("&a---------------------------------------------"),
    COMMAND_HOME_TELEPORT_SUCCESSFUL("&aYou have been teleported to your &e{0} &ahome location."),
    COMMAND_HOME_TELEPORT_BED_SUCCESSFUL("&aYou have been teleported to your bed!"),
    COMMAND_SETHOME_SUCCESSFUL("&aYou successfully saved the home location &e{0}&a!"),
    COMMAND_DELETEHOME_SUCCESSFUL("&aYou successfully deleted the home location &e{0}&a."),
    COMMAND_HELP_ESSENTIALS("&3{0}&b. &e{1}"),
    COMMAND_HELP_ESSENTIALS_HOVER("&6{0}"),
    COMMAND_HELP_ESSENTIALS_TITLE("                   &e-== &2&l&nEssentials Help&r &e==-"),
    COMMAND_HELP_ESSENTIALS_DIVIDER("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_HOPPER_FILTER_DIVIDER("&3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_CLAIMING_DIVIDER("&3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_ARMOR_STAND_DIVIDER("&5▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_STORE_DIVIDER("&5▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_DISCORD_DIVIDER("&b▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_VANILLA_CHANGES_TITLE("&a------------- &e[ &2&lVanilla Changes &e] &a-------------"),
    COMMAND_HELP_VANILLA_CHANGES_DIVIDER("&a----------------------------------------------"),
    COMMAND_HELP_ENCHANTS_TITLE("&a--------------- &e[ &2&lCustom Enchants &e] &a---------------"),
    COMMAND_HELP_ENCHANTS_DIVIDER("&a-------------------------------------------------"),
    COMMAND_HELP_PLUGIN_HOVER("&6&l[&e&l!&6&l] &eClick to view a list of {0} plugin commands you have permission to use."),
    COMMAND_HELP_ENCHANT_HOVER("&6&l[&e&l!&6&l] &eClick to view a list of custom enchants on this server."),
    COMMAND_HELP_PLUGIN_VANILLA_CHANGES_HOVER("&6&l[&e&l!&6&l] &eClick to view a list of all the notable vanilla changes in the server."),
    COMMAND_RANKUP_ADVANCEMENT_PROGRESS("&a&lAdvancements&7: &8[{0}&8] &2{1}&7/&2{2}"),
    COMMAND_RANKUP_NEXT_RANK("&9&lNext Rank&7: {0}"),
    COMMAND_RANKUP_REWARDS("&2&lRewards&7:\n&e- &aEXP&7: &e{0}\n&e- &aCash&7: &6${1}"),
    COMMAND_RANKUP_REWARD_CASH("&aThe amount &6${0} &ahas been deposited into your account!"),
    COMMAND_RANKUP_REWARD_EXP("&aYou have been rewarded &2{0} EXP&a!"),
    COMMAND_RANKSET_SUCCESSFUL("&aThe rank &6{0} &awas successfully set for the player &6{1}&a!"),
    COMMAND_RANKLIST_SERVER_RANKS("&6&lServer Ranks&7:"),
    COMMAND_RANKLIST_STAFF_RANKS("&b&lStaff Ranks&7:"),
    COMMAND_RANKLIST_PREMIUM_RANKS("&a&lPremium Ranks&7:"),
    COMMAND_VOTE_TOTAL_VOTES("&e&lTotal Votes: &a{0}"),
    COMMAND_VOTE_REWARDS("&e&lReward: &6$5,000"),
    COMMAND_VOTE_WEBSITE("&e&lWebsite:"),
    COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_TITLE("&e&lAdvancement Name&7: "),
    COMMAND_CHECK_ADVANCEMENT_ADVANCEMENT_DESCRIPTION("&e&lAdvancement Description&7: \n"),
    COMMAND_RANKUP_HOVER("&e&lTotal Advancements&7: &a{0}&7/&a{1}"),
    COMMAND_RANKUP_CONFIRM_RANKUP_MESSAGE("&6&l[&e&l!&6&l] &aClick confirm to rank up&7: "),
    COMMAND_RANKUP_CONFIRM_PRESTIGE("&6&l[&e&l!&6&l] &aClick confirm to prestige&7: "),
    COMMAND_RANKUP_CONFIRM_PRESTIGE_WARNING("&cYou will lose all your advancement progress if you prestige. Your rank will also be set to Nomad. Each prestige you'll earn &bx0.1&c more &2EXP&c and &6money &cuntil you max out at &bx1.0 &cwhich is double the amount."),
    COMMAND_RANKUP_PRESTIGE_BROADCAST("&2Player &6{0} &2is now prestige &a&l{1}&2!"),
    COMMAND_RANKUP_CONFIRM_BUTTON("&6[&e&lCONFIRM&6]"),
    COMMAND_RANKUP_CONFIRM_RANKUP_HOVER("&aClick to rank up to {0}&a!"),
    COMMAND_RANKUP_CONFIRM_PRESTIGE_HOVER("&aClick to prestige to level {0}&a!"),
    COMMAND_RANKUP_BROADCAST("&aPlayer &6{0} &ahas ranked up to {1}&a!"),
    COMMAND_RANKUP_CHECK("&aAdvancement check completed successfully! You currently have &2{0}&7/&2{1} &aadvancements completed."),
    COMMAND_SORT_SUCCESSFUL("&aYou successfully sorted the &3{0} Container &ain front of you."),
    COMMAND_MONEY_GIVE("&aThe player &6{0} &ahas been sent &6${1}&a!"),
    COMMAND_MONEY_GIVE_TARGET("&aYou have received &6${0}&a!"),
    COMMAND_RANKLIST_HAS_LINE("&3{0}&b. &2> {1} &2<"),
    COMMAND_RANKLIST_LINE("&3{0}&b. {1}"),
    COMMAND_MONEY_SET("&aThe player &6{0} &abalance has been set to &6${1}&a."),
    COMMAND_MONEY_SET_TARGET("&aYour balance has been set to &6${0}&a."),
    COMMAND_MONEY_REMOVE("&aThe player &6{0} &ahad &6${1} &aremoved from their balance."),
    COMMAND_MONEY_REMOVE_TARGET("&aYour balance has just been deducted by &6${0}&a."),
    COMMAND_WEATHER_CLEAR("&aThe weather has been &ecleared&a!"),
    COMMAND_WEATHER_RAIN("&aThe weather has been changed to &erain&a."),
    COMMAND_WEATHER_THUNDER("&aThe weather has been changed to &ethunderstorm&a."),
    COMMAND_CLEAR("&aThe player &6{0} &ainventory was successfully cleared."),
    ERROR_PREVIOUS_PAGE("&7You are already on the first page."),
    ERROR_NEXT_PAGE("&7You are on the last page."),
    ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
    ERROR_SORT_NO_CONTAINER("&cSadly a container could not be found in front of you."),
    ERROR_SORT_INTERACT("&cYou can't interact with that container so sorting was canceled."),
    ERROR_RANDOMTELEPORT_LOCATION_NOT_FOUND("&cPlease try again, no safe location could be found."),
    ERROR_COMMAND_CHECK_ADVANCEMENT_NOT_ADVANCEMENT("&7The input &c{0} &7is not a advancement key name."),
    ERROR_SELL_NOT_SELLABLE("&cThe item you're currently holding can't be sold to the server. Use &6/worth list &cfor a list of items that can be sold to the server."),
    ERROR_ADDPERM_HAS_PERM("&cThe player &6{0} &calready has the permission &b{1}&c."),
    ERROR_REMOVEPERM_DOES_NOT_HAVE("&cThe player &6{0} &cdoes not have the permission &b{1}&c."),
    ERROR_HOME_NOT_SAVED("&cThe name &e{0} &cis not a saved home."),
    ERROR_HOME_BED_NOT_SAVED("&cYou currently don't have a bed."),
    ERROR_HOME_NO_SAVED_HOMES("&cYou currently don't have any saved homes."),
    ERROR_HOME_MAX_HOMES("&cYou can't save anymore home locations, you have reached the max of &e{0} &chomes&c."),
    ERROR_HOME_SUPPORTED_WORLD("&cYou can't save a home in this world."),
    ERROR_SETHOME_BED("&cYou can't name a home bed or page, these are reserved names."),
    ERROR_HOME_ALREADY_SAVED("&cThe name &e{0} &cis already a saved home location."),
    ERROR_DELETEHOME_NOT_SAVED("&cThe name &e{0} &cis not a saved home."),
    ERROR_DELETEHOME_BED("&cYou can't delete your bed home, you just need to remove the bed or sleep in another bed."),
    ERROR_LIST_PAGE_NOT_NUMBER("&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    ERROR_ARMOR_STAND_EDIT("&cThat armor stand is currently being edited by another player."),
    ERROR_RANDOM_TELEPORT_DELAY("&7You will need to wait {0} &7to use random teleport again."),
    ERROR_RANDOM_WORLD_TYPE("&cYou can only use random teleport in normal worlds."),
    ERROR_EXP_BOTTLE_POINTS("&cYou need at least 8 exp to store some in a bottle!"),
    ERROR_COMMAND_BOOSTER("&cThere are currently no active boosters."),
    ERROR_COMMAND_BACK("&cYou currently don't have any saved back locations."),
    ERROR_COMMAND_TIME("&cThe time &e{0} &cis not an option."),
    ERROR_COMMAND_RANKUP_CONFIRM("&cYou only have &2{0} &cadvancements completed and you need &2{1} &cto rankup."),
    ERROR_COMMAND_PRESTIGE("&cYou only have &2{0} &cadvancements completed and you need &2{1} &cto prestige."),
    ERROR_COMMAND_TELEPORT_ALREADY_REQUESTED("&cYou already have a pending teleport request sent to &6{0}&c."),
    ERROR_COMMAND_TELEPORT_NOT_REQUESTING("&cThe player &6{0} &cis not currently requesting teleportation."),
    ERROR_COMMAND_MONEY_VALUE("&cThe input &6{0} &cis not a number."),
    ERROR_COMMAND_WRONG_COMMAND_ARG("&cThe input &6{0} &cis not a option for this command."),
    ERROR_COMMAND_PAY_INSUFFICIENT_FUNDS("&cYou can't send &e{0} &6${1} &cbecause you only have &6${2}&c."),
    ERROR_COMMAND_PAY_SELF("&cYou can't pay yourself."),
    ERROR_COMMAND_PAY_NOT_NUMBER("&cThe input &6{0} &cis not a whole number. &a&lUse&7: &e(1)"),
    ERROR_COMMAND_TELEPORT_TO_SELF("&cYou can't teleport to yourself."),
    ERROR_COMMAND_MESSAGE_TO_SELF("&cYou can't message yourself."),
    ERROR_COMMAND_TELEPORT_REQUEST_EXPIRED("&7Your teleport request to &6{0} &7has now expired."),
    ERROR_COMMAND_TRADE_REQUEST_EXPIRED("&7Your trade request to &6{0} &7has now expired."),
    ERROR_COMMAND_DUEL_REQUEST_EXPIRED("&7Your duel request to &6{0} &7has now expired."),
    ERROR_COMMAND_DUEL_DUEL_SELF("&cYou can't duel yourself."),
    ERROR_COMMAND_TRADE_TRADE_SELF("&cYou can't trade yourself."),
    ERROR_COMMAND_TRADE_IS_ALREADY_TRADING("&cYou are already in a trade."),
    ERROR_COMMAND_NOT_ARG("&cThe input {0} is not a valid arg for this command."),
    ERROR_COMMAND_TRADE_TARGET_IS_ALREADY_TRADING("&cThe player &6{0} &cis currently in a trade with another player."),
    ERROR_COMMAND_TRADE_REQUEST_ALREADY_SENT("&cYou already have a pending trade request for the player &6{0}&c."),
    ERROR_COMMAND_TRADE_NO_PENDING_REQUEST("&cThe player &6{0} &cdid not send you a trade request."),
    ERROR_COMMAND_DUEL_REQUEST_ALREADY_SENT("&cYou already have a pending duel request for the player &6{0}&c."),
    ERROR_COMMAND_DUEL_REQUEST_IN_DUEL("&cThe player &6{0} &cis already dueling a player."),
    ERROR_COMMAND_DUEL_REQUEST_YOUR_IN_DUEL("&cYou're currently in a duel."),
    ERROR_COMMAND_DUEL_NO_PENDING_REQUEST("&cThe player &6{0} &cdid not send you a duel request."),
    ERROR_COMMAND_TELEPORTDENY_DENIED("&cSadly the player &6{0} &chas denied your teleport request."),
    ERROR_COMMAND_REPLY_NO_PLAYER("&cNo player was found for a reply."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_CHAIR_OCCUPIED("&cThe player {0} is already sitting on this block!"),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    ERROR_COMMAND_WORLD_NOT_FOUND("&cThe world &6{0} &ccould not be found."),
    ERROR_CHUNK_MAX_ENTITIES("&cYou can only have {0} of each entity in each chunk."),
    ERROR_COMMAND_FLYSPEED_LIMIT("&cYou can only adjust your fly speed between 1 and 10."),
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
    MENU_COLOR_TITLE("&9&lName Color"),
    MENU_HOME_TITLE("&2&lHomes"),
    MENU_HOME_CLOCK_NAME("&a&lMax Homes"),
    MENU_HOME_CLOCK_LORE("&6Time Played&7: &2{0}\n&6Accrued Timer&7: &21 home every {1} &2played\n&6Default Homes&7: &2{2}\n&6Accrued Homes&7: &2&2{3}&7/&2{4}\n&6Total Homes&7: &2{5}&7/&2{6}"),
    MENU_HOME_ITEM_HOME_NAME("&e&l{0}"),
    MENU_TRADE_TITLE("&2&lTrading Interface"),
    MENU_RESOURCE_WORLDS_TITLE("&2&lResource Worlds"),
    MENU_SPAWNER_TITLE("&e&lSpawner Shop"),
    MENU_BOT_CHECKER_TITLE("&e&lPlayer Verification"),
    MENU_WELCOME_TITLE("&2&lJourney Survival"),
    MENU_BOT_CHECKER_ITEM_LORE("&7Click to verify you're not a bot."),
    MENU_BOT_CHECKER_ITEM_NAME("&6&lPlayer Verification"),
    MENU_RESOURCE_WORLD_LOCKED("&cYou need to unlock the advancement for entering this world type before you can use this resource world."),
    MENU_RESOURCE_WORLD_CLOCK_NAME("&d&lNext Reset&7: {0}"),
    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        String value = string;
        if (variables == null || variables.length == 0) return pu.format(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return pu.format(value);
    }

    public Component getComponent(String[] variables) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        String value = string;
        if (variables == null || variables.length == 0) return pu.formatC(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return pu.formatC(value);
    }
}
