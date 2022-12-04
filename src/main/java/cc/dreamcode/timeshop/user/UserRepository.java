package cc.dreamcode.timeshop.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@DocumentCollection(path = "user", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {

    default CompletableFuture<User> findOrCreate(UUID uuid, String userName) {
        return CompletableFuture.supplyAsync(() -> {
            User user = this.findOrCreateByPath(uuid);

            if (userName != null) {
                user.updateName(userName);
            }

            return user;
        });
    }

    default CompletableFuture<User> findOrCreate(UUID uuid) {
        return this.findOrCreate(uuid, null);
    }
}