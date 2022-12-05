package cc.dreamcode.timeshop;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.timeshop.builder.ItemBuilder;
import cc.dreamcode.timeshop.config.MessagesConfiguration;
import cc.dreamcode.timeshop.config.PluginConfiguration;
import cc.dreamcode.timeshop.product.Product;
import cc.dreamcode.timeshop.product.ProductPresenter;
import cc.dreamcode.timeshop.product.ProductService;
import cc.dreamcode.timeshop.shared.CurrencyPluralizer;
import cc.dreamcode.timeshop.shared.ItemUtil;
import cc.dreamcode.timeshop.user.User;
import cc.dreamcode.timeshop.user.UserRepository;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimeShopMenu {

    private final MessagesConfiguration messages;
    private final PluginConfiguration configuration;
    private final CurrencyPluralizer pluralizer;
    private final UserRepository userRepository;
    private final ProductService service;
    private final Server server;

    public TimeShopMenu(MessagesConfiguration messages, PluginConfiguration configuration, CurrencyPluralizer pluralizer, UserRepository userRepository, ProductService service, Server server) {
        this.messages = messages;
        this.configuration = configuration;
        this.pluralizer = pluralizer;
        this.userRepository = userRepository;
        this.service = service;
        this.server = server;
    }

    public void open(Player player) {
        BukkitMenuBuilder builder = this.configuration.menu;
        BukkitMenu menu = builder.build();

        User user = this.userRepository.findOrCreate(player.getUniqueId(), player.getName()).join();

        for (Product product : this.service.products()) {
            ProductPresenter presenter = product.presenter();

            menu.setItem(presenter.slot(), presenter.itemPresentation(), event -> {
                event.setCancelled(true);

                if (product.price() > user.currency()) {
                    int missing = product.price() - user.currency();

                    this.messages.notEnoughtCurrency.send(player, ImmutableMap.of(
                            "MISSING", missing,
                            "PRURAL", this.pluralizer.prularlize(missing))
                    );

                    return;
                }

                user.removeCurrency(product.price());

                product.elements().forEach(element -> ItemUtil.giveItem(player, element));

                product.commands().forEach(command -> this.server.dispatchCommand(this.server.getConsoleSender(), command.replace("{PLAYER}", player.getName())));

                this.messages.productBought.send(player, ImmutableMap.of(
                        "PRODUCT", presenter.displayName(),
                        "PRICE", product.price()
                ));
            });
        }

        builder.getItems().forEach((integer, itemStack) -> {
            ItemStack formatted = ItemBuilder.of(new ItemStack(itemStack))
                    .setLore(itemStack.getItemMeta().getLore().stream()
                            .map(line -> line
                                    .replace("{PLAYER}", player.getName())
                                    .replace("{AMOUNT}", String.valueOf(user.currency()))
                                    .replace("{PLURAL}", this.pluralizer.prularlize(user.currency()))
                            )
                            .toArray(String[]::new)
                    )
                    .toItemStack();

            menu.setItem(integer, formatted, event -> event.setCancelled(true));
        });

        menu.open(player);
    }
}
