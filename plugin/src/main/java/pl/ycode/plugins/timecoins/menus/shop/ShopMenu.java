package pl.ycode.plugins.timecoins.menus.shop;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import pl.ycode.plugins.timecoins.menus.AbstractMenu;
import pl.ycode.plugins.timecoins.menus.MenuConfig;
import pl.ycode.plugins.timecoins.menus.shop.items.BackItem;
import pl.ycode.plugins.timecoins.menus.shop.items.ForwardItem;
import pl.ycode.plugins.timecoins.shop.ShopItem;
import pl.ycode.plugins.timecoins.shop.ShopService;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;
import java.util.stream.Collectors;

public class ShopMenu extends AbstractMenu {
    @Inject private ShopService shopService;

    @Override
    protected Gui buildGui() {
        List<Item> items = this.shopService.getItems().stream()
                .filter(shopItem -> shopItem.getItem() != null && !shopItem.getItem().getType().isAir())
                .map(shopItem -> new SimpleItem(new ItemBuilder(shopItem.getItem())))
                .collect(Collectors.toList());

        var builder = PagedGui.items()
                .setStructure(this.config.getStructure())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);
//                .addIngredient('<', new BackItem())
//                .addIngredient('>', new ForwardItem());
        this.config.items().forEach((key, value) -> {
            builder.addIngredient(key, new AbstractItem() {
                @Override
                public ItemProvider getItemProvider() {
                    return new ItemBuilder(value.getItem());
                }

                @Override
                public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {

                }
            });
        });
        return builder.setContent(items).build();
    }

    @Override
    protected Class<? extends MenuConfig> configClassName() {
        return ShopMenuConfiguration.class;
    }
}
