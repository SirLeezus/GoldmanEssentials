package lee.code.essentials.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum IntegerValues {
    //CLICK_DELAY("general-settings.click-delay", 5),
    ;

    @Getter private final String path;
    @Getter private final int def;
    @Setter private static FileConfiguration file;

    public int getDefault() {
        return this.def;
    }

    public int getConfigValue() {
        return file.getInt(this.path, this.def);
    }
}