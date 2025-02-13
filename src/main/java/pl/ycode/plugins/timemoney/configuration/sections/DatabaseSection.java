package pl.ycode.plugins.timemoney.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.ycode.plugins.timemoney.database.DatabaseType;

public class DatabaseSection extends OkaeriConfig {
    @Comment("Available: H2, SQLITE, MARIADB")
    public DatabaseType type = DatabaseType.H2;

    @Comment({
            "For mariadb or mysql type hostname with port. (ex. localhost:3306)",
            "For h2 or sqlite type file name (ex. storage.db)"
    })
    public String hostname = "storage.db";
    public String name = "ytm_users";
    @Comment("Only for mariadb or mysql")
    public String username = "";
    public String password = "";
}
