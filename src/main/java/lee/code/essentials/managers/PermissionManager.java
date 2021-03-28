package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.Cache;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public class PermissionManager {

    @Getter private final List<String> defaultPerms = new ArrayList<>();

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
                for (String perm : getDefaultPerms()) attachment.setPermission(perm, true);
                break;

            case "MOD":

                break;

            case "ADMIN":

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

        for (String perm : getDefaultPerms()) {
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
        defaultPerms.add("essentials.command.spawn");
        defaultPerms.add("chunk.command.maxclaims");
        defaultPerms.add("chunk.command.chunk");
        defaultPerms.add("pets.command.test");
    }
}
