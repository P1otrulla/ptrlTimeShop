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
import cc.dreamcode.timeshop.database.DatabaseProvider;
import cc.dreamcode.timeshop.product.ProductService;
import cc.dreamcode.timeshop.shared.CurrencyPluralizer;
import cc.dreamcode.timeshop.user.User;
import cc.dreamcode.timeshop.user.UserController;
import cc.dreamcode.timeshop.user.UserPlayingTimeTask;
import cc.dreamcode.timeshop.user.UserRepository;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
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

    private DatabaseProvider databaseProvider;

    private DocumentPersistence persistence;
    private UserRepository userRepository;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.configService = new ConfigService();

        this.messages = this.configService.create(MessagesConfiguration.class, new File(this.getDataFolder(), "messages.yml"));
        this.command = this.configService.create(CommandConfiguration.class, new File(this.getDataFolder(), "commands.yml"));
        this.config = this.configService.create(PluginConfiguration.class, new File(this.getDataFolder(), "config.yml"));

        BukkitMenuProvider.create(this);
        BukkitNoticeProvider.create(this);

        PersistenceCollection userCollection = PersistenceCollection.of(UserRepository.class);

        this.databaseProvider = new DatabaseProvider(this.config, this.getDataFolder());
        this.persistence = this.databaseProvider.setupPersistance();
        this.persistence.registerCollection(userCollection);

        this.userRepository = RepositoryDeclaration.of(UserRepository.class)
                .newProxy(this.persistence, userCollection, this.getClassLoader());

        CurrencyPluralizer currencyPluralizer = new CurrencyPluralizer(this.config, this.getLogger());
        TimeShopMenu menu = new TimeShopMenu(this.messages, this.config, currencyPluralizer, this.userRepository, this.config);

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

    public DatabaseProvider getDatabaseProvider() {
        return this.databaseProvider;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public ProductService getProductService() {
        return this.config;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }
}
