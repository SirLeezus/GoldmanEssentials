package lee.code.essentials.nms;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

public class CustomChair extends ArmorStand {

    public CustomChair(Location loc) {
        super(EntityType.ARMOR_STAND, ((CraftWorld)loc.getWorld()).getHandle());

        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setMarker(true);
        this.setInvisible(true);
        this.setSmall(true);
        this.setCustomName(new TextComponent("chair"));
    }

    //prevent saving... I think...
    @Override
    public boolean save(CompoundTag nbttagcompound) {
        return false;
    }

    @Override
    public float tickHeadTurn(float f, float f1) {
        if (this.getPassengers().size() == 0) {
            this.dead = true;
            return 0.0F;
        }

        if (this.getPassengers().get(0) != null) {
            if (this.getPassengers().get(0) instanceof Player) {
                this.xRotO = (this.animationPosition = (this.getPassengers().get(0)).getYRot());
                this.yRotO = ((this.getPassengers().get(0)).getXRot() * 0.5F);
                setRot(this.xRotO, this.yRotO);
                this.run = this.xRotO;
            }
        }
        return 0.0F;
    }
}
