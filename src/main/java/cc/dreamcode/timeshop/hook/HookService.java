package cc.dreamcode.timeshop.hook;

import cc.dreamcode.timeshop.hook.placeholderapi.PlaceholderApiExpansion;
import cc.dreamcode.timeshop.user.UserRepository;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class HookService {

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

            this.server.getLogger().info("Successfully hooked into " + pluginName + " hook!");
        }
    }
}
