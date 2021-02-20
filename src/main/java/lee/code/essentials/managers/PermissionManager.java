package lee.code.essentials.managers;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.database.SQLite;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class PermissionManager {

    public void register(Player player) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        SQLite SQL = plugin.getSqLite();
        UUID uuid = player.getUniqueId();

        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.getPermissions().clear();

        String group = SQL.getRank(uuid);

        //TODO add perms and setrank command

        switch (group) {

            case "DEFAULT":
                attachment.setPermission("essentials.command.spawn", true);
                attachment.setPermission("pets.command.use", true);
                attachment.setPermission("pets.use.sheep", true);
                attachment.setPermission("pets.use.whitesheep", true);
                attachment.setPermission("chunk.command.maxclaims", true);
                attachment.setPermission("chunk.command.use", true);
                attachment.setPermission("chunk.command.claim", true);
                break;

            case "MOD":

                break;

            case "ADMIN":

                break;

            case "OWNER":

                break;
        }

        player.updateCommands();
    }
}
