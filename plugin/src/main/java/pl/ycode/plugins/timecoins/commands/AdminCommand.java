package pl.ycode.plugins.timecoins.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.okaeri.injector.annotation.Inject;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ycode.plugins.timecoins.shop.ShopService;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.utils.TimeUtil;

@Command(name = "ytc-admin")
@Permission("ytc.command.admin")
public class AdminCommand {
    @Inject private ShopService shopService;

    @Execute(name = "give")
    void give(@Context CommandSender sender, @Arg User user, @Arg int amount) {
        user.give(amount);
        sender.sendMessage("Dodano " + amount + " monet do konta gracza");
    }
    @Execute(name = "take")
    void take(@Context CommandSender sender, @Arg User user, @Arg int amount) {
        user.take(amount);
        sender.sendMessage("Usunieto " + amount + " monet z konta gracza");
    }
    @Execute(name = "info")
    void give(@Context CommandSender sender, @Arg User user) {
        sender.sendMessage("Gracz " + user.getName());
        sender.sendMessage("Monety: " + user.getCoins());
        sender.sendMessage("Wydane monety: " + user.getSpentCoins());
        sender.sendMessage("Pierwsze logowanie: " + TimeUtil.formatDateTime(user.getFirstLogin()));
    }

    @Execute(name = "shop add")
    void shopAdd(@Context Player player, @Arg int price) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        this.shopService.addItem(itemStack, price);
        player.sendMessage("Dodano nowy item do sklepu!");
    }
}
