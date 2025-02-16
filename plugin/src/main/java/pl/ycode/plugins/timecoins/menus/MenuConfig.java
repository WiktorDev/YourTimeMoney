package pl.ycode.plugins.timecoins.menus;

import eu.okaeri.configs.OkaeriConfig;

import java.util.List;
import java.util.Map;

public abstract class MenuConfig extends OkaeriConfig {
    public abstract String title();
    protected abstract List<String> structure();
    public abstract Map<Character, MenuItem> items();

    public String[] getStructure() {
        return this.structure().toArray(new String[0]);
    }
}
