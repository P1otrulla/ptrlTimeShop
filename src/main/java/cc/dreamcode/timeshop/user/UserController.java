package cc.dreamcode.timeshop.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class UserController implements Listener {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        UUID uniqueId = player.getUniqueId();
        String name = player.getName();

        this.userRepository.findOrCreate(uniqueId, name);
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        UUID uniqueId = player.getUniqueId();

        this.userRepository.findOrCreate(uniqueId).whenCompleteAsync((user, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            user.save();
        });
    }
}
