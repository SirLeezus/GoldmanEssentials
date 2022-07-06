package lee.code.essentials.commands.cmds;

import lee.code.chunks.ChunkAPI;
import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SortCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        ChunkAPI chunkAPI = plugin.getChunkAPI();
        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            Block block = player.getTargetBlock(5);
            if (block != null && block.getState() instanceof Container container && isSupportedContainer(container)) {
                if (chunkAPI.canInteractInChunk(uuid, block.getChunk())) {
                    ArrayList<ItemStack> newInventory = new ArrayList<>();
                    for (ItemStack item : container.getInventory().getContents()) {
                        if (item != null && !item.getType().equals(Material.AIR)) newInventory.add(item);
                    }
                    container.getInventory().clear();

                    ItemStack[] arrayNewItems = newInventory.toArray(new ItemStack[0]);
                    Arrays.sort(arrayNewItems, (o1, o2) -> {
                        String item1 = o1.getType().name();
                        String item2 = o2.getType().name();
                        return item1.compareTo(item2);
                    });
                    for (ItemStack item : arrayNewItems) container.getInventory().addItem(item);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SORT_SUCCESSFUL.getComponent(new String[] { BukkitUtils.parseCapitalization(block.getType().name()) })));
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SORT_INTERACT.getComponent(null)));
            } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SORT_NO_CONTAINER.getComponent(null)));
        } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
        return true;
    }

    private boolean isSupportedContainer(BlockState state) {
        return state instanceof Chest || state instanceof ShulkerBox || state instanceof Barrel;
    }
}


