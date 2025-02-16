package pl.ycode.plugins.timecoins.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {
    private final String message;
    private final Map<String, Object> variables = new HashMap<>();

    public Message(String message) {
        this.message = message;
    }
    public Message(List<String> message) {
        this.message = String.join("\n", message);
    }

    public Message with(String key, Object value) {
        this.variables.put(key, value);
        return this;
    }

    public void send(Player player) {
        player.sendMessage(this.format());
    }
    public void send(CommandSender sender) {
        sender.sendMessage(this.format());
    }

    private Component format() {
        String formatted = message;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            formatted = formatted.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        formatted = formatted.replace(">>", "»").replace("<<", "«");
        return MiniMessage.miniMessage().deserialize(formatted);
    }

    @Override
    public String toString() {
        return MiniMessage.miniMessage().serialize(this.format());
    }
}
