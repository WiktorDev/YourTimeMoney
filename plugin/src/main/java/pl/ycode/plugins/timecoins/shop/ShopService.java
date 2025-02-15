package pl.ycode.plugins.timecoins.shop;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ycode.plugins.timecoins.configuration.ShopConfiguration;
import pl.ycode.plugins.timecoins.user.UserService;

import java.util.List;

public class ShopService {
    @Inject private UserService userService;
    @Inject private ShopConfiguration shopConfiguration;

    public List<ShopItem> getItems() {
        return this.shopConfiguration.getItems();
    }

    public void addItem(ItemStack item, int price) {
        this.shopConfiguration.getItems().add(new ShopItem(item, price));
        this.shopConfiguration.save();
    }

    public void buyItem(Player player, ShopItem item) {

    }
}
