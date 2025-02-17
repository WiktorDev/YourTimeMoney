package pl.ycode.plugins.timecoins.menus.shop.items;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.menus.shop.ShopMenuConfiguration;
import pl.ycode.plugins.timecoins.shop.ShopItem;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;
import pl.ycode.plugins.timecoins.utils.Format;
import pl.ycode.plugins.timecoins.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ShopGuiItem extends AbstractItem {
    @Inject private Plugin plugin;
    @Inject private PluginConfiguration configuration;
    @Inject private UserService userService;

    private ShopItem shopItem;
    private ShopMenuConfiguration shopMenuConfiguration;

    public ShopGuiItem param(ShopItem shopItem, ShopMenuConfiguration shopMenuConfiguration) {
        this.shopItem = shopItem;
        this.shopMenuConfiguration = shopMenuConfiguration;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(shopItem.getItem());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        ItemStack clickedItem = inventoryClickEvent.getCurrentItem();
        if (clickedItem == null || this.shopMenuConfiguration.getNoFoundsItem().getType() == clickedItem.getType()) return;

        User user = userService.findFirst(player);
        if (user.has(shopItem.getPrice())) {
            user.take(shopItem.getPrice());
            new Message(configuration.shopPurchaseSuccessfully).send(player);
            return;
        }

        inventoryClickEvent.setCurrentItem(new Format(this.shopMenuConfiguration.getNoFoundsItem()).get());
        new BukkitRunnable() {
            @Override
            public void run() {
                inventoryClickEvent.setCurrentItem(clickedItem);
            }
        }.runTaskLater(plugin, 10L);
    }
}
