package pl.ycode.plugins.timecoins.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import org.bukkit.plugin.Plugin;
import pl.ycode.plugins.timecoins.configuration.PluginConfiguration;
import pl.ycode.plugins.timecoins.configuration.sections.DatabaseSection;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    @Inject private Plugin plugin;
    @Inject private PluginConfiguration configuration;

    private HikariDataSource dataSource;
    private Connection connection;

    @PostConstruct
    public void __construct() {
        DatabaseSection database = this.configuration.database;
        HikariConfig config = new HikariConfig();
        switch (database.type) {
            case H2:
                File dbFile = new File(this.plugin.getDataFolder(), database.hostname);
                config.setJdbcUrl("jdbc:h2:" + dbFile.getAbsolutePath());
                config.setDriverClassName("org.h2.Driver");
                break;
            case SQLITE:
                File sqliteFile = new File(this.plugin.getDataFolder(), database.hostname);
                config.setJdbcUrl("jdbc:sqlite:" + sqliteFile.getAbsolutePath());
                config.setDriverClassName("org.sqlite.JDBC");
                break;
            case MARIADB:
                config.setJdbcUrl("jdbc:mariadb://" + database.hostname + "/" + database.name);
                config.setUsername(database.username);
                config.setPassword(database.password);
                config.setDriverClassName("org.mariadb.jdbc.Driver");
                break;
            default: throw new IllegalStateException("Unexpected value: " + database.type);
        }
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(60000);

        this.dataSource = new HikariDataSource(config);

        try {
            Statement statement = this.dataSource.getConnection().createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE IF NOT EXISTS `ytimecoins_users` (");
            builder.append("`unique_id` VARCHAR(64) PRIMARY KEY,");
            builder.append("`coins` INT,");
            builder.append("`spent_coins` INT,");
            builder.append("`first_login` BIGINT,");
            builder.append("`last_update` BIGINT");
            builder.append(");");
            statement.executeUpdate(builder.toString());
        } catch (SQLException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) dataSource.close();
    }
}
