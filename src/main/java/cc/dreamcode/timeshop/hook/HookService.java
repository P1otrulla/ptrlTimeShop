package cc.dreamcode.timeshop.hook;

import cc.dreamcode.timeshop.hook.placeholderapi.PlaceholderApiExpansion;
import cc.dreamcode.timeshop.user.UserRepository;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HookService.class);

    private final UserRepository userRepository;
    private final Server server;

    public HookService(UserRepository userRepository, Server server) {
        this.userRepository = userRepository;
        this.server = server;
    }

    public void init() {
        setupBridge("PlaceholderAPI", () -> new PlaceholderApiExpansion(this.userRepository).register());
    }

    private void setupBridge(String pluginName, HookInitializer hook) {
        PluginManager pluginManager = this.server.getPluginManager();

        if (pluginManager.isPluginEnabled(pluginName)) {
            hook.initialize();

            LOGGER.info("Successfully hooked into " + pluginName + " hook!");
        }
    }
}
