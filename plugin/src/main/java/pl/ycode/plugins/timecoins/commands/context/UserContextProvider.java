package pl.ycode.plugins.timecoins.commands.context;

import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;

public class UserContextProvider implements ContextProvider<CommandSender, User> {
    @Inject private UserService userService;

    @Override
    public ContextResult<User> provide(Invocation<CommandSender> invocation) {
        if (!(invocation.sender() instanceof Player player)) {
            return ContextResult.error("&cOnly players can use this command!");
        }
        return ContextResult.ok(() -> {
            User user = this.userService.findFirst(player);
            if (user == null) {
                throw new IllegalStateException("&cUser " + player.getName() + " not found");
            }
            return user;
        });
    }
}
