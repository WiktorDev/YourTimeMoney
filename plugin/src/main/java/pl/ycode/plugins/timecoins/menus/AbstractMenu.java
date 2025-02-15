package pl.ycode.plugins.timecoins.menus;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public abstract class AbstractMenu {
    protected abstract Gui buildGui();

    public void open(Player player) {
        Window window = Window.single()
                .setViewer(player)
                .setGui(this::buildGui)
                .setTitle("")
                .build();
        window.open();
    }
}
