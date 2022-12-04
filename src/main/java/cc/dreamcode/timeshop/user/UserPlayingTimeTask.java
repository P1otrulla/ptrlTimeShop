package cc.dreamcode.timeshop.user;

import cc.dreamcode.timeshop.config.PluginConfiguration;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserPlayingTimeTask implements Runnable {

    private final UserRepository userRepository;
    private final PluginConfiguration configuration;
    private final Server server;

    public UserPlayingTimeTask(UserRepository userRepository, PluginConfiguration configuration, Server server) {
        this.userRepository = userRepository;
        this.configuration = configuration;
        this.server = server;
    }

    @Override
    public void run() {
        for (Player player : this.server.getOnlinePlayers()) {
            UUID uniqueId = player.getUniqueId();
            String name = player.getName();

            this.userRepository.findOrCreate(uniqueId, name).whenComplete((user, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                    return;
                }

                user.addProgress(this.configuration.currencyValue);
            });
        }
    }
}
