package pl.ycode.plugins.timecoins.menus.shop;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import pl.ycode.plugins.timecoins.menus.AbstractMenu;
import pl.ycode.plugins.timecoins.menus.shop.items.BackItem;
import pl.ycode.plugins.timecoins.menus.shop.items.ForwardItem;
import pl.ycode.plugins.timecoins.shop.ShopItem;
import pl.ycode.plugins.timecoins.shop.ShopService;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
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
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));

        return PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # < # > # # #"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('<', new BackItem())
                .addIngredient('>', new ForwardItem())
                .setContent(items)
                .build();
    }
}
