package cc.dreamcode.timeshop.hook.placeholderapi;

import cc.dreamcode.timeshop.user.User;
import cc.dreamcode.timeshop.user.UserRepository;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiExpansion extends PlaceholderExpansion {

    private final UserRepository userRepository;

    public PlaceholderApiExpansion(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dreamtimeshop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Piotrulla";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.2";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("currency")) {
            return this.userRepository.findOrCreate(player.getUniqueId(), player.getName())
                    .thenApply(User::currency)
                    .join()
                    .toString();
        }

        return "";
    }
}
