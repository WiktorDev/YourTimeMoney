package pl.ycode.plugins.timecoins.configuration;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;

import java.io.File;

public class ConfigurationFactory {
    private final File dataDirectory;

    public ConfigurationFactory(File dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public <T extends OkaeriConfig> T produce(Class<T> type, String fileName) {
        return this.produce(type, new File(this.dataDirectory, fileName));
    }

    public <T extends OkaeriConfig> T produce(Class<T> type, File file) {
        return ConfigManager.create(type, it -> it
                .withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit())
                .withBindFile(file)
                .saveDefaults()
                .load(true)
        );
    }
}
