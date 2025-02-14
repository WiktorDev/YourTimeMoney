package pl.ycode.plugins.timecoins.user;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {
    @Inject private DatabaseConnector databaseConnector;

    private final List<User> users = new ArrayList<>();

    @PostConstruct()
    public void __construct() {
        String query = "SELECT * FROM `ytimecoins_users`";
        try(Connection connection = this.databaseConnector.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                this.users.add(new User(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createIfNotExists(Player player) {
        if (this.findFirst(player) != null) return;
        User user = new User(player);
        this.save(user);
        this.users.add(user);
    }

    public User findFirst(Player player) {
        return this.findFirst(player.getUniqueId());
    }
    public User findFirst(UUID uniqueId) {
        return this.users.stream().filter(it -> it.getUniqueId().equals(uniqueId)).findFirst().orElse(null);
    }

    public void save(User user) {
        try {
            Statement statement = this.databaseConnector.getConnection().createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO `ytimecoins_users`(`unique_id`, `coins`, `spent_coins`, `first_login`, `last_update`) VALUES (");
            builder.append("'").append(user.getUniqueId().toString()).append("',");
            builder.append(user.getCoins()).append(",");
            builder.append(user.getSpentCoins()).append(",");
            builder.append(user.getFirstLogin()).append(",");
            builder.append(user.getLastUpdate());
            builder.append(");");
            statement.executeUpdate(builder.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
