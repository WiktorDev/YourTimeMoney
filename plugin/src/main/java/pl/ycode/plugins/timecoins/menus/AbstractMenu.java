package pl.ycode.plugins.timecoins.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import pl.ycode.plugins.timecoins.menus.global.BackItem;
import pl.ycode.plugins.timecoins.menus.global.ForwardItem;
import pl.ycode.plugins.timecoins.utils.Message;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

public abstract class AbstractMenu<C extends MenuConfig> {
    protected C config;
    protected abstract Gui.Builder<?, ?> builder();
    protected abstract Class<C> configClassName();

    public void setConfig(C config) {
        this.config = config;
    }

    public void open(Player player) {
        var builder = this.builder();
        builder.setStructure(this.config.getStructure());
        builder.addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);
        this.config.items().forEach((key, value) -> {
            if (key == '<') builder.addIngredient('<', new BackItem(value.getItem()));
            if (key == '>') builder.addIngredient('>', new ForwardItem(value.getItem()));
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

        Window window = Window.single()
                .setViewer(player)
                .setGui(builder.build())
                .setTitle(new Message(this.config.title()).toString())
                .build();
        window.open();
    }
}
