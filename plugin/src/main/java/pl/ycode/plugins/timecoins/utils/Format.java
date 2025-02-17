package pl.ycode.plugins.timecoins.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class Format {
    private final ItemStack itemStack;
    private final Map<String, Object> variables = new HashMap<>();

    public Format(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public Format with(String key, Object value) {
        this.variables.put(key, value);
        return this;
    }
    private Component format(String message) {
        String formatted = message;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            formatted = formatted.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        formatted = formatted.replace(">>", "»").replace("<<", "«");
        return MiniMessage.miniMessage().deserialize(formatted);
    }
    public ItemStack get() {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta.hasDisplayName()) meta.displayName(format(meta.getDisplayName()));
        if (meta.hasLore()) meta.lore(meta.getLore().stream().map(this::format).toList());
        this.itemStack.setItemMeta(meta);
        return this.itemStack;
    }
}
