package cc.dreamcode.timeshop.config.item;

import cc.dreamcode.timeshop.builder.ItemBuilder;
import cc.dreamcode.timeshop.product.Product;
import cc.dreamcode.timeshop.product.ProductPresenter;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductItem extends OkaeriConfig implements Product {

    public int price = 100;

    public List<String> commands = Collections.singletonList("gamemode creative {PLAYER}");

    public List<ItemStack> elements = Arrays.asList(ItemBuilder.of(Material.DIAMOND)
            .setName("&bDiamenty")
            .setAmount(32)
            .setLore(Collections.singletonList("&7podstawowe lore"))
            .addEnchant(Enchantment.THORNS, 1)
            .toItemStack(),

            new ItemStack(Material.DIAMOND_BLOCK, 1));

    public ProductPresenterItem presenter = new ProductPresenterItem();

    @Override
    public int price() {
        return this.price;
    }

    @Override
    public List<ItemStack> elements() {
        return this.elements;
    }

    @Override
    public List<String> commands() {
        return this.commands;
    }

    @Override
    public ProductPresenter presenter() {
        return this.presenter;
    }
}
