package lee.code.essentials.menusystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerMU {

    private final UUID owner;
    @Getter @Setter private ArmorStand armorStand;
    @Getter @Setter private int page;
    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }
}
