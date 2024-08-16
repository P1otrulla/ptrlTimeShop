package dev.piotrulla.timeshop.user;

import dev.piotrulla.timeshop.config.implementation.PluginConfig;
import dev.piotrulla.timeshop.TimeShopMultification;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class UserPlayingTimeTask implements Runnable {

    private final TimeShopMultification multification;
    private final PluginConfig pluginConfig;
    private final UserService userService;
    private final Server server;

    public UserPlayingTimeTask(TimeShopMultification multification, PluginConfig pluginConfig, UserService userService, Server server) {
        this.multification = multification;
        this.pluginConfig = pluginConfig;
        this.userService = userService;
        this.server = server;
    }

    @Override
    public void run() {
        for (Player player : this.server.getOnlinePlayers()) {
            User user = this.userService.findUser(player.getUniqueId());

            if (user == null) {
                user = this.userService.createUser(player.getUniqueId(), player.getName());
            }
            int currency = user.currency();

            user.addProgress(this.pluginConfig.currencyProgress, this.pluginConfig.currencyValue);
            this.userService.saveUser(user);

            if (currency != user.currency()) {
                this.multification.player(player.getUniqueId(), msg -> msg.receivedCurrency);
            }
        }
    }
}
