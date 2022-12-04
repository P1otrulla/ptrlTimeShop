package cc.dreamcode.timeshop.product;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ProductService {

    private final List<Product> products = new LinkedList<>();

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Collection<Product> products() {
        return Collections.unmodifiableCollection(this.products);
    }
}
