package dev.piotrulla.timeshop.bridge;

import dev.piotrulla.timeshop.user.UserService;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIBridge extends PlaceholderExpansion {

    private final PluginDescriptionFile pluginDescriptionFile;
    private final UserService userService;

    public PlaceholderAPIBridge(PluginDescriptionFile pluginDescriptionFile, UserService userService) {
        this.pluginDescriptionFile = pluginDescriptionFile;
        this.userService = userService;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ptrlTimeShop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "P1otrulla";
    }

    @Override
    public @NotNull String getVersion() {
        return this.pluginDescriptionFile.getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return null;
    }
}
