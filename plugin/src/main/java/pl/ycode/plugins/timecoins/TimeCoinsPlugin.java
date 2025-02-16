package pl.ycode.plugins.timecoins;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ycode.plugins.timecoins.commands.AdminCommand;
import pl.ycode.plugins.timecoins.commands.MainCommand;
import pl.ycode.plugins.timecoins.commands.arguments.UserArgument;
import pl.ycode.plugins.timecoins.commands.context.UserContextProvider;
import pl.ycode.plugins.timecoins.configuration.ConfigurationFactory;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.configuration.ShopConfiguration;
import pl.ycode.plugins.timecoins.database.DatabaseConnector;
import pl.ycode.plugins.timecoins.hooks.PlaceholderApiHook;
import pl.ycode.plugins.timecoins.listeners.PlayerJoinListener;
import pl.ycode.plugins.timecoins.listeners.PlayerQuitListener;
import pl.ycode.plugins.timecoins.menus.Menu;
import pl.ycode.plugins.timecoins.shop.ShopService;
import pl.ycode.plugins.timecoins.tasks.UpdateUserTask;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;

import java.util.List;

public final class TimeCoinsPlugin extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();
    private ConfigurationFactory configurationFactory;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this);
        this.injector.registerInjectable(this.injector);

        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        this.injector.registerInjectable(this.configurationFactory);
        this.registerConfiguration(PluginConfiguration.class, "configuration.yml");
        this.registerConfiguration(ShopConfiguration.class, "shop_items.yml");

        DatabaseConnector databaseConnector = this.injector.createInstance(DatabaseConnector.class);
        this.injector.registerInjectable(databaseConnector);

        this.injector.registerInjectable(this.injector.createInstance(UserService.class));
        this.injector.registerInjectable(this.injector.createInstance(ShopService.class));

        this.injector.registerInjectable(this.injector.createInstance(Menu.class));

        this.liteCommands = LiteBukkitFactory.builder("yourtimecoins", this)
                .argument(User.class, this.injector.createInstance(UserArgument.class))
                .context(User.class, this.injector.createInstance(UserContextProvider.class))
                .commands(
                        this.injector.createInstance(MainCommand.class),
                        this.injector.createInstance(AdminCommand.class)
                )
                .build();

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
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

    private<T extends OkaeriConfig> void registerConfiguration(Class<T> config, String name) {
        this.injector.registerInjectable(this.configurationFactory.produce(config, name));
    }
}
