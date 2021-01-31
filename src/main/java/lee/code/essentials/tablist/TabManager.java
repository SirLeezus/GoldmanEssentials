package lee.code.essentials.tablist;

import lee.code.essentials.TheEssentials;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabManager {

    private final List<ChatComponentText> headers = new ArrayList<>();
    private final List<ChatComponentText> footers = new ArrayList<>();

    TheEssentials plugin = TheEssentials.getPlugin();

    public void showTab() {

        if (headers.isEmpty() && footers.isEmpty()) return;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            int count1 = 0; //header
            int count2 = 0; //footer

            @Override
            public void run() {

                try {

                    addFooter("&aOnline&2: " + Bukkit.getOnlinePlayers().size());

                    Field a = packet.getClass().getDeclaredField("header");
                    a.setAccessible(true);

                    Field b = packet.getClass().getDeclaredField("footer");
                    b.setAccessible(true);

                    if (count1 >= headers.size()) count1 = 0;
                    if (count2 >= footers.size()) count2 = 0;

                    a.set(packet, headers.get(count1));
                    b.set(packet, footers.get(count2));

                    if (Bukkit.getOnlinePlayers().size() != 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                        }
                    }

                    count1++;
                    count2++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, 10, 40);
    }

    public void addHeader(String header) {
        headers.add(new ChatComponentText(plugin.getPluginUtility().format(header)));
    }

    public void addFooter(String footer) {
        footers.clear();
        footers.add(new ChatComponentText(plugin.getPluginUtility().format(footer)));
    }

    public void loadTab() {
        addHeader("&2&lJourney Survival\n&a1.16.5");
        addHeader("&5&lJourney Survival\n&d1.16.5");
        addHeader("&e&lJourney Survival\n&61.16.5");

        showTab();
    }
}
