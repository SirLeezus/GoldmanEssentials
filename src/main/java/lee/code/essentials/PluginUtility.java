package lee.code.essentials;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class PluginUtility {

    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public String formatAmount(int value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }
}
