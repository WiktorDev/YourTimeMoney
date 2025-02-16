package pl.ycode.plugins.timecoins.menus;

import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.utils.Message;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public abstract class AbstractMenu {
    protected MenuConfig config;
    protected abstract Gui buildGui();
    protected abstract Class<? extends MenuConfig> configClassName();

    public void setConfig(MenuConfig config) {
        this.config = config;
    }

    public void open(Player player) {
        Window window = Window.single()
                .setViewer(player)
                .setGui(this::buildGui)
                .setTitle(new Message(this.config.title()).toString())
                .build();
        window.open();
    }
}
