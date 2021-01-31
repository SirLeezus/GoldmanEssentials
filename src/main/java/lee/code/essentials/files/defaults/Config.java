package lee.code.essentials.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Config {

    //AMOUNT_FORMAT("general-settings.amount-format", "#,###"),
    SERVER_SPAWN_WORLD("spawn.world", "world"),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";

        String value = ChatColor.translateAlternateColorCodes('&', fileValue);

        if (args == null) return value;
        else if (args.length == 0) return value;

        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);

        return value;
    }
}