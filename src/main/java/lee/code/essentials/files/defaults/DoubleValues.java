package lee.code.essentials.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum DoubleValues {
    SERVER_SPAWN_X( "spawn.x",0.00),
    SERVER_SPAWN_Y( "spawn.y",0.00),
    SERVER_SPAWN_Z( "spawn.z",0.00),
    SERVER_SPAWN_PITCH( "spawn.pitch",0.00),
    SERVER_SPAWN_YAW( "spawn.yaw",0.00),
    ;

    @Getter private final String path;
    @Getter private final double def;
    @Setter private static FileConfiguration file;

    public double getDefault() {
        return this.def;
    }

    public double getConfigValue() {
        return file.getDouble(this.path, this.def);
    }
}