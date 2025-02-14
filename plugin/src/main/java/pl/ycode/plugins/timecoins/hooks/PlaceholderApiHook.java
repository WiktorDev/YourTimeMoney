package pl.ycode.plugins.timecoins.hooks;

import eu.okaeri.injector.annotation.Inject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;
import pl.ycode.plugins.timecoins.utils.TimeUtil;

public class PlaceholderApiHook extends PlaceholderExpansion {
    @Inject private UserService userService;
    @Inject private Plugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "ytimecoins";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(",", this.plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        User user = this.userService.findFirst(player.getUniqueId());
        if (user == null) return null;
        if (params.equalsIgnoreCase("coins")) {
            return String.valueOf(user.getCoins());
        }
        if (params.equalsIgnoreCase("spent_coins")) {
            return String.valueOf(user.getSpentCoins());
        }
        if (params.equalsIgnoreCase("first_login_datetime")) {
            return TimeUtil.formatDateTime(user.getFirstLogin());
        }
        if (params.equalsIgnoreCase("first_login_date")) {
            return TimeUtil.formatDate(user.getFirstLogin());
        }
        return null;
    }
}
