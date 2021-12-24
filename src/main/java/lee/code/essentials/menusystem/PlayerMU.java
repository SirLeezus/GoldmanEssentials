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
    @Setter private UUID trading;
    @Getter @Setter private boolean ownerTrading;
    @Getter @Setter private boolean traderTrading;
    @Getter @Setter private ArmorStand armorStand;
    @Getter @Setter private int page;
    @Getter @Setter private boolean isOwnerTradeConfirmed;
    @Getter @Setter private boolean isTraderTradeConfirmed;
    public Player getOwner() { return Bukkit.getPlayer(owner); }
    public UUID getOwnerUUID() { return owner; }
    public Player getTrader() { return Bukkit.getPlayer(trading); }
    public UUID getTraderUUID() { return trading; }
}
