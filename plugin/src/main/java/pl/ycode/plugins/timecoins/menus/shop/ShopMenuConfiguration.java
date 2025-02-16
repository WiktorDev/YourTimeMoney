package pl.ycode.plugins.timecoins.menus.shop;

import org.bukkit.Material;
import pl.ycode.plugins.timecoins.menus.MenuConfig;
import pl.ycode.plugins.timecoins.menus.MenuItem;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.List;
import java.util.Map;

public class ShopMenuConfiguration extends MenuConfig {
    private String title = "<bold>SKLEP ZA CZAS";
    private List<String> structure = List.of(
            "# # # # # # # # #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# # # < # > # # #"
    );
    private Map<Character, MenuItem> items = Map.of(
            '#', new MenuItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("").get(), List.of()),
            '<', new MenuItem(
                    new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                            .setDisplayName("<red>Poprzednia strona")
                            .addLoreLines("<gray>Kliknij aby przejsc dalej")
                            .get(),
                    List.of("PREVIOUS_PAGE")
            ),
            '>', new MenuItem(
                    new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                            .setDisplayName("<green>Nastepna strona")
                            .addLoreLines("<gray>Kliknij aby przejsc dalej")
                            .get(),
                    List.of("NEXT_PAGE")
            )
    );

    @Override
    public String title() {
        return this.title;
    }

    @Override
    protected List<String> structure() {
        return this.structure;
    }

    @Override
    public Map<Character, MenuItem> items() {
        return this.items;
    }
}
