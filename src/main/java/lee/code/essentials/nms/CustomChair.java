package lee.code.essentials.nms;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class CustomChair extends EntityArmorStand {

    public CustomChair(Location loc) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)loc.getWorld()).getHandle());
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

    @Override
    public float f(float f, float f1) {
        if (this.passengers.size() == 0) {
            this.dead = true;
            return 0.0F;
        }

        if (this.passengers.get(0) != null) {
            if(this.passengers.get(0) instanceof EntityHuman) {
                this.lastYaw = (this.yaw = (this.passengers.get(0)).yaw);
                this.pitch = ((this.passengers.get(0)).pitch * 0.5F);
                setYawPitch(this.yaw, this.pitch);
                this.aK = this.yaw;
            }
        }
        return 0.0F;
    }
}
