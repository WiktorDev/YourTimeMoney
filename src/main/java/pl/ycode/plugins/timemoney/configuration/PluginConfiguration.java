package pl.ycode.plugins.timemoney.configuration;

import eu.okaeri.configs.OkaeriConfig;
import pl.ycode.plugins.timemoney.configuration.sections.DatabaseSection;

public class PluginConfiguration extends OkaeriConfig {
    public DatabaseSection database = new DatabaseSection();
}
