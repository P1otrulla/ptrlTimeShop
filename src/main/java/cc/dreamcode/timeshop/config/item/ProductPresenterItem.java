package cc.dreamcode.timeshop.config.item;

import cc.dreamcode.timeshop.builder.ItemBuilder;
import cc.dreamcode.timeshop.product.ProductPresenter;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ProductPresenterItem extends OkaeriConfig {

    public String displayName = "&bDiamenty";
    public int slot = 0;

    public ItemStack itemStack = ItemBuilder.of(Material.DIAMOND)
            .setName("&bZestaw diamentów")
            .setLore(Arrays.asList("&7Zawiera: ", "&e- 32 diamenty", "&e- 1 blok diamentowy", "", "&7Cena: &e100 &7monet czasu"))
            .toItemStack();

    public ProductPresenterItem(String displayName, int slot, ItemStack itemStack) {
        this.displayName = displayName;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public ProductPresenterItem() {
    }

    public ProductPresenter toProductPresenter() {
        return new ProductPresenter(this.displayName, this.slot, this.itemStack);
    }
}
