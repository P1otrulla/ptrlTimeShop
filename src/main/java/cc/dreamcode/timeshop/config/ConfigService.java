package cc.dreamcode.timeshop.config;

import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.okaeri_serdes.BukkitNoticeSerdes;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;

import java.io.File;

public final class ConfigService {

    public <T extends OkaeriConfig> T create(Class<T> instance, File file) {
        return ConfigManager.create(instance, (config) -> {
            config.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            config.withSerdesPack(registry -> new ConfigSerdesPack().register(registry));
            config.withBindFile(file);
            config.saveDefaults();
            config.load();
        });
    }

    private static class ConfigSerdesPack implements OkaeriSerdesPack {

        @Override
        public void register(SerdesRegistry registry) {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        }
    }
}
