package pl.ycode.plugins.timecoins.menus;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuItem extends OkaeriConfig {
    private ItemStack item;
    private List<String> actions;

    public MenuItem(ItemStack item, List<String> actions) {
        this.item = item;
        this.actions = actions;
    }

    public ItemStack getItem() {
        return item;
    }

    public List<String> getActions() {
        return actions;
    }
}
