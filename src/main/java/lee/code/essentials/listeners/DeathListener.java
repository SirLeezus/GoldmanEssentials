package lee.code.essentials.listeners;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeathMessage(PlayerDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Component dm = e.deathMessage();
        if (dm != null) plugin.getServer().sendMessage(Lang.DEATH_PREFIX.getComponent(null).append(dm).append(Component.text(".")).color(NamedTextColor.GRAY));
    }
}