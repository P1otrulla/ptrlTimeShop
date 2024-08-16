package dev.piotrulla.timeshop.product;

import org.bukkit.inventory.ItemStack;

public interface ProductPresenter {

    String displayName();

    int slot();

    ItemStack itemPresentation();
}
