package lee.code.essentials.menusystem.menus;

import lee.code.essentials.GoldmanEssentials;
import lee.code.essentials.lists.Lang;
import lee.code.essentials.menusystem.Menu;
import lee.code.essentials.menusystem.PlayerMU;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

public class ArmorStandMenu extends Menu {

    public ArmorStandMenu(PlayerMU pmu, ArmorStand armorStand) {
        super(pmu);
        pmu.setArmorStand(armorStand);
    }

    @Override
    public String getMenuName() {
        return Lang.MENU_ARMOR_STAND_TITLE.getString(null);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        Player player = pmu.getOwner();

        if (!(e.getClickedInventory() == player.getInventory())) {
            if (plugin.getData().hasPlayerClickDelay(player.getUniqueId())) return;
            else plugin.getPU().addPlayerClickDelay(player.getUniqueId());

            int slot = e.getSlot();
            ItemStack clickedItem = e.getCurrentItem();
            ItemStack cursorItem = player.getItemOnCursor();
            ArmorStand armorStand = pmu.getArmorStand();
            EntityEquipment equipment = armorStand.getEquipment();

            if (armorStand.isDead()) {
                player.closeInventory();
                return;
            }

            if (equipment != null) {
                switch (slot) {
                    case 13:
                        equipmentChange(player, EquipmentSlot.HEAD, slot, equipment, cursorItem, clickedItem);
                        break;
                    case 22:
                        equipmentChange(player, EquipmentSlot.CHEST, slot, equipment, cursorItem, clickedItem);
                        break;
                    case 31:
                        equipmentChange(player, EquipmentSlot.LEGS, slot, equipment, cursorItem, clickedItem);
                        break;
                    case 40:
                        equipmentChange(player, EquipmentSlot.FEET, slot, equipment, cursorItem, clickedItem);
                        break;
                    case 21:
                        equipmentChange(player, EquipmentSlot.OFF_HAND, slot, equipment, cursorItem, clickedItem);
                        break;
                    case 23:
                        equipmentChange(player, EquipmentSlot.HAND, slot, equipment, cursorItem, clickedItem);
                        break;

                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                        if (clickedItem != null) updateSetting(player, armorStand, clickedItem, slot);
                        break;

                    case 3:
                    case 4:
                    case 5:
                    case 10:
                    case 11:
                    case 12:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                    case 19:
                    case 20:
                    case 24:
                    case 25:
                    case 26:
                    case 28:
                    case 29:
                    case 30:
                    case 32:
                    case 33:
                    case 34:
                    case 49:
                        double amount = 0.01;
                        if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) amount = 0.10;
                        if (e.getClick().isLeftClick()) amount = amount - amount - amount;
                        if (slot == 49) amount = amount * 50;
                        updatePosition(armorStand, slot, amount);
                        break;
                }
            }
        } else if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            e.setCancelled(true);
        } else if (e.getClickedInventory() == player.getInventory()) e.setCancelled(false);
    }

    @Override
    public void setMenuItems() {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();
        setFillerGlass();
        ArmorStand armorStand = pmu.getArmorStand();
        EntityEquipment equipment = armorStand.getEquipment();

        if (equipment != null) {
            ItemStack helmet = equipment.getHelmet();
            ItemStack chest = equipment.getChestplate();
            ItemStack pants = equipment.getLeggings();
            ItemStack boots = equipment.getBoots();
            ItemStack rightHand = equipment.getItemInMainHand();
            ItemStack leftHand = equipment.getItemInOffHand();

            ItemStack allow = new ItemStack(asSettingTrue);
            ItemStack deny = new ItemStack(asSettingFalse);
            ItemStack position = new ItemStack(asPosition);
            ItemStack headPosition = new ItemStack(asHeadPosition);
            ItemStack torsoPosition = new ItemStack(asTorsoPosition);
            ItemStack leftArmPosition = new ItemStack(asLeftArmPosition);
            ItemStack rightArmPosition = new ItemStack(asRightArmPosition);
            ItemStack leftLegPosition = new ItemStack(asLeftLegPosition);
            ItemStack rightLegPosition = new ItemStack(asRightLegPosition);
            ItemStack directionPosition = new ItemStack(asDirectionPosition);

            ItemMeta allowMeta = allow.getItemMeta();
            ItemMeta denyMeta = deny.getItemMeta();
            ItemMeta positionMeta = position.getItemMeta();
            ItemMeta headPositionMeta = headPosition.getItemMeta();
            ItemMeta torsoPositionMeta = torsoPosition.getItemMeta();
            ItemMeta leftArmPositionMeta = leftArmPosition.getItemMeta();
            ItemMeta rightArmPositionMeta = rightArmPosition.getItemMeta();
            ItemMeta leftLegPositionMeta = leftLegPosition.getItemMeta();
            ItemMeta rightLegPositionMeta = rightLegPosition.getItemMeta();
            ItemMeta directionPositionMeta = directionPosition.getItemMeta();

            String resultTrue = Lang.TRUE.getString(null);
            String resultFalse = Lang.FALSE.getString(null);

            if (armorStand.isInvulnerable()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_INVULNERABLE.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(45, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_INVULNERABLE.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(45, deny);
            }

            if (armorStand.hasArms()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_ARMS.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(46, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_ARMS.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(46, deny);
            }

            if (armorStand.hasBasePlate()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_PLATE.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(47, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_PLATE.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(47, deny);
            }

            if (armorStand.isSmall()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_SMALL.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(48, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_SMALL.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(48, deny);
            }

            if (armorStand.isVisible()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_VISIBLE.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(50, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_VISIBLE.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(50, deny);
            }

            if (armorStand.isCustomNameVisible()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_NAME_VISIBLE.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(51, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_NAME_VISIBLE.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(51, deny);
            }

            if (armorStand.isGlowing()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GLOWING.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(52, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GLOWING.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(52, deny);
            }

            if (armorStand.hasGravity()) {
                allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GRAVITY.getComponent(new String[] { resultTrue }));
                allow.setItemMeta(allowMeta);
                inventory.setItem(53, allow);
            } else {
                denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GRAVITY.getComponent(new String[] { resultFalse }));
                deny.setItemMeta(denyMeta);
                inventory.setItem(53, deny);
            }

            //POSITION LOCATION

            positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getX()) }));
            position.setItemMeta(positionMeta);
            inventory.setItem(3, position);

            positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getY()) }));
            position.setItemMeta(positionMeta);
            inventory.setItem(4, position);

            positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getZ()) }));
            position.setItemMeta(positionMeta);
            inventory.setItem(5, position);

            //POSITION HEAD

            headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getX()) }));
            headPosition.setItemMeta(headPositionMeta);
            inventory.setItem(10, headPosition);

            headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getY()) }));
            headPosition.setItemMeta(headPositionMeta);
            inventory.setItem(11, headPosition);

            headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getZ()) }));
            headPosition.setItemMeta(headPositionMeta);
            inventory.setItem(12, headPosition);

            //POSITION TORSO

            torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getX()) }));
            torsoPosition.setItemMeta(torsoPositionMeta);
            inventory.setItem(14, torsoPosition);

            torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getY()) }));
            torsoPosition.setItemMeta(torsoPositionMeta);
            inventory.setItem(15, torsoPosition);

            torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getZ()) }));
            torsoPosition.setItemMeta(torsoPositionMeta);
            inventory.setItem(16, torsoPosition);

            //POSITION LEFT ARM

            leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getX()) }));
            leftArmPosition.setItemMeta(leftArmPositionMeta);
            inventory.setItem(18, leftArmPosition);

            leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getY()) }));
            leftArmPosition.setItemMeta(leftArmPositionMeta);
            inventory.setItem(19, leftArmPosition);

            leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getZ()) }));
            leftArmPosition.setItemMeta(leftArmPositionMeta);
            inventory.setItem(20, leftArmPosition);

            //POSITION RIGHT ARM

            rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getX()) }));
            rightArmPosition.setItemMeta(rightArmPositionMeta);
            inventory.setItem(24, rightArmPosition);

            rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getY()) }));
            rightArmPosition.setItemMeta(rightArmPositionMeta);
            inventory.setItem(25, rightArmPosition);

            rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getZ()) }));
            rightArmPosition.setItemMeta(rightArmPositionMeta);
            inventory.setItem(26, rightArmPosition);

            //POSITION LEFT LEG

            leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getX()) }));
            leftLegPosition.setItemMeta(leftLegPositionMeta);
            inventory.setItem(28, leftLegPosition);

            leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getY()) }));
            leftLegPosition.setItemMeta(leftLegPositionMeta);
            inventory.setItem(29, leftLegPosition);

            leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getZ()) }));
            leftLegPosition.setItemMeta(leftLegPositionMeta);
            inventory.setItem(30, leftLegPosition);

            //POSITION RIGHT LEG

            rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getX()) }));
            rightLegPosition.setItemMeta(rightLegPositionMeta);
            inventory.setItem(32, rightLegPosition);

            rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getY()) }));
            rightLegPosition.setItemMeta(rightLegPositionMeta);
            inventory.setItem(33, rightLegPosition);

            rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getZ()) }));
            rightLegPosition.setItemMeta(rightLegPositionMeta);
            inventory.setItem(34, rightLegPosition);

            //POSITION DIRECTION

            directionPositionMeta.displayName(Lang.MENU_ARMOR_STAND_DIRECTION_POSITION.getComponent(new String[] { plugin.getPU().shortenDouble(armorStand.getLocation().getYaw()) }));
            CompassMeta compassMeta = (CompassMeta) directionPositionMeta;
            compassMeta.setLodestone(armorStand.getLocation().add(armorStand.getLocation().getDirection().setY(0).normalize().multiply(5)));
            compassMeta.setLodestoneTracked(true);
            directionPosition.setItemMeta(directionPositionMeta);
            inventory.setItem(49, directionPosition);

            //ARMOR STAND

            inventory.setItem(13, helmet);
            inventory.setItem(21, leftHand);
            inventory.setItem(22, chest);
            inventory.setItem(23, rightHand);
            inventory.setItem(31, pants);
            inventory.setItem(40, boots);
        }
    }

    private void equipmentChange(Player player, EquipmentSlot equipmentSlot, int inventorySlot, EntityEquipment equipment, ItemStack cursorItem, ItemStack clickedItem) {
        ItemStack air = new ItemStack(Material.AIR);
        if (!cursorItem.getType().equals(Material.AIR)) {
            if (clickedItem != null && !clickedItem.getType().equals(Material.AIR)) player.setItemOnCursor(clickedItem);
            else player.setItemOnCursor(air);
            equipment.setItem(equipmentSlot, cursorItem);
            inventory.setItem(inventorySlot, cursorItem);
        } else if (clickedItem != null && !clickedItem.getType().equals(Material.AIR)) {
            player.setItemOnCursor(clickedItem);
            equipment.setItem(equipmentSlot, air);
            inventory.setItem(inventorySlot, air);
        }
    }

    private void updateSetting(Player player, ArmorStand armorStand, ItemStack item, int slot) {

        ItemStack allow = new ItemStack(asSettingTrue);
        ItemStack deny = new ItemStack(asSettingFalse);

        ItemMeta allowMeta = allow.getItemMeta();
        ItemMeta denyMeta = deny.getItemMeta();

        String resultTrue = Lang.TRUE.getString(null);
        String resultFalse = Lang.FALSE.getString(null);

        //allow
        if (item.getType().equals(asSettingFalse.getType())) {
            switch (slot) {
                case 45:
                    armorStand.setInvulnerable(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_INVULNERABLE.getComponent(new String[] { resultTrue }));
                    break;
                case 46:
                    armorStand.setArms(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_ARMS.getComponent(new String[] { resultTrue }));
                    break;
                case 47:
                    armorStand.setBasePlate(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_PLATE.getComponent(new String[] { resultTrue }));
                    break;
                case 48:
                    armorStand.setSmall(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_SMALL.getComponent(new String[] { resultTrue }));
                    break;
                case 50:
                    armorStand.setVisible(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_VISIBLE.getComponent(new String[] { resultTrue }));
                    break;
                case 51:
                    armorStand.setCustomNameVisible(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_NAME_VISIBLE.getComponent(new String[] { resultTrue }));
                    break;
                case 52:
                    armorStand.setGlowing(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GLOWING.getComponent(new String[] { resultTrue }));
                    break;
                case 53:
                    armorStand.setGravity(true);
                    allowMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GRAVITY.getComponent(new String[] { resultTrue }));
                    break;
            }
            allow.setItemMeta(allowMeta);
            inventory.setItem(slot, allow);
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
            //deny
        } else if (item.getType().equals(asSettingTrue.getType())) {
            switch (slot) {
                case 45:
                    armorStand.setInvulnerable(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_INVULNERABLE.getComponent(new String[] { resultFalse }));
                    break;
                case 46:
                    armorStand.setArms(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_ARMS.getComponent(new String[] { resultFalse }));
                    break;
                case 47:
                    armorStand.setBasePlate(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_PLATE.getComponent(new String[] { resultFalse }));
                    break;
                case 48:
                    armorStand.setSmall(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_SMALL.getComponent(new String[] { resultFalse }));
                    break;
                case 50:
                    armorStand.setVisible(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_VISIBLE.getComponent(new String[] { resultFalse }));
                    break;
                case 51:
                    armorStand.setCustomNameVisible(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_NAME_VISIBLE.getComponent(new String[] { resultFalse }));
                    break;
                case 52:
                    armorStand.setGlowing(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GLOWING.getComponent(new String[] { resultFalse }));
                    break;
                case 53:
                    armorStand.setGravity(false);
                    denyMeta.displayName(Lang.MENU_ARMOR_STAND_SETTING_GRAVITY.getComponent(new String[] { resultFalse }));
                    break;
            }
            deny.setItemMeta(denyMeta);
            inventory.setItem(slot, deny);
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1, 1);
        }
    }

    private void updatePosition(ArmorStand armorStand, int slot, double amount) {
        GoldmanEssentials plugin = GoldmanEssentials.getPlugin();

        ItemStack position = new ItemStack(asPosition);
        ItemStack headPosition = new ItemStack(asHeadPosition);
        ItemStack torsoPosition = new ItemStack(asTorsoPosition);
        ItemStack leftArmPosition = new ItemStack(asLeftArmPosition);
        ItemStack rightArmPosition = new ItemStack(asRightArmPosition);
        ItemStack leftLegPosition = new ItemStack(asLeftLegPosition);
        ItemStack rightLegPosition = new ItemStack(asRightLegPosition);
        ItemStack directionPosition = new ItemStack(asDirectionPosition);

        ItemMeta positionMeta = position.getItemMeta();
        ItemMeta headPositionMeta = headPosition.getItemMeta();
        ItemMeta torsoPositionMeta = torsoPosition.getItemMeta();
        ItemMeta leftArmPositionMeta = leftArmPosition.getItemMeta();
        ItemMeta rightArmPositionMeta = rightArmPosition.getItemMeta();
        ItemMeta leftLegPositionMeta = leftLegPosition.getItemMeta();
        ItemMeta rightLegPositionMeta = rightLegPosition.getItemMeta();
        ItemMeta directionPositionMeta = directionPosition.getItemMeta();

        double pX = armorStand.getLocation().getX();
        double pY = armorStand.getLocation().getY();
        double pZ = armorStand.getLocation().getZ();
        float pPitch = armorStand.getLocation().getPitch();
        float pYaw = armorStand.getLocation().getYaw();

        double hX = armorStand.getHeadPose().getX();
        double hY = armorStand.getHeadPose().getY();
        double hZ = armorStand.getHeadPose().getZ();

        double tX = armorStand.getBodyPose().getX();
        double tY = armorStand.getBodyPose().getY();
        double tZ = armorStand.getBodyPose().getZ();

        double laX = armorStand.getLeftArmPose().getX();
        double laY = armorStand.getLeftArmPose().getY();
        double laZ = armorStand.getLeftArmPose().getZ();

        double raX = armorStand.getRightArmPose().getX();
        double raY = armorStand.getRightArmPose().getY();
        double raZ = armorStand.getRightArmPose().getZ();

        double llX = armorStand.getLeftLegPose().getX();
        double llY = armorStand.getLeftLegPose().getY();
        double llZ = armorStand.getLeftLegPose().getZ();

        double rlX = armorStand.getRightLegPose().getX();
        double rlY = armorStand.getRightLegPose().getY();
        double rlZ = armorStand.getRightLegPose().getZ();

        switch (slot) {
            case 3:
                armorStand.teleport(new Location(armorStand.getWorld(), pX + amount, pY, pZ, pYaw, pPitch));
                positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getX()) }));
                position.setItemMeta(positionMeta);
                inventory.setItem(slot, position);
                break;
            case 4:
                if (!armorStand.hasGravity()) {
                    armorStand.teleport(new Location(armorStand.getWorld(), pX, pY + amount, pZ, pYaw, pPitch));
                    positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getY()) }));
                    position.setItemMeta(positionMeta);
                    inventory.setItem(slot, position);
                }
                break;
            case 5:
                armorStand.teleport(new Location(armorStand.getWorld(), pX, pY, pZ + amount, pYaw, pPitch));
                positionMeta.displayName(Lang.MENU_ARMOR_STAND_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLocation().getZ()) }));
                position.setItemMeta(positionMeta);
                inventory.setItem(slot, position);
                break;

            case 10:
                armorStand.setHeadPose(new EulerAngle(hX + amount, hY, hZ));
                headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getX()) }));
                headPosition.setItemMeta(headPositionMeta);
                inventory.setItem(slot, headPosition);
                break;

            case 11:
                armorStand.setHeadPose(new EulerAngle(hX, hY + amount, hZ));
                headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getY()) }));
                headPosition.setItemMeta(headPositionMeta);
                inventory.setItem(slot, headPosition);
                break;

            case 12:
                armorStand.setHeadPose(new EulerAngle(hX, hY, hZ + amount));
                headPositionMeta.displayName(Lang.MENU_ARMOR_STAND_HEAD_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getHeadPose().getZ()) }));
                headPosition.setItemMeta(headPositionMeta);
                inventory.setItem(slot, headPosition);
                break;

            case 14:
                armorStand.setBodyPose(new EulerAngle(tX + amount, tY, tZ));
                torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getX()) }));
                torsoPosition.setItemMeta(torsoPositionMeta);
                inventory.setItem(slot, torsoPosition);
                break;

            case 15:
                armorStand.setBodyPose(new EulerAngle(tX, tY + amount, tZ));
                torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getY()) }));
                torsoPosition.setItemMeta(torsoPositionMeta);
                inventory.setItem(slot, torsoPosition);
                break;

            case 16:
                armorStand.setBodyPose(new EulerAngle(tX, tY, tZ + amount));
                torsoPositionMeta.displayName(Lang.MENU_ARMOR_STAND_TORSO_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getBodyPose().getZ()) }));
                torsoPosition.setItemMeta(torsoPositionMeta);
                inventory.setItem(slot, torsoPosition);
                break;

            case 18:
                armorStand.setLeftArmPose(new EulerAngle(laX + amount, laY, laZ));
                leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getX()) }));
                leftArmPosition.setItemMeta(leftArmPositionMeta);
                inventory.setItem(slot, leftArmPosition);
                break;

            case 19:
                armorStand.setLeftArmPose(new EulerAngle(laX, laY + amount, laZ));
                leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getY()) }));
                leftArmPosition.setItemMeta(leftArmPositionMeta);
                inventory.setItem(slot, leftArmPosition);
                break;

            case 20:
                armorStand.setLeftArmPose(new EulerAngle(laX, laY, laZ + amount));
                leftArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_ARM_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftArmPose().getZ()) }));
                leftArmPosition.setItemMeta(leftArmPositionMeta);
                inventory.setItem(slot, leftArmPosition);
                break;

            case 24:
                armorStand.setRightArmPose(new EulerAngle(raX + amount, raY, raZ));
                rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getX()) }));
                rightArmPosition.setItemMeta(rightArmPositionMeta);
                inventory.setItem(slot, rightArmPosition);
                break;

            case 25:
                armorStand.setRightArmPose(new EulerAngle(raX , raY + amount, raZ));
                rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getY()) }));
                rightArmPosition.setItemMeta(rightArmPositionMeta);
                inventory.setItem(slot, rightArmPosition);
                break;

            case 26:
                armorStand.setRightArmPose(new EulerAngle(raX , raY, raZ + amount));
                rightArmPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_ARM_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightArmPose().getZ()) }));
                rightArmPosition.setItemMeta(rightArmPositionMeta);
                inventory.setItem(slot, rightArmPosition);
                break;

            case 28:
                armorStand.setLeftLegPose(new EulerAngle(llX + amount, llY, llZ));
                leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getX()) }));
                leftLegPosition.setItemMeta(leftLegPositionMeta);
                inventory.setItem(slot, leftLegPosition);
                break;

            case 29:
                armorStand.setLeftLegPose(new EulerAngle(llX, llY + amount, llZ));
                leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getY()) }));
                leftLegPosition.setItemMeta(leftLegPositionMeta);
                inventory.setItem(slot, leftLegPosition);
                break;

            case 30:
                armorStand.setLeftLegPose(new EulerAngle(llX, llY, llZ + amount));
                leftLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_LEFT_LEG_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getLeftLegPose().getZ()) }));
                leftLegPosition.setItemMeta(leftLegPositionMeta);
                inventory.setItem(slot, leftLegPosition);
                break;

            case 32:
                armorStand.setRightLegPose(new EulerAngle(rlX + amount, rlY, rlZ));
                rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cX&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getX()) }));
                rightLegPosition.setItemMeta(rightLegPositionMeta);
                inventory.setItem(slot, rightLegPosition);
                break;

            case 33:
                armorStand.setRightLegPose(new EulerAngle(rlX, rlY + amount, rlZ));
                rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cY&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getY()) }));
                rightLegPosition.setItemMeta(rightLegPositionMeta);
                inventory.setItem(slot, rightLegPosition);
                break;

            case 34:
                armorStand.setRightLegPose(new EulerAngle(rlX, rlY, rlZ  + amount));
                rightLegPositionMeta.displayName(Lang.MENU_ARMOR_STAND_RIGHT_LEG_POSITION.getComponent(new String[] { "&cZ&7: &6" + plugin.getPU().shortenDouble(armorStand.getRightLegPose().getZ()) }));
                rightLegPosition.setItemMeta(rightLegPositionMeta);
                inventory.setItem(slot, rightLegPosition);
                break;

            case 49:
                armorStand.teleport(new Location(armorStand.getWorld(), pX, pY, pZ, pYaw + (float) amount, pPitch));
                directionPositionMeta.displayName(Lang.MENU_ARMOR_STAND_DIRECTION_POSITION.getComponent(new String[] { plugin.getPU().shortenDouble(armorStand.getLocation().getYaw()) }));
                CompassMeta compassMeta = (CompassMeta) directionPositionMeta;
                compassMeta.setLodestone(armorStand.getLocation().add(armorStand.getLocation().getDirection().setY(0).normalize().multiply(5)));
                compassMeta.setLodestoneTracked(true);
                directionPosition.setItemMeta(directionPositionMeta);
                inventory.setItem(slot, directionPosition);
                break;
        }
    }
}
