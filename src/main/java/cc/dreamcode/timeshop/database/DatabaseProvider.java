package cc.dreamcode.timeshop.database;

import cc.dreamcode.timeshop.config.PluginConfiguration;
import cc.dreamcode.timeshop.config.sub.StorageConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

import java.io.File;

public final class DatabaseProvider {

    private final PluginConfiguration pluginConfiguration;
    private final File dataFolder;

    public DatabaseProvider(PluginConfiguration pluginConfiguration, File dataFolder) {
        this.pluginConfiguration = pluginConfiguration;
        this.dataFolder = dataFolder;
    }

    public DocumentPersistence setupPersistance() {
        StorageConfig storage = this.pluginConfiguration.storage;

        PersistencePath persistencePath = PersistencePath.of(storage.prefix);

        switch (storage.storageType) {
            case FLAT: {
                return new DocumentPersistence(new FlatPersistence(dataFolder, ".yml"), YamlBukkitConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case MYSQL: {
                HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(storage.uri);

                return new DocumentPersistence(new MariaDbPersistence(persistencePath, mariadbHikari), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case H2: {
                HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(storage.uri);

                return new DocumentPersistence(new H2Persistence(persistencePath, jdbcHikari), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case REDIS: {
                RedisURI redisUri = RedisURI.create(storage.uri);
                RedisClient redisClient = RedisClient.create(redisUri);

                return new DocumentPersistence(new RedisPersistence(persistencePath, redisClient), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            case MONGO: {
                MongoClient mongoClient = MongoClients.create(storage.uri);

                return new DocumentPersistence(new MongoPersistence(persistencePath, mongoClient, storage.prefix), JsonSimpleConfigurer::new, new SerdesBukkit(), new SerdesCommons());
            }

            default: {
                throw new RuntimeException("unsupported storage backend: " + storage.storageType);
            }
        }
    }
}
