package lee.code.essentials.permissions;

import lee.code.essentials.TheEssentials;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class RegisterPermissions {

    public void registerDefault(Player player) {
        TheEssentials plugin = TheEssentials.getPlugin();

        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.getPermissions().clear();

        //default perms
        //TODO make groups
        attachment.setPermission("essentials.command.spawn", true);
        attachment.setPermission("pets.command.use", true);

        player.updateCommands();
    }
}
