package cc.dreamcode.timeshop.config.item;

import cc.dreamcode.timeshop.builder.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductItem extends OkaeriConfig {

    public int price = 100;

    public ProductPresenterItem presenter = new ProductPresenterItem();

    public List<ItemStack> elements = Arrays.asList(ItemBuilder.of(Material.DIAMOND)
            .setName("&bDiamenty")
            .setAmount(32)
            .setLore(Collections.singletonList("&7podstawowe lore"))
            .addEnchant(Enchantment.THORNS, 1)
            .toItemStack(),

            new ItemStack(Material.DIAMOND_BLOCK, 1));

    public ProductItem(int price, ProductPresenterItem presenter, List<ItemStack> elements) {
        this.price = price;
        this.presenter = presenter;
        this.elements = elements;
    }

    public ProductItem() {
    }
}
