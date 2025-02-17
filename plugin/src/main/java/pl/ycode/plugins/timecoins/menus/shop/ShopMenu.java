package pl.ycode.plugins.timecoins.menus.shop;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import pl.ycode.plugins.timecoins.menus.AbstractMenu;
import pl.ycode.plugins.timecoins.menus.shop.items.ShopGuiItem;
import pl.ycode.plugins.timecoins.shop.ShopService;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.Item;
import java.util.List;
import java.util.stream.Collectors;

public class ShopMenu extends AbstractMenu<ShopMenuConfiguration> {
    @Inject private ShopService shopService;
    @Inject private Injector injector;

    @Override
    protected Class<ShopMenuConfiguration> configClassName() {
        return ShopMenuConfiguration.class;
    }

    @Override
    protected Gui.Builder<?, ?> builder() {
        List<Item> items = this.shopService.getItems().stream()
                .filter(shopItem -> shopItem.getItem() != null && !shopItem.getItem().getType().isAir())
                .map(shopItem -> this.injector.createInstance(ShopGuiItem.class).param(shopItem, this.config))
                .collect(Collectors.toList());
        return PagedGui.items().setContent(items);
    }
}
