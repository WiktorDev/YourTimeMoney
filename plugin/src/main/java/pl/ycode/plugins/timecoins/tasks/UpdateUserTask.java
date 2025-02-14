package pl.ycode.plugins.timecoins.tasks;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.user.User;
import pl.ycode.plugins.timecoins.user.UserService;
import pl.ycode.plugins.timecoins.utils.TimeUtil;

public class UpdateUserTask implements Runnable {
    @Inject private PluginConfiguration config;
    @Inject private UserService userService;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = this.userService.findFirst(player);
            if (user == null) continue;
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - user.getLastUpdate();

            if (elapsedTime >= TimeUtil.timeFromString(this.config.time)) {
                user.addCoin();
                user.setLastUpdate(currentTime);
                player.sendMessage(this.config.receivedCoinMessage.replace("{coins}", String.valueOf(user.getCoins())));
            }
        }
    }
}
