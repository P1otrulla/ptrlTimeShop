package dev.piotrulla.timeshop.config.item;

import dev.piotrulla.timeshop.product.ProductPresenter;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.Arrays;

public class ProductPresenterItem extends OkaeriConfig implements ProductPresenter {

    public String displayName = "&bDiamenty";
    public int slot = 0;

    public ItemStack itemStack = new ItemBuilder(Material.DIAMOND)
            .setDisplayName("&bZestaw diamentów")
            .setLegacyLore(
                Arrays.asList(
                    "&7Zawiera: ",
                    "&e- 32 diamenty",
                    "&e- 1 blok diamentowy",
                    "",
                    "&7Cena: &e100 &7monet czasu"))
            .get();

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
