package lee.code.essentials.lists;

import lee.code.essentials.GoldmanEssentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Broadcasts {
    a("&6Use a crafting table to lookup recipes, simply click the green book inside the crafting table menu!"),
    b("&6You can claim chunks as you walk with &e/chunk autoclaim &6or use &e/chunk claim &6to claim the chunk you're standing on!"),
    c("&6You can fly in your own or trusted chunks with the command &e/chunk fly&6! Comes with the MVP rank!"),
    e("&6Want to show some love? You can buy a booster which will multiply harvesting drops and mob drops for everyone, use the command &e/help store&6 for more info!"),
    f("&6Check out our store if you want a cute pet or trail, every purchase helps keep the server running! Use the command &e/help store&6 for more info!"),
    g("&6Press the \"L\" key on your keyboard to bring up your advancements! Keep in mind you need to unlock at least one for any to show."),
    h("&6Use the &e/rankup &6command to check your progress! When you're ready to rank up use the command &e/rankup confirm&6!"),
    i("&6Want to save a location? Use the command &e/sethome&6! You can also check all your saved locations with the command &e/homes&6!"),
    j("&6Right click the ground with a stick to bring up chunk info or use the command &e/chunk info&6!"),
    k("&6You can put a chunk up for sale with the command &e/chunk setprice&6!"),
    l("&6Manage chunk flags with command &e/chunk manage&6! You can also trust players with the command &e/chunk trust &6or &e/chunk trustall&6!"),
    m("&6You can edit a armor stand by sneak-right-clicking one! Use the menu to place items on it or change flags!"),
    n("&6All mobs in the game have a chance of dropping a head, including players!"),
    o("&6Learn how to create a chest shop with the command &e/shop signhelp&6! You can also set a spawn for your shop area with the command &e/shop setspawn&6!"),
    p("&6All spawners can be picked up using the enchantment silk touch on your pickaxe! Use the command &e/shop spawner&6 to view a list of spawners you can buy!"),
    q("&6You can lock any type of container on this server! Use the command &e/lock signhelp &6for more info!"),
    r("&6Looking for resources? Try our resource worlds with the command &e/resourceworlds&6! They reset every 24 hours!"),
    s("&6Did you know this server has custom enchantments? Run the command &e/help enchants&6 for more info!"),
    t("&6Request to teleport to a player using the command &e/tp&6! The other player will need to accept your request to teleport!"),
    u("&6You can view a list of all the possible ranks in the server using the command &e/ranklist&6!"),
    v("&6Check out all the changes made to vanilla with the command &e/help vanilla&6!"),
    w("&6You can sit on carpet or slabs by sneak-right-clicking them with no items in your hand!"),
    x("&6Use the command &e/shops &6to view all the player shops in the server!"),
    y("&6You can sell items to the server using the command &e/sell &6or &e/sellall&6! To check the worth of all the items that can be sold to the server run the command &e/worth list&6!"),
    z("&6You can use color codes in anvils, signs and books! Use the command &e/colors &6to view the color codes!"),
    ;

    @Getter private final String string;

    public Component getComponent() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        return plugin.getPU().formatC(string);
    }
}
