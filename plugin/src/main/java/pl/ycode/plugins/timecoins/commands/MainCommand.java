package pl.ycode.plugins.timecoins.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.menus.shop.ShopMenu;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;

@Command(name = "ytimecoins")
@Permission("ytimecoins.command.main")
public class MainCommand {
    @Inject private UserService userService;
    @Inject private Injector injector;

    @Execute
    void balance(@Context Player player) {
        User user = this.userService.findFirst(player);
        player.sendMessage("Twoje monty: " + user.getCoins());
    }

    @Execute(name = "shop")
    void shop(@Context Player player) {
        this.injector.createInstance(ShopMenu.class).open(player);
    }
}
