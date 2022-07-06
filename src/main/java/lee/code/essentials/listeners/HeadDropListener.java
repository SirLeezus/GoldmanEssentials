package lee.code.essentials.listeners;

import lee.code.essentials.Data;
import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.PU;
import lee.code.essentials.lists.EntityHeads;
import lee.code.essentials.lists.Settings;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadDropListener implements Listener {

    @EventHandler
    public void onHeadDrop(EntityDeathEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        PU pu = plugin.getPU();
        Data data = plugin.getData();
        Entity entity = e.getEntity();

        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if (entity instanceof Player player) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                headMeta.setOwningPlayer(player);
                head.setItemMeta(headMeta);
                player.getWorld().dropItemNaturally(player.getLocation(), head);
            } else if (entity instanceof EnderDragon) {
                ItemStack dragonHead = new ItemStack(Material.DRAGON_HEAD);
                if (!e.getDrops().contains(dragonHead)) e.getDrops().add(dragonHead);
            } else if (entity instanceof Warden) {
                e.getDrops().add(EntityHeads.valueOf(e.getEntityType().name()).getHead());
            } else {
                if (pu.headDropRNG() >= Settings.HEAD_DROP_RNG.getValue() || killer.getGameMode().equals(GameMode.CREATIVE)) {
                    String type = e.getEntityType().name();
                    if (entity instanceof Sheep sheep) {
                        if (sheep.name().equals(Component.text("jeb_"))) {
                            type = "RAINBOW_" + type;
                        } else {
                            DyeColor color = sheep.getColor();
                            if (color != null) type = color.name() + "_" + type;
                            else type = "WHITE_" + type;
                        }
                    } else if (entity instanceof Axolotl axolotl) {
                        Axolotl.Variant variant = axolotl.getVariant();
                        type = variant.name() + "_" + type;
                    } else if (entity instanceof Parrot parrot) {
                        Parrot.Variant variant = parrot.getVariant();
                        type = variant.name() + "_" + type;
                    } else if (entity instanceof Llama llama) {
                        if (entity instanceof TraderLlama traderLlama) {
                            Llama.Color color = traderLlama.getColor();
                            type = color.name() + "_" + type;
                        } else {
                            Llama.Color color = llama.getColor();
                            type = color.name() + "_" + type;
                        }
                    } else if (entity instanceof Villager villager) {
                        Villager.Profession villagerProfession = villager.getProfession();
                        Villager.Type villagerType = villager.getVillagerType();
                        if (villagerProfession != Villager.Profession.NONE) {
                            type = villagerType.name() + "_" + villagerProfession.name() + "_" + type;
                        } else type = villagerType.name() + "_" + type;
                    } else if (entity instanceof MushroomCow mushroomCow) {
                        MushroomCow.Variant variant = mushroomCow.getVariant();
                        type = variant.name() + "_" + type;
                    } else if (entity instanceof Frog frog) {
                        Frog.Variant variant = frog.getVariant();
                        type = variant.name() + "_" + type;
                    }
                    if (data.getEntityHeadKeys().contains(type)) {
                        ItemStack head = EntityHeads.valueOf(type).getHead();
                        entity.getWorld().dropItemNaturally(entity.getLocation(), head);
                    }
                }
            }
        }
    }
}
