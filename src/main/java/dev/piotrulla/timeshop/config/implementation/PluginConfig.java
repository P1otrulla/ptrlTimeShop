package dev.piotrulla.timeshop.config.implementation;

import dev.piotrulla.timeshop.config.item.ProductItem;
import dev.piotrulla.timeshop.product.Product;
import dev.piotrulla.timeshop.product.ProductRepository;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PluginConfig extends OkaeriConfig implements ProductRepository {

    @Comment({ "", "Time in secounds for coin:" })
    public int currencyProgress = 300;

    @Comment({ "", "How much coins for one progress:" })
    public int currencyValue = 5;

    @Comment({ "", "Plural settings:" })
    public String currencyLang = "pl";
    public List<String> currencyPlurals = Arrays.asList("monete", "monety", "monet");

    @Comment({ "", "Products:" })
    public Map<String, ProductItem> products = Collections.singletonMap("diamonds", new ProductItem());

    @Override
    public Collection<Product> products() {
        return Collections.unmodifiableCollection(this.products.values());
    }
}
