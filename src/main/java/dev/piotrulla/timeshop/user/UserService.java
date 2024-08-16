package dev.piotrulla.timeshop.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private final Map<UUID, User> usersByUniqueId = new HashMap<>();
    private final Map<String, User> usersByName = new HashMap<>();

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UUID uniqueId, String name) {
        User user = new User(uniqueId, name);

        this.usersByUniqueId.put(uniqueId, user);
        this.usersByName.put(name, user);

        return user;
    }

    public User findUser(UUID uniqueId) {
        return this.usersByUniqueId.get(uniqueId);
    }

    public User findUser(String name) {
        return this.usersByName.get(name);
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public void loadDatabase() {
        this.userRepository.fetchAll().whenComplete((users, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            users.forEach(user -> {
                this.usersByUniqueId.put(user.uniqueId(), user);
                this.usersByName.put(user.name(), user);
            });
        });
    }

    public Collection<User> users() {
        return Collections.unmodifiableCollection(this.usersByUniqueId.values());
    }
}
