package cc.dreamcode.timeshop.product;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class Product {

    private final int id;
    private final int price;

    private final List<ItemStack> elements;

    private final ProductPresenter presenter;

    public Product(int id, int price, List<ItemStack> elements, ProductPresenter presenter) {
        this.id = id;
        this.price = price;
        this.elements = elements;
        this.presenter = presenter;
    }

    public int id() {
        return this.id;
    }

    public int price() {
        return this.price;
    }

    public List<ItemStack> elements() {
        return this.elements;
    }

    public ProductPresenter presenter() {
        return this.presenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        return this.id == product.id
                && this.price == product.price
                && this.presenter.equals(product.presenter);
    }

    public static Product of(int id, int price, ProductPresenter presenter, List<ItemStack> elements) {
        return new Product(id, price, elements, presenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.price, this.presenter, this.elements);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + this.id +
                ", price=" + this.price +
                ", presenter=" + this.presenter +
                ", elements=" + this.elements +
                '}';
    }
}
