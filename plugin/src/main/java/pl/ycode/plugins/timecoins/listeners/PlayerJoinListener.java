package pl.ycode.plugins.timecoins.listeners;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.ycode.plugins.timecoins.user.UserService;

public class PlayerJoinListener implements Listener {
    @Inject private UserService userService;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.userService.createIfNotExists(event.getPlayer());
    }
}
