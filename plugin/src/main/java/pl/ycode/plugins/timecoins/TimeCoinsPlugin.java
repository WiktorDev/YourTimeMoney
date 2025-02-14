package pl.ycode.plugins.timecoins;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ycode.plugins.timecoins.configuration.ConfigurationFactory;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.database.DatabaseConnector;
import pl.ycode.plugins.timecoins.hooks.PlaceholderApiHook;
import pl.ycode.plugins.timecoins.listeners.PlayerJoinListener;
import pl.ycode.plugins.timecoins.listeners.PlayerQuitListener;
import pl.ycode.plugins.timecoins.tasks.UpdateUserTask;
import pl.ycode.plugins.timecoins.user.UserService;

import java.util.List;

public final class TimeCoinsPlugin extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();
    private ConfigurationFactory configurationFactory;

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this);

        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        this.registerConfiguration(PluginConfiguration.class, "configuration.yml");

        DatabaseConnector databaseConnector = this.injector.createInstance(DatabaseConnector.class);
        this.injector.registerInjectable(databaseConnector);

        this.injector.registerInjectable(this.injector.createInstance(UserService.class));

        List.of(
                this.injector.createInstance(PlayerJoinListener.class),
                this.injector.createInstance(PlayerQuitListener.class)
        ).forEach(it -> this.getServer().getPluginManager().registerEvents(it, this));

        this.getServer().getScheduler().runTaskTimer(this, this.injector.createInstance(UpdateUserTask.class), 0L, 20L * 60);

        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.injector.createInstance(PlaceholderApiHook.class).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private<T extends OkaeriConfig> void registerConfiguration(Class<T> config, String name) {
        this.injector.registerInjectable(this.configurationFactory.produce(config, name));
    }
}
