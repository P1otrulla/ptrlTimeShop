package dev.piotrulla.timeshop.product;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Product {

    int price();

    List<ItemStack> elements();

    List<String> commands();

    ProductPresenter presenter();
}