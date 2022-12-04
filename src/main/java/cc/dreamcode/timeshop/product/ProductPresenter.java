package cc.dreamcode.timeshop.product;

import org.bukkit.inventory.ItemStack;

public interface ProductPresenter {

    String displayName();

    int slot();

    ItemStack itemPresentation();
}
