package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lee.code.essentials.lists.PremiumRankList;
import lee.code.essentials.lists.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public class PermissionManager {

    private final List<String> defaultPerms = new ArrayList<>();
    private final List<String> staffPerms = new ArrayList<>();
    private final List<String> vipPerms = new ArrayList<>();
    private final List<String> mvpPerms = new ArrayList<>();
    private final List<String> elitePerms = new ArrayList<>();

    public void register(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        UUID uuid = player.getUniqueId();

        PermissionAttachment attachment = player.addAttachment(plugin);
        if (!player.isOp()) {
            attachment.getPermissions().clear();

            for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) attachment.setPermission(perm.getPermission(), false);

            RankList rank = RankList.valueOf(cache.getRank(uuid));
            switch (rank) {
                case MOD:
                case ADMIN:
                    for (String perm : defaultPerms) attachment.setPermission(perm, true);
                    for (String perm : staffPerms) attachment.setPermission(perm, true);
                    break;
                default: for (String perm : defaultPerms) attachment.setPermission(perm, true);
            }
            for (String perm : cache.getPerms(uuid)) attachment.setPermission(perm, true);
        } else for (String perm : defaultPerms) attachment.setPermission(perm, true);

        player.recalculatePermissions();
        player.updateCommands();
    }

    public Collection<String> getCommands(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Collection<String> commands = new HashSet<>();
        CommandMap commandMap = plugin.getServer().getCommandMap();
         for (Map.Entry<String, Command> sCommand : commandMap.getKnownCommands().entrySet()) {
             String perm = sCommand.getValue().getPermission();
             if (perm != null && player.hasPermission(perm)) {
                 Command command = sCommand.getValue();
                 List<String> aliases = command.getAliases();
                 commands.addAll(aliases);
                 commands.add(command.getName());
             }
         }
        return commands;
    }

    public void loadPerms() {

        //bukkit
        defaultPerms.add("bukkit.command.tps");
        defaultPerms.add("bukkit.command.ping");
        defaultPerms.add("allow.ride.dolphin");
        defaultPerms.add("allow.special.dolphin");

        // essentials
        defaultPerms.add("essentials.command.spawn");
        defaultPerms.add("essentials.command.balance");
        defaultPerms.add("essentials.command.balancetop");
        defaultPerms.add("essentials.command.playtime");
        defaultPerms.add("essentials.command.playtimetop");
        defaultPerms.add("essentials.command.ranklist");
        defaultPerms.add("essentials.command.reply");
        defaultPerms.add("essentials.command.message");
        defaultPerms.add("essentials.command.teleport");
        defaultPerms.add("essentials.command.rankup");
        defaultPerms.add("essentials.command.worth");
        defaultPerms.add("essentials.command.sell");
        defaultPerms.add("essentials.command.sellall");
        defaultPerms.add("essentials.command.rules");
        defaultPerms.add("essentials.command.help");
        defaultPerms.add("essentials.command.home");
        defaultPerms.add("essentials.command.deletehome");
        defaultPerms.add("essentials.command.sethome");
        defaultPerms.add("essentials.command.booster");
        defaultPerms.add("essentials.command.teleportaccept");
        defaultPerms.add("essentials.command.teleportdeny");
        defaultPerms.add("essentials.command.colors");
        defaultPerms.add("essentials.command.back");
        defaultPerms.add("essentials.command.motd");
        defaultPerms.add("essentials.command.pay");
        defaultPerms.add("essentials.command.resourceworlds");
        defaultPerms.add("essentials.command.seen");
        defaultPerms.add("essentials.command.afk");
        defaultPerms.add("essentials.command.vote");
        defaultPerms.add("essentials.command.randomteleport");
        defaultPerms.add("essentials.command.trade");
        defaultPerms.add("essentials.command.checkadvancement");
        defaultPerms.add("essentials.command.duel");
        defaultPerms.add("essentials.command.sort");
        defaultPerms.add("essentials.command.votetop");
        defaultPerms.add("essentials.command.bottlexp");

        // chunks
        defaultPerms.add("chunk.command.chunk");
        defaultPerms.add("chunk.command.teleport");
        defaultPerms.add("chunk.command.abandonallclaims");
        defaultPerms.add("chunk.command.autoclaim");
        defaultPerms.add("chunk.command.buy");
        defaultPerms.add("chunk.command.list");
        defaultPerms.add("chunk.command.claim");
        defaultPerms.add("chunk.command.info");
        defaultPerms.add("chunk.command.manage");
        defaultPerms.add("chunk.command.map");
        defaultPerms.add("chunk.command.maxclaims");
        defaultPerms.add("chunk.command.setprice");
        defaultPerms.add("chunk.command.trust");
        defaultPerms.add("chunk.command.trustall");
        defaultPerms.add("chunk.command.trusted");
        defaultPerms.add("chunk.command.unclaim");
        defaultPerms.add("chunk.command.untrust");
        defaultPerms.add("chunk.command.untrustall");

        // shops
        defaultPerms.add("shop.command.shop");
        defaultPerms.add("shop.command.purchases");
        defaultPerms.add("shop.command.setspawn");
        defaultPerms.add("shop.command.removespawn");
        defaultPerms.add("shop.command.signhelp");
        defaultPerms.add("shop.command.spawn");
        defaultPerms.add("shop.command.spawner");
        defaultPerms.add("shop.command.daily");

        // locks
        defaultPerms.add("lock.command.lock");
        defaultPerms.add("lock.command.signhelp");
        defaultPerms.add("lock.command.remove");
        defaultPerms.add("lock.command.add");

        // pets
        defaultPerms.add("pets.command.pets");

        // trails
        defaultPerms.add("trails.command.trails");
        defaultPerms.add("trails.use.normal");

        // staff
        staffPerms.add("essentials.command.ban");
        staffPerms.add("essentials.command.unban");
        staffPerms.add("essentials.command.tempban");
        staffPerms.add("essentials.command.mute");
        staffPerms.add("essentials.command.tempmute");
        staffPerms.add("essentials.command.vanish");
        staffPerms.add("essentials.command.fly");
        staffPerms.add("essentials.command.kick");
        staffPerms.add("essentials.command.staffchat");
        staffPerms.add("essentials.command.invsee");
        staffPerms.add("essentials.command.enderchest");
        staffPerms.add("lock.command.admin");

        // vip
        vipPerms.add("essentials.command.glow");
        vipPerms.add("pets.use.sheep");
        vipPerms.add("pets.use.pink_sheep");
        vipPerms.add("pets.use.parrot");
        vipPerms.add("pets.use.red_parrot");
        vipPerms.add("pets.use.pig");
        vipPerms.add("trails.use.redstone");
        vipPerms.add("trails.use.flame");
        vipPerms.add("trails.use.halo");
        vipPerms.add("trails.use.block_break");

        //mvp
        mvpPerms.addAll(vipPerms);
        mvpPerms.add("essentials.command.namecolor");
        mvpPerms.add("chunk.command.fly");
        mvpPerms.add("pets.use.panda");
        mvpPerms.add("pets.use.playful_panda");
        mvpPerms.add("pets.use.fox");
        mvpPerms.add("pets.use.red_fox");
        mvpPerms.add("pets.use.cow");
        mvpPerms.add("pets.use.brown_and_white_cow");
        mvpPerms.add("pets.use.villager");
        mvpPerms.add("pets.use.swamp_villager");
        mvpPerms.add("pets.use.goat");
        mvpPerms.add("pets.use.axolotl");
        mvpPerms.add("pets.use.leucistic_axolotl");
        mvpPerms.add("trails.use.soul_fire_flame");
        mvpPerms.add("trails.use.villager_happy");
        mvpPerms.add("trails.use.drip_lava");
        mvpPerms.add("trails.use.enchantment_table");
        mvpPerms.add("trails.use.snowflake");
        mvpPerms.add("trails.use.crit");
        mvpPerms.add("trails.use.halo");
        mvpPerms.add("trails.use.helix");
        mvpPerms.add("trails.use.arrow_shoot");
        mvpPerms.add("trails.use.snowball_throw");

        //elite
        elitePerms.addAll(mvpPerms);
        elitePerms.add("pets.use.desert_villager");
        elitePerms.add("pets.use.savanna_villager");
        elitePerms.add("pets.use.snow_fox");
        elitePerms.add("pets.use.llama");
        elitePerms.add("pets.use.brown_llama");
        elitePerms.add("pets.use.gold_axolotl");
        elitePerms.add("pets.use.blue_axolotl");
        elitePerms.add("pets.use.cat");
        elitePerms.add("pets.use.all_black_cat");
        elitePerms.add("pets.use.red_cat");
        elitePerms.add("pets.use.polar_bear");
        elitePerms.add("trails.use.magic_crit");
        elitePerms.add("trails.use.nautilus");
        elitePerms.add("trails.use.drip_water");
        elitePerms.add("trails.use.portal");
        elitePerms.add("trails.use.wax_on");
        elitePerms.add("trails.use.wax_off");
        elitePerms.add("trails.use.spit");
        elitePerms.add("trails.use.totem");
        elitePerms.add("trails.use.dragon_breath");
        elitePerms.add("trails.use.spinning_helix");
        elitePerms.add("trails.use.sphere");
        elitePerms.add("trails.use.cone");
        elitePerms.add("trails.use.trident_throw");
        elitePerms.add("trails.use.ender_pearl_throw");
    }

    public void addPremiumPerms(UUID uuid, PremiumRankList rank) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        switch (rank) {
            case VIP -> cache.addPermList(uuid, vipPerms);
            case MVP -> cache.addPermList(uuid, mvpPerms);
            case ELITE -> cache.addPermList(uuid, elitePerms);
        }
    }
}
