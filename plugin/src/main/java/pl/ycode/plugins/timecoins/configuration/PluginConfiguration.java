package pl.ycode.plugins.timecoins.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.ycode.plugins.timecoins.configuration.sections.DatabaseSection;

import java.util.List;

public class PluginConfiguration extends OkaeriConfig {
    public DatabaseSection database = new DatabaseSection();

    @Comment("Format: [number][s,m,h,d] (ex. 1d -> 1 day, 30m -> 30 minutes)")
    public String time = "5m";

    public String balanceCommandMessage = "Twoje monety: <yellow>{coins}</yellow>";
    public String receivedCoinMessage = "Zdobyłeś 1 monete czasu! Masz teraz {coins} monet";

    public String adminGiveCommand = "&8&l>> &7Dodano {coins} monet do konta gracza {player}";
    public String adminTakeCommand = "&8&l>> &7Usunięto {coins} monet z konta gracza {player}";
    public List<String> adminInfoCommand = List.of(
            "&6&lGracz {player}",
            "Monety: {coins}",
            "Wydane monety: {spent_coins}",
            "Pierwsze logowanie: {first_login}"
    );
    public String adminShopAddCommand = "&8&l>> &aDodano nowy przedmiot do sklepu";

    public String shopPurchaseSuccessfully = "<dark_gray><bold>>></bold> <green>Pomyślnie zakupiono przedmiot ze sklepu!";
}
