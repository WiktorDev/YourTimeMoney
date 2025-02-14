package pl.ycode.plugins.timecoins.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.ycode.plugins.timecoins.configuration.sections.DatabaseSection;

public class PluginConfiguration extends OkaeriConfig {
    public DatabaseSection database = new DatabaseSection();

    @Comment("Format: [number][s,m,h,d] (ex. 1d -> 1 day, 30m -> 30 minutes)")
    public String time = "5m";

    public String receivedCoinMessage = "Zdobyłeś 1 monete czasu! Masz teraz {coins} monet";
}
