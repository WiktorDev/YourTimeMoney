package pl.ycode.plugins.timemoney;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ycode.plugins.timemoney.configuration.ConfigurationFactory;
import pl.ycode.plugins.timemoney.configuration.PluginConfiguration;
import pl.ycode.plugins.timemoney.database.DatabaseConnector;

public final class TimeMoneyPlugin extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();
    private ConfigurationFactory configurationFactory;

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this);

        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        this.registerConfiguration(PluginConfiguration.class, "configuration.yml");

        DatabaseConnector databaseConnector = this.injector.createInstance(DatabaseConnector.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private<T extends OkaeriConfig> void registerConfiguration(Class<T> config, String name) {
        this.injector.registerInjectable(this.configurationFactory.produce(config, name));
    }
}
