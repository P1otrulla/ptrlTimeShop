package dev.piotrulla.timeshop.user;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    CompletableFuture<List<User>> fetchAll();

    CompletableFuture<Void> save(User user);
}
