package lee.code.essentials.menusystem;

import lombok.Getter;

public abstract class PaginatedMenu extends Menu {

    public PaginatedMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Getter protected int maxItemsPerPage = 36;
    protected int page = 0;
    protected int index = 0;

    public void addMenuBorder() {
        inventory.setItem(48, previousPage);
        inventory.setItem(50, nextPage);

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, fillerGlass);
        }

        inventory.setItem(17, fillerGlass);
        inventory.setItem(18, fillerGlass);
        inventory.setItem(26, fillerGlass);
        inventory.setItem(27, fillerGlass);
        inventory.setItem(35, fillerGlass);
        inventory.setItem(36, fillerGlass);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, fillerGlass);
        }
    }
}