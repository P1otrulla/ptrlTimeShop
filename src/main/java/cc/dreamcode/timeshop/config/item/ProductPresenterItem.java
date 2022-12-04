package cc.dreamcode.timeshop.config.item;

import cc.dreamcode.timeshop.builder.ItemBuilder;
import cc.dreamcode.timeshop.product.ProductPresenter;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ProductPresenterItem extends OkaeriConfig implements ProductPresenter {

    public String displayName = "&bDiamenty";
    public int slot = 0;

    public ItemStack itemStack = ItemBuilder.of(Material.DIAMOND)
            .setName("&bZestaw diamentów")
            .setLore(Arrays.asList("&7Zawiera: ", "&e- 32 diamenty", "&e- 1 blok diamentowy", "", "&7Cena: &e100 &7monet czasu"))
            .toItemStack();

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public int slot() {
        return this.slot;
    }

    @Override
    public ItemStack itemPresentation() {
        return this.itemStack;
    }
}
