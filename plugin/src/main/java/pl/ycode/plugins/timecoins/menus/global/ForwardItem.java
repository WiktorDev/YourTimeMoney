package pl.ycode.plugins.timecoins.menus.global;

import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends PageItem {
    private final ItemStack itemStack;

    public ForwardItem(ItemStack itemStack) {
        super(true);
        this.itemStack = itemStack;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        return new ItemBuilder(this.itemStack);
    }
}