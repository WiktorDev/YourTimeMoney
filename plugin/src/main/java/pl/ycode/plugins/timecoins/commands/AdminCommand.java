package pl.ycode.plugins.timecoins.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.shop.ShopService;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.utils.Message;
import pl.ycode.plugins.timecoins.utils.TimeUtil;

@Command(name = "ytc-admin")
@Permission("ytc.command.admin")
public class AdminCommand {
    @Inject private PluginConfiguration config;
    @Inject private ShopService shopService;

    @Execute(name = "give")
    void give(@Context CommandSender sender, @Arg User user, @Arg int amount) {
        user.give(amount);
        new Message(this.config.adminGiveCommand)
                .with("coins", amount)
                .with("player", user.getName())
                .send(sender);
    }
    @Execute(name = "take")
    void take(@Context CommandSender sender, @Arg User user, @Arg int amount) {
        user.take(amount);
        new Message(this.config.adminTakeCommand)
                .with("coins", amount)
                .with("player", user.getName())
                .send(sender);
    }
    @Execute(name = "info")
    void give(@Context CommandSender sender, @Arg User user) {
        new Message(this.config.adminInfoCommand)
                .with("player", user.getName())
                .with("coins", user.getCoins())
                .with("spent_coins", user.getSpentCoins())
                .with("first_login", TimeUtil.formatDateTime(user.getFirstLogin()))
                .send(sender);
    }

    @Execute(name = "shop add")
    void shopAdd(@Context Player player, @Arg int price) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        this.shopService.addItem(itemStack, price);
        new Message(this.config.adminShopAddCommand).send(player);
    }
}
