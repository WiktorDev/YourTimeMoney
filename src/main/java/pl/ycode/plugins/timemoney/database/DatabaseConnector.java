package pl.ycode.plugins.timemoney.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import org.bukkit.plugin.Plugin;
import pl.ycode.plugins.timemoney.configuration.PluginConfiguration;
import pl.ycode.plugins.timemoney.configuration.sections.DatabaseSection;
import pl.ycode.plugins.timemoney.user.User;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<User> fetchUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try(Connection connection = this.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(rs));
            }
        }
        return users;
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) dataSource.close();
    }
}
