package pl.ycode.plugins.timecoins.commands.arguments;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;

public class UserArgument extends ArgumentResolver<CommandSender, User> {
    @Inject private UserService userService;

    @Override
    protected ParseResult<User> parse(Invocation<CommandSender> invocation, Argument<User> context, String argument) {
        if (argument.isEmpty()) return ParseResult.failure("User not found :c");
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(argument);
        User user = this.userService.findFirst(offlinePlayer.getUniqueId());
        if (user == null) {
            return ParseResult.failure("User not found :c");
        }
        return ParseResult.success(user);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<User> argument, SuggestionContext context) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(SuggestionResult.collector());
    }
}
