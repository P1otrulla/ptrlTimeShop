package cc.dreamcode.timeshop.config;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.timeshop.builder.ItemBuilder;
import cc.dreamcode.timeshop.config.item.ProductItem;
import cc.dreamcode.timeshop.config.sub.StorageConfig;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Header("## dreamTimeShop (Main-Config) ##")
public final class PluginConfiguration extends OkaeriConfig {

    public StorageConfig storage = new StorageConfig();

    @Comment({ "", "# Ustaw ilość czasu w sekundach za jedna monete:" })
    public int currencyValue = 5;

    @Comment({ "", "# Ustaw odmiane monet:" })
    public List<String> currencyPlurals = Arrays.asList("moneta", "monety", "monet");

    @Comment({ "", "Ustaw konfiguracje menu:" })
    public BukkitMenuBuilder menu = new BukkitMenuBuilder(
            "&6Sklep za czas!",
            3,
            true,
            new ImmutableMap.Builder<Integer, ItemStack>()
                    .put(26, new ItemBuilder(Material.GOLD_INGOT)
                            .setName("&eTwoje monety czasu")
                            .setLore(Collections.singletonList("&7Posiadasz: &e{AMOUNT} &7{PLURAL}"))
                            .toItemStack())
                    .build()
    );

    @Comment({ "", "Ustaw konfiguracje produktów:" })
    public Map<String, ProductItem> products = Collections.singletonMap("diamonds", new ProductItem());

}
