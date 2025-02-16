package pl.ycode.plugins.timecoins.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.menus.Menu;
import pl.ycode.plugins.timecoins.menus.shop.ShopMenu;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.utils.Message;

@Command(name = "ytimecoins")
@Permission("ytimecoins.command.main")
public class MainCommand {
    @Inject private PluginConfiguration configuration;
    @Inject private Menu menu;

    @Execute
    void balance(@Context Player player, @Context User user) {
        new Message(this.configuration.balanceCommandMessage)
                .with("coins", user.getCoins())
                .send(player);
    }

    @Execute(name = "shop")
    void shop(@Context Player player) {
        this.menu.resolve(ShopMenu.class).open(player);
    }
}
