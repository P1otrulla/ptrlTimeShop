package dev.piotrulla.timeshop;

import com.eternalcode.commons.adventure.AdventureLegacyColorPostProcessor;
import com.eternalcode.commons.adventure.AdventureUrlPostProcessor;
import dev.piotrulla.timeshop.bridge.BridgeService;
import dev.piotrulla.timeshop.config.ConfigService;
import dev.piotrulla.timeshop.config.implementation.MessagesConfig;
import dev.piotrulla.timeshop.config.implementation.PluginConfig;
import dev.piotrulla.timeshop.database.DatabaseService;
import dev.piotrulla.timeshop.database.user.DatabaseUserRepository;
import dev.piotrulla.timeshop.user.UserRepository;
import dev.piotrulla.timeshop.user.UserService;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TimeShopPlugin extends JavaPlugin {

    private ConfigService configService;

    private MessagesConfig messagesConfig;
    private PluginConfig pluginConfig;

    private BridgeService bridgeService;

    private AudienceProvider audienceProvider;
    private MiniMessage miniMessage;

    private DatabaseService databaseService;

    private UserRepository userRepository;
    private UserService userService;

    private TimeShopMultification multification;

    private TimeShopPluralizer pluralizer = new TimeShopPluralizer(this.pluginConfig);

    @Override
    public void onEnable() {
        this.configService = new ConfigService();

        this.messagesConfig = this.configService.create(MessagesConfig.class, new File(this.getDataFolder(), "messages.yml"));
        this.pluginConfig = this.configService.create(PluginConfig.class, new File(this.getDataFolder(), "config.yml"));

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new AdventureLegacyColorPostProcessor())
                .postProcessor(new AdventureUrlPostProcessor())
                .build();

        Server server = this.getServer();

        this.databaseService = new DatabaseService();
        this.userRepository = new DatabaseUserRepository();
        this.userService = new UserService(this.userRepository);
        this.userService.loadDatabase();

        this.bridgeService = new BridgeService(this.getDescription(), server.getPluginManager(), this.userService);
        this.bridgeService.initialize();

        this.multification = new TimeShopMultification(this.audienceProvider, this.messagesConfig, this.miniMessage);
    }

    @Override
    public void onDisable() {

    }
}
