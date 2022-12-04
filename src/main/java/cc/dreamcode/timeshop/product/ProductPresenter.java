package cc.dreamcode.timeshop.product;

import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ProductPresenter {

    private final String displayName;
    private final ItemStack itemPresenter;
    private final int slot;

    public ProductPresenter(String displayName, int slot, ItemStack itemStack) {
        this.displayName = displayName;
        this.slot = slot;
        this.itemPresenter = itemStack;
    }

    public String displayName() {
        return this.displayName;
    }

    public int slot() {
        return this.slot;
    }

    public ItemStack itemPresentation() {
        return this.itemPresenter;
    }

    public static ProductPresenter of(String displayName, int slot, ItemStack itemStack) {
        return new ProductPresenter(displayName, slot, itemStack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPresenter presenter = (ProductPresenter) o;

        return this.slot == presenter.slot &&
                this.displayName.equals(presenter.displayName) &&
                this.itemPresenter.equals(presenter.itemPresenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.displayName, this.slot, this.itemPresenter);
    }

    @Override
    public String toString() {
        return "ProductPresenter{" +
                "displayName='" + this.displayName + '\'' +
                ", slot=" + this.slot +
                ", itemStack=" + this.itemPresenter +
                '}';
    }
}
