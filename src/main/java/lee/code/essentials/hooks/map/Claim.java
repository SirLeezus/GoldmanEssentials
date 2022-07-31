package lee.code.essentials.hooks.map;

import lombok.Getter;

import java.util.UUID;

public class Claim {
    @Getter private final int x;
    @Getter private final int z;
    @Getter private final UUID owner;
    @Getter private final boolean adminChunk;

    public Claim(int x, int z, UUID owner, boolean adminChunk) {
        this.x = x;
        this.z = z;
        this.owner = owner;
        this.adminChunk = adminChunk;
    }

    public boolean isTouching(Claim claim) {
        if (!owner.equals(claim.owner)) return false; // not same owner
        else if (claim.x == x && claim.z == z - 1) return true; // touches north
        else if (claim.x == x && claim.z == z + 1) return true; // touches south
        else if (claim.x == x - 1 && claim.z == z) return true; // touches west
        return claim.x == x + 1 && claim.z == z; // touches east
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof Claim)) return false;
        Claim other = (Claim) o;
        return this.x == other.x && this.z == other.z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Claim{x=" + x + ",z=" + z + ",owner=" + owner + ",admin=" + adminChunk + "}";
    }
}
