package pl.ycode.plugins.timecoins.menus;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import pl.ycode.plugins.timecoins.configuration.ConfigurationFactory;
import pl.ycode.plugins.timecoins.menus.shop.ShopMenuConfiguration;

import java.util.HashMap;

public class Menu {
    @Inject private Injector injector;
    @Inject private ConfigurationFactory factory;

    private final HashMap<Class<?>, MenuConfig> configs = new HashMap<>();

    @PostConstruct
    public void __construct() {
        this.setupMenuConfig(ShopMenuConfiguration.class);
    }

    public<T extends AbstractMenu> AbstractMenu resolve(Class<T> clazz) {
        T menu = this.injector.createInstance(clazz);
        menu.setConfig(this.configs.get(menu.configClassName()));
        return menu;
    }

    private void setupMenuConfig(Class<? extends MenuConfig> clazz) {
        String name = this.toSnakeCase(clazz.getSimpleName());
        this.configs.put(clazz, this.factory.produce(clazz, "menus/"+name+".yml"));
    }

    private String toSnakeCase(String input) {
        return input
                .replace("Configuration","")
                .replace("Config", "")
                .replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
