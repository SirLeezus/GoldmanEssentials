package lee.code.essentials.permissions;

import lee.code.essentials.TheEssentials;
import lee.code.essentials.database.SQLite;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class PermissionManager {

    public void register(Player player) {
        TheEssentials plugin = TheEssentials.getPlugin();
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
