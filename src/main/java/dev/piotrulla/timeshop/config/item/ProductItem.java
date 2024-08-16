package dev.piotrulla.timeshop.config.item;

import dev.piotrulla.timeshop.product.Product;
import dev.piotrulla.timeshop.product.ProductPresenter;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductItem extends OkaeriConfig implements Product {

    public int price = 100;

    public List<String> commands = Collections.singletonList("gamemode creative {PLAYER}");

    public List<ItemStack> elements = Arrays.asList(
            new ItemBuilder(Material.DIAMOND)
                    .setDisplayName("&bDiamenty")
                    .setAmount(32)
                    .setLegacyLore(Collections.singletonList("&7podstawowe lore"))
                    .addEnchantment(Enchantment.THORNS, 1, false)
                    .get(),

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
