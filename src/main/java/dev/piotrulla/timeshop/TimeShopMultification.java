package dev.piotrulla.timeshop;

import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.bukkit.BukkitMultification;
import com.eternalcode.multification.translation.TranslationProvider;
import dev.piotrulla.timeshop.config.implementation.MessagesConfig;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimeShopMultification extends BukkitMultification<MessagesConfig> {

    private final AudienceProvider audienceProvider;
    private final MessagesConfig messagesConfig;
    private final MiniMessage miniMessage;

    public TimeShopMultification(AudienceProvider audienceProvider, MessagesConfig messagesConfig, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.messagesConfig = messagesConfig;
        this.miniMessage = miniMessage;
    }

    @Override
    protected @NotNull TranslationProvider<MessagesConfig> translationProvider() {
        return locale -> this.messagesConfig;
    }

    @Override
    protected @NotNull ComponentSerializer<Component, Component, String> serializer() {
        return this.miniMessage;
    }

    @Override
    protected @NotNull AudienceConverter<CommandSender> audienceConverter() {
        return commandSender -> {
            if (commandSender instanceof Player player) {
                return this.audienceProvider.player(player.getUniqueId());
            }

            return this.audienceProvider.console();
        };
    }
}