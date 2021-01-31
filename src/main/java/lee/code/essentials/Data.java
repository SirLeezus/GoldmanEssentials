package lee.code.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<String> worldNames = new ArrayList<>();
    public List<String> getWorlds() {
        return worldNames;
    }

    public void loadWorldNames() {
        for (World selectedWorld : Bukkit.getWorlds()) {
            worldNames.add(selectedWorld.getName());
        }
    }
}
