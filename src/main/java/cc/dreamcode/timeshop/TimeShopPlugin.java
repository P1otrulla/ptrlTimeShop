package cc.dreamcode.timeshop;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.timeshop.command.CommandConfiguration;
import cc.dreamcode.timeshop.command.argument.UserArgument;
import cc.dreamcode.timeshop.command.configurer.CommandConfigurer;
import cc.dreamcode.timeshop.command.handler.InvalidUsage;
import cc.dreamcode.timeshop.command.handler.PermissionMessage;
import cc.dreamcode.timeshop.command.implementation.TimeShopAdminCommand;
import cc.dreamcode.timeshop.command.implementation.TimeShopCommand;
import cc.dreamcode.timeshop.config.ConfigService;
import cc.dreamcode.timeshop.config.MessagesConfiguration;
import cc.dreamcode.timeshop.config.PluginConfiguration;
import cc.dreamcode.timeshop.product.Product;
import cc.dreamcode.timeshop.product.ProductPresenter;
import cc.dreamcode.timeshop.product.ProductService;
import cc.dreamcode.timeshop.shared.CurrencyPluralizer;
import cc.dreamcode.timeshop.user.User;
import cc.dreamcode.timeshop.user.UserController;
import cc.dreamcode.timeshop.user.UserPlayingTimeTask;
import cc.dreamcode.timeshop.user.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zaxxer.hikari.HikariConfig;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TimeShopPlugin extends JavaPlugin {

    private ConfigService configService;
    private MessagesConfiguration messages;
    private CommandConfiguration command;
    private PluginConfiguration config;

    private DocumentPersistence persistence;
    private UserRepository userRepository;

    private ProductService productService;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.configService = new ConfigService();

        this.messages = this.configService.create(MessagesConfiguration.class, new File(this.getDataFolder(), "messages.yml"));
        this.command = this.configService.create(CommandConfiguration.class, new File(this.getDataFolder(), "commands.yml"));
        this.config = this.configService.create(PluginConfiguration.class, new File(this.getDataFolder(), "config.yml"));

        PersistenceCollection userCollection = PersistenceCollection.of(UserRepository.class);

        BukkitMenuProvider.create(this);
        BukkitNoticeProvider.create(this);

        this.persistence = this.prepareStorage();
        this.persistence.registerCollection(userCollection);

        this.userRepository = RepositoryDeclaration.of(UserRepository.class)
                .newProxy(this.persistence, userCollection, this.getClassLoader());

        this.productService = new ProductService();

        this.config.products.values().forEach(product -> {
            ProductPresenter presenter = product.presenter.toProductPresenter();

            int id = this.productService.products().size() + 1;

            this.productService.addProduct(Product.of(id, product.price, presenter, product.elements));
        });

        CurrencyPluralizer currencyPluralizer = new CurrencyPluralizer(this.config, this.getLogger());
        TimeShopMenu menu = new TimeShopMenu(this.messages, this.config, currencyPluralizer, this.userRepository, this.productService);

        this.getServer().getPluginManager().registerEvents(new UserController(this.userRepository), this);
        this.getServer().getScheduler().runTaskTimer(this, new UserPlayingTimeTask(this.userRepository, this.config, this.getServer()), 20, 20);

        Metrics metrics = new Metrics(this, 17005);
        metrics.addCustomChart(new SimplePie("users", () -> String.valueOf(this.userRepository.count())));

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "dream-timeshop")
                .argument(User.class, new UserArgument(this.userRepository))

                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("onlyPlayer"))

                .invalidUsageHandler(new InvalidUsage(this.messages))
                .permissionHandler(new PermissionMessage(this.messages))

                .commandInstance(new TimeShopCommand(menu))
                .commandInstance(new TimeShopAdminCommand(this.config, this.messages, this.command))

                .commandGlobalEditor(new CommandConfigurer(this.command))

                .register();
    }

    @Override
    public void onDisable() {
        try {
            this.persistence.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.liteCommands.getPlatform().unregisterAll();
    }

    private DocumentPersistence prepareStorage() {
        try { Class.forName("org.mariadb.jdbc.Driver"); } catch (ClassNotFoundException ignored) { }
        try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException ignored) { }

        PersistencePath persistencePath = PersistencePath.of(this.config.storage.prefix);

        File dataFolder = this.getDataFolder();

        switch (this.config.storage.storageType) {
            case FLAT: {
                return new DocumentPersistence(new FlatPersistence(dataFolder, ".yml"), YamlBukkitConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case MYSQL: {
                 HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(this.config.storage.uri);

                return new DocumentPersistence(new MariaDbPersistence(persistencePath, mariadbHikari), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case H2: {
                final HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(this.config.storage.uri);

                return new DocumentPersistence(new H2Persistence(persistencePath, jdbcHikari), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case REDIS: {
                RedisURI redisUri = RedisURI.create(this.config.storage.uri);
                RedisClient redisClient = RedisClient.create(redisUri);

                return new DocumentPersistence(new RedisPersistence(persistencePath, redisClient), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case MONGO: {
                MongoClient mongoClient = MongoClients.create(this.config.storage.uri);

                return new DocumentPersistence(new MongoPersistence(persistencePath, mongoClient, this.config.storage.prefix), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            default: {
                throw new RuntimeException("unsupported storage backend: " + this.config.storage.storageType);
            }
        }
    }

    public ConfigService getConfigService() {
        return this.configService;
    }

    public MessagesConfiguration getMessagesConfiguration() {
        return this.messages;
    }

    public CommandConfiguration getCommandConfiguration() {
        return this.command;
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.config;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public ProductService getProductService() {
        return this.productService;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }
}
