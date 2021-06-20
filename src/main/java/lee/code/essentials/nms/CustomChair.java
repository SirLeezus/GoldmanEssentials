package lee.code.essentials.nms;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

public class CustomChair extends EntityArmorStand {

    public CustomChair(Location loc) {
        super(EntityTypes.c, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setMarker(true);
        this.setInvisible(true);
        this.setSmall(true);
        this.setCustomName(new ChatComponentText("chair"));
    }

    @Override
    public NBTTagCompound save(NBTTagCompound nbttagcompound){
        return nbttagcompound;
    }

    // Key Map
    // be = dead
    // x = yaw
    // y = pitch
    // bh = run
    // aT = animationPosition

    @Override
    public float e(float e, float e1) {
        if (this.getPassengers().size() == 0) {
            this.be = true;
            return 0.0F;
        }

        if (this.getPassengers().get(0) != null) {
            if(this.getPassengers().get(0) instanceof EntityHuman) {
                this.x = (this.aT = (this.getPassengers().get(0)).getYRot());
                this.y = ((this.getPassengers().get(0)).getXRot() * 0.5F);
                setYawPitch(this.x, this.y);
                this.bh = this.x;
            }
        }
        return 0.0F;
    }
}
