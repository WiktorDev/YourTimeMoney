package pl.ycode.plugins.timecoins.shop;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.inventory.ItemStack;

public class ShopItem extends OkaeriConfig {
    private ItemStack item;
    private int price;

    public ShopItem(ItemStack item, int price) {
        this.item = item;
        this.price = price;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
