package pl.ycode.plugins.timemoney.user;

import pl.ycode.plugins.timemoney.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService(DatabaseConnector databaseConnector) {
        String query = "SELECT * FROM users";
        try(Connection connection = databaseConnector.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                this.users.add(new User(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
