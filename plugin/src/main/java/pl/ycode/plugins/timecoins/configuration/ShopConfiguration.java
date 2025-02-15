package pl.ycode.plugins.timecoins.configuration;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.ycode.plugins.timecoins.shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopConfiguration extends OkaeriConfig {
    private List<ShopItem> items = new ArrayList<>(){{
        add(new ShopItem(new ItemStack(Material.DIRT), 10));
    }};

    public List<ShopItem> getItems() {
        return items;
    }
    public void setItems(List<ShopItem> items) {
        this.items = items;
    }
}
