package dev.piotrulla.timeshop.database.user;

import dev.piotrulla.timeshop.user.User;
import dev.piotrulla.timeshop.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseUserRepository implements UserRepository {

    @Override
    public CompletableFuture<List<User>> fetchAll() {
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

    @Override
    public CompletableFuture<Void> save(User user) {
        return null;
    }
}
