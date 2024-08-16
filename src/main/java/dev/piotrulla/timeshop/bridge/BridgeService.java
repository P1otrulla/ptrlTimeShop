package dev.piotrulla.timeshop.bridge;

import dev.piotrulla.timeshop.user.UserService;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

public class BridgeService {

    private final PluginDescriptionFile pluginDescriptionFile;
    private final PluginManager pluginManager;
    private final UserService userService;

    public BridgeService(PluginDescriptionFile pluginDescriptionFile, PluginManager pluginManager, UserService userService) {
        this.pluginDescriptionFile = pluginDescriptionFile;
        this.pluginManager = pluginManager;
        this.userService = userService;
    }

    public void initialize() {
        init("PlaceholderAPI", () -> new PlaceholderAPIBridge(this.pluginDescriptionFile, this.userService).register());
    }

    void init(String pluginName, Runnable runnable) {
        if (this.pluginManager.isPluginEnabled(pluginName)) {
            runnable.run();
        }
    }

}
