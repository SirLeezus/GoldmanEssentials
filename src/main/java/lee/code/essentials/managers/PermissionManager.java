package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public class PermissionManager {

    private final List<String> perms = new ArrayList<>();
    private final List<String> defaultPerms = new ArrayList<>();
    private final List<String> staffPerms = new ArrayList<>();

    public void register(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();
        UUID uuid = player.getUniqueId();

        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.getPermissions().clear();

        for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) attachment.setPermission(perm.getPermission(), false);

        String rank = cache.getRank(uuid);

        switch (rank) {

            case "NOMAD":
            case "ADVENTURER":
            case "CONQUER":
            case "HERO":
            case "LEGEND":
            case "MYTH":
            case "IMMORTAL":
            case "GOD":
                for (String perm : defaultPerms) attachment.setPermission(perm, true);
                break;

            case "MOD":
            case "ADMIN":
                for (String perm : defaultPerms) attachment.setPermission(perm, true);
                for (String perm : staffPerms) attachment.setPermission(perm, true);
                break;
        }

        for (String perm : cache.getPerms(uuid)) attachment.setPermission(perm, true);
        player.recalculatePermissions();
        player.updateCommands();
    }

    public Collection<String> getCommands(UUID uuid) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Cache cache = plugin.getCache();

        Collection<String> commands = new ArrayList<>(Collections.singleton(""));

        for (String perm : perms) {
            String command = perm.substring(perm.lastIndexOf(".") + 1);
            Command registeredCommand = plugin.getServer().getCommandMap().getCommand(command);
            if (registeredCommand != null) {
                List<String> aliases = registeredCommand.getAliases();
                commands.addAll(aliases);
                commands.add(command);
            }
        }

        for (String perm : cache.getPerms(uuid)) {
            String command = perm.substring(perm.lastIndexOf(".") + 1);
            Command registeredCommand = plugin.getServer().getCommandMap().getCommand(command);
            if (registeredCommand != null) {
                List<String> aliases = registeredCommand.getAliases();
                commands.addAll(aliases);
                commands.add(command);
            }
        }
        return commands;
    }

    public void loadPerms() {

        // essentials
        defaultPerms.add("essentials.command.spawn");
        defaultPerms.add("essentials.command.balance");
        defaultPerms.add("essentials.command.balancetop");
        defaultPerms.add("essentials.command.playtime");
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

        // locks
        defaultPerms.add("lock.command.lock");
        defaultPerms.add("lock.command.signhelp");
        defaultPerms.add("lock.command.remove");
        defaultPerms.add("lock.command.add");

        // pets
        defaultPerms.add("pets.command.test");

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

        perms.addAll(defaultPerms);
        perms.addAll(staffPerms);
    }
}
